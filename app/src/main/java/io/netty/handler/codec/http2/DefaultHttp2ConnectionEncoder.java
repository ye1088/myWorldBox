package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.CoalescingBufferQueue;
import io.netty.handler.codec.http2.Http2Exception.ClosedStreamCreationException;
import io.netty.handler.codec.http2.Http2FrameWriter.Configuration;
import io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;

public class DefaultHttp2ConnectionEncoder implements Http2ConnectionEncoder {
    private final Http2Connection connection;
    private final Http2FrameWriter frameWriter;
    private Http2LifecycleManager lifecycleManager;
    private final ArrayDeque<Http2Settings> outstandingLocalSettingsQueue = new ArrayDeque(4);

    public abstract class FlowControlledBase implements ChannelFutureListener, FlowControlled {
        protected boolean endOfStream;
        protected int padding;
        protected ChannelPromise promise;
        protected final Http2Stream stream;

        protected FlowControlledBase(Http2Stream stream, int padding, boolean endOfStream, ChannelPromise promise) {
            if (padding < 0) {
                throw new IllegalArgumentException("padding must be >= 0");
            }
            this.padding = padding;
            this.endOfStream = endOfStream;
            this.stream = stream;
            this.promise = promise;
        }

        public void writeComplete() {
            if (this.endOfStream) {
                DefaultHttp2ConnectionEncoder.this.lifecycleManager.closeStreamLocal(this.stream, this.promise);
            }
        }

        public void operationComplete(ChannelFuture future) throws Exception {
            if (!future.isSuccess()) {
                error(DefaultHttp2ConnectionEncoder.this.flowController().channelHandlerContext(), future.cause());
            }
        }
    }

    private final class FlowControlledData extends FlowControlledBase {
        private int dataSize = this.queue.readableBytes();
        private final CoalescingBufferQueue queue;

        FlowControlledData(Http2Stream stream, ByteBuf buf, int padding, boolean endOfStream, ChannelPromise promise) {
            super(stream, padding, endOfStream, promise);
            this.queue = new CoalescingBufferQueue(promise.channel());
            this.queue.add(buf, promise);
        }

        public int size() {
            return this.dataSize + this.padding;
        }

        public void error(ChannelHandlerContext ctx, Throwable cause) {
            this.queue.releaseAndFailAll(cause);
            DefaultHttp2ConnectionEncoder.this.lifecycleManager.onError(ctx, cause);
        }

        public void write(ChannelHandlerContext ctx, int allowedBytes) {
            ChannelPromise writePromise;
            boolean z = false;
            int queuedData = this.queue.readableBytes();
            if (!this.endOfStream) {
                if (queuedData == 0) {
                    writePromise = ctx.newPromise().addListener(this);
                    this.queue.remove(0, writePromise).release();
                    ctx.write(Unpooled.EMPTY_BUFFER, writePromise);
                    return;
                } else if (allowedBytes == 0) {
                    return;
                }
            }
            int writeableData = Math.min(queuedData, allowedBytes);
            writePromise = ctx.newPromise().addListener(this);
            ByteBuf toWrite = this.queue.remove(writeableData, writePromise);
            this.dataSize = this.queue.readableBytes();
            int writeablePadding = Math.min(allowedBytes - writeableData, this.padding);
            this.padding -= writeablePadding;
            Http2FrameWriter frameWriter = DefaultHttp2ConnectionEncoder.this.frameWriter();
            int id = this.stream.id();
            if (this.endOfStream && size() == 0) {
                z = true;
            }
            frameWriter.writeData(ctx, id, toWrite, writeablePadding, z, writePromise);
        }

        public boolean merge(ChannelHandlerContext ctx, FlowControlled next) {
            if (FlowControlledData.class == next.getClass()) {
                FlowControlledData nextData = (FlowControlledData) next;
                if (Integer.MAX_VALUE - nextData.size() >= size()) {
                    nextData.queue.copyTo(this.queue);
                    this.dataSize = this.queue.readableBytes();
                    this.padding = Math.max(this.padding, nextData.padding);
                    this.endOfStream = nextData.endOfStream;
                    return true;
                }
            }
            return false;
        }
    }

    private final class FlowControlledHeaders extends FlowControlledBase {
        private final boolean exclusive;
        private final Http2Headers headers;
        private final int streamDependency;
        private final short weight;

        FlowControlledHeaders(Http2Stream stream, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endOfStream, ChannelPromise promise) {
            super(stream, padding, endOfStream, promise);
            this.headers = headers;
            this.streamDependency = streamDependency;
            this.weight = weight;
            this.exclusive = exclusive;
        }

        public int size() {
            return 0;
        }

        public void error(ChannelHandlerContext ctx, Throwable cause) {
            if (ctx != null) {
                DefaultHttp2ConnectionEncoder.this.lifecycleManager.onError(ctx, cause);
            }
            this.promise.tryFailure(cause);
        }

        public void write(ChannelHandlerContext ctx, int allowedBytes) {
            if (this.promise.isVoid()) {
                this.promise = ctx.newPromise();
            }
            this.promise.addListener(this);
            DefaultHttp2ConnectionEncoder.this.frameWriter.writeHeaders(ctx, this.stream.id(), this.headers, this.streamDependency, this.weight, this.exclusive, this.padding, this.endOfStream, this.promise);
        }

        public boolean merge(ChannelHandlerContext ctx, FlowControlled next) {
            return false;
        }
    }

    public DefaultHttp2ConnectionEncoder(Http2Connection connection, Http2FrameWriter frameWriter) {
        this.connection = (Http2Connection) ObjectUtil.checkNotNull(connection, "connection");
        this.frameWriter = (Http2FrameWriter) ObjectUtil.checkNotNull(frameWriter, "frameWriter");
        if (connection.remote().flowController() == null) {
            connection.remote().flowController(new DefaultHttp2RemoteFlowController(connection));
        }
    }

    public void lifecycleManager(Http2LifecycleManager lifecycleManager) {
        this.lifecycleManager = (Http2LifecycleManager) ObjectUtil.checkNotNull(lifecycleManager, "lifecycleManager");
    }

    public Http2FrameWriter frameWriter() {
        return this.frameWriter;
    }

    public Http2Connection connection() {
        return this.connection;
    }

    public final Http2RemoteFlowController flowController() {
        return (Http2RemoteFlowController) connection().remote().flowController();
    }

    public void remoteSettings(Http2Settings settings) throws Http2Exception {
        Boolean pushEnabled = settings.pushEnabled();
        Configuration config = configuration();
        Http2HeaderTable outboundHeaderTable = config.headerTable();
        Http2FrameSizePolicy outboundFrameSizePolicy = config.frameSizePolicy();
        if (pushEnabled != null) {
            if (this.connection.isServer() || !pushEnabled.booleanValue()) {
                this.connection.remote().allowPushTo(pushEnabled.booleanValue());
            } else {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Client received a_isRightVersion value of ENABLE_PUSH specified to other than 0", new Object[0]);
            }
        }
        Long maxConcurrentStreams = settings.maxConcurrentStreams();
        if (maxConcurrentStreams != null) {
            this.connection.local().maxStreams((int) Math.min(maxConcurrentStreams.longValue(), 2147483647L), Integer.MAX_VALUE);
        }
        Long headerTableSize = settings.headerTableSize();
        if (headerTableSize != null) {
            outboundHeaderTable.maxHeaderTableSize((int) Math.min(headerTableSize.longValue(), 2147483647L));
        }
        Integer maxHeaderListSize = settings.maxHeaderListSize();
        if (maxHeaderListSize != null) {
            outboundHeaderTable.maxHeaderListSize(maxHeaderListSize.intValue());
        }
        Integer maxFrameSize = settings.maxFrameSize();
        if (maxFrameSize != null) {
            outboundFrameSizePolicy.maxFrameSize(maxFrameSize.intValue());
        }
        Integer initialWindowSize = settings.initialWindowSize();
        if (initialWindowSize != null) {
            flowController().initialWindowSize(initialWindowSize.intValue());
        }
    }

    public ChannelFuture writeData(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream, ChannelPromise promise) {
        try {
            Http2Stream stream = requireStream(streamId);
            switch (stream.state()) {
                case OPEN:
                case HALF_CLOSED_REMOTE:
                    flowController().addFlowControlled(stream, new FlowControlledData(stream, data, padding, endOfStream, promise));
                    return promise;
                default:
                    throw new IllegalStateException(String.format("Stream %d in unexpected state: %s", new Object[]{Integer.valueOf(stream.id()), stream.state()}));
            }
        } catch (Throwable e) {
            data.release();
            return promise.setFailure(e);
        }
    }

    public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream, ChannelPromise promise) {
        return writeHeaders(ctx, streamId, headers, 0, (short) 16, false, padding, endStream, promise);
    }

    public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endOfStream, ChannelPromise promise) {
        try {
            Http2Stream stream = this.connection.stream(streamId);
            if (stream == null) {
                stream = this.connection.local().createStream(streamId, endOfStream);
            } else {
                switch (stream.state()) {
                    case OPEN:
                    case HALF_CLOSED_REMOTE:
                        break;
                    case RESERVED_LOCAL:
                        stream.open(endOfStream);
                        break;
                    default:
                        throw new IllegalStateException(String.format("Stream %d in unexpected state: %s", new Object[]{Integer.valueOf(stream.id()), stream.state()}));
                }
            }
            Http2RemoteFlowController flowController = flowController();
            if (endOfStream && flowController.hasFlowControlled(stream)) {
                flowController.addFlowControlled(stream, new FlowControlledHeaders(stream, headers, streamDependency, weight, exclusive, padding, endOfStream, promise));
                return promise;
            }
            ChannelFuture future = this.frameWriter.writeHeaders(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endOfStream, promise);
            if (endOfStream) {
                final Http2Stream finalStream = stream;
                final ChannelPromise channelPromise = promise;
                future.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        DefaultHttp2ConnectionEncoder.this.lifecycleManager.closeStreamLocal(finalStream, channelPromise);
                    }
                });
            }
            return future;
        } catch (Http2NoMoreStreamIdsException e) {
            this.lifecycleManager.onError(ctx, e);
            return promise.setFailure(e);
        } catch (Throwable e2) {
            return promise.setFailure(e2);
        }
    }

    public ChannelFuture writePriority(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive, ChannelPromise promise) {
        try {
            Http2Stream stream = this.connection.stream(streamId);
            if (stream == null) {
                stream = this.connection.local().createIdleStream(streamId);
            }
            stream.setPriority(streamDependency, weight, exclusive);
        } catch (ClosedStreamCreationException e) {
        } catch (Throwable t) {
            return promise.setFailure(t);
        }
        return this.frameWriter.writePriority(ctx, streamId, streamDependency, weight, exclusive, promise);
    }

    public ChannelFuture writeRstStream(ChannelHandlerContext ctx, int streamId, long errorCode, ChannelPromise promise) {
        return this.lifecycleManager.resetStream(ctx, streamId, errorCode, promise);
    }

    public ChannelFuture writeSettings(ChannelHandlerContext ctx, Http2Settings settings, ChannelPromise promise) {
        this.outstandingLocalSettingsQueue.add(settings);
        try {
            if (settings.pushEnabled() == null || !this.connection.isServer()) {
                return this.frameWriter.writeSettings(ctx, settings, promise);
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified", new Object[0]);
        } catch (Throwable e) {
            return promise.setFailure(e);
        }
    }

    public ChannelFuture writeSettingsAck(ChannelHandlerContext ctx, ChannelPromise promise) {
        return this.frameWriter.writeSettingsAck(ctx, promise);
    }

    public ChannelFuture writePing(ChannelHandlerContext ctx, boolean ack, ByteBuf data, ChannelPromise promise) {
        return this.frameWriter.writePing(ctx, ack, data, promise);
    }

    public ChannelFuture writePushPromise(ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding, ChannelPromise promise) {
        try {
            if (this.connection.goAwayReceived()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Sending PUSH_PROMISE after GO_AWAY received.", new Object[0]);
            }
            this.connection.local().reservePushStream(promisedStreamId, requireStream(streamId));
            return this.frameWriter.writePushPromise(ctx, streamId, promisedStreamId, headers, padding, promise);
        } catch (Throwable e) {
            return promise.setFailure(e);
        }
    }

    public ChannelFuture writeGoAway(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData, ChannelPromise promise) {
        return this.lifecycleManager.goAway(ctx, lastStreamId, errorCode, debugData, promise);
    }

    public ChannelFuture writeWindowUpdate(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement, ChannelPromise promise) {
        return promise.setFailure(new UnsupportedOperationException("Use the Http2[Inbound|Outbound]FlowController objects to control window sizes"));
    }

    public ChannelFuture writeFrame(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload, ChannelPromise promise) {
        return this.frameWriter.writeFrame(ctx, frameType, streamId, flags, payload, promise);
    }

    public void close() {
        this.frameWriter.close();
    }

    public Http2Settings pollSentSettings() {
        return (Http2Settings) this.outstandingLocalSettingsQueue.poll();
    }

    public Configuration configuration() {
        return this.frameWriter.configuration();
    }

    private Http2Stream requireStream(int streamId) {
        Http2Stream stream = this.connection.stream(streamId);
        if (stream != null) {
            return stream;
        }
        String message;
        if (this.connection.streamMayHaveExisted(streamId)) {
            message = "Stream no longer exists: " + streamId;
        } else {
            message = "Stream does not exist: " + streamId;
        }
        throw new IllegalArgumentException(message);
    }
}
