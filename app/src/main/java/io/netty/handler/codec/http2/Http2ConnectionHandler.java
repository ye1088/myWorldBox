package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http2.Http2Exception.CompositeStreamException;
import io.netty.handler.codec.http2.Http2Exception.StreamException;
import io.netty.handler.codec.http2.Http2Stream.State;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Http2ConnectionHandler extends ByteToMessageDecoder implements ChannelOutboundHandler, Http2LifecycleManager {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Http2ConnectionHandler.class);
    private BaseDecoder byteDecoder;
    private ChannelFutureListener closeListener;
    private final Http2ConnectionDecoder decoder;
    private final Http2ConnectionEncoder encoder;
    private long gracefulShutdownTimeoutMillis;
    private final Http2Settings initialSettings;

    private abstract class BaseDecoder {
        public abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception;

        private BaseDecoder() {
        }

        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        }

        public void channelActive(ChannelHandlerContext ctx) throws Exception {
        }

        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            Http2ConnectionHandler.this.encoder().close();
            Http2ConnectionHandler.this.decoder().close();
            Http2ConnectionHandler.this.connection().close(ctx.voidPromise());
        }

        public boolean prefaceSent() {
            return true;
        }
    }

    private static final class ClosingChannelFutureListener implements ChannelFutureListener {
        private final ChannelHandlerContext ctx;
        private final ChannelPromise promise;
        private final ScheduledFuture<?> timeoutTask;

        ClosingChannelFutureListener(ChannelHandlerContext ctx, ChannelPromise promise) {
            this.ctx = ctx;
            this.promise = promise;
            this.timeoutTask = null;
        }

        ClosingChannelFutureListener(final ChannelHandlerContext ctx, final ChannelPromise promise, long timeout, TimeUnit unit) {
            this.ctx = ctx;
            this.promise = promise;
            this.timeoutTask = ctx.executor().schedule(new Runnable() {
                public void run() {
                    ctx.close(promise);
                }
            }, timeout, unit);
        }

        public void operationComplete(ChannelFuture sentGoAwayFuture) throws Exception {
            if (this.timeoutTask != null) {
                this.timeoutTask.cancel(false);
            }
            this.ctx.close(this.promise);
        }
    }

    private final class FrameDecoder extends BaseDecoder {
        private FrameDecoder() {
            super();
        }

        public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            try {
                Http2ConnectionHandler.this.decoder.decodeFrame(ctx, in, out);
            } catch (Throwable e) {
                Http2ConnectionHandler.this.onError(ctx, e);
            }
        }
    }

    private final class PrefaceDecoder extends BaseDecoder {
        private ByteBuf clientPrefaceString;
        private boolean prefaceSent;

        public PrefaceDecoder(ChannelHandlerContext ctx) {
            super();
            this.clientPrefaceString = Http2ConnectionHandler.clientPrefaceString(Http2ConnectionHandler.this.encoder.connection());
            sendPreface(ctx);
        }

        public boolean prefaceSent() {
            return this.prefaceSent;
        }

        public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            try {
                if (ctx.channel().isActive() && readClientPrefaceString(in) && verifyFirstFrameIsSettings(in)) {
                    Http2ConnectionHandler.this.byteDecoder = new FrameDecoder();
                    Http2ConnectionHandler.this.byteDecoder.decode(ctx, in, out);
                }
            } catch (Throwable e) {
                Http2ConnectionHandler.this.onError(ctx, e);
            }
        }

        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            sendPreface(ctx);
        }

        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            cleanup();
            super.channelInactive(ctx);
        }

        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            cleanup();
        }

        private void cleanup() {
            if (this.clientPrefaceString != null) {
                this.clientPrefaceString.release();
                this.clientPrefaceString = null;
            }
        }

        private boolean readClientPrefaceString(ByteBuf in) throws Http2Exception {
            if (this.clientPrefaceString == null) {
                return true;
            }
            int bytesRead = Math.min(in.readableBytes(), this.clientPrefaceString.readableBytes());
            if (bytesRead == 0 || !ByteBufUtil.equals(in, in.readerIndex(), this.clientPrefaceString, this.clientPrefaceString.readerIndex(), bytesRead)) {
                String receivedBytes = ByteBufUtil.hexDump(in, in.readerIndex(), Math.min(in.readableBytes(), this.clientPrefaceString.readableBytes()));
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP/2 client preface string missing or corrupt. Hex dump for received bytes: %s", receivedBytes);
            }
            in.skipBytes(bytesRead);
            this.clientPrefaceString.skipBytes(bytesRead);
            if (this.clientPrefaceString.isReadable()) {
                return false;
            }
            this.clientPrefaceString.release();
            this.clientPrefaceString = null;
            return true;
        }

        private boolean verifyFirstFrameIsSettings(ByteBuf in) throws Http2Exception {
            if (in.readableBytes() < 5) {
                return false;
            }
            short frameType = in.getUnsignedByte(in.readerIndex() + 3);
            short flags = in.getUnsignedByte(in.readerIndex() + 4);
            if (frameType == (short) 4 && (flags & 1) == 0) {
                return true;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "First received frame was not SETTINGS. Hex dump for first 5 bytes: %s", ByteBufUtil.hexDump(in, in.readerIndex(), 5));
        }

        private void sendPreface(ChannelHandlerContext ctx) {
            if (!this.prefaceSent && ctx.channel().isActive()) {
                this.prefaceSent = true;
                if (!Http2ConnectionHandler.this.connection().isServer()) {
                    ctx.write(Http2CodecUtil.connectionPrefaceBuf()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                }
                Http2ConnectionHandler.this.encoder.writeSettings(ctx, Http2ConnectionHandler.this.initialSettings, ctx.newPromise()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
    }

    protected Http2ConnectionHandler(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings initialSettings) {
        this.initialSettings = (Http2Settings) ObjectUtil.checkNotNull(initialSettings, "initialSettings");
        this.decoder = (Http2ConnectionDecoder) ObjectUtil.checkNotNull(decoder, "decoder");
        this.encoder = (Http2ConnectionEncoder) ObjectUtil.checkNotNull(encoder, "encoder");
        if (encoder.connection() != decoder.connection()) {
            throw new IllegalArgumentException("Encoder and Decoder do not share the same connection object");
        }
    }

    public long gracefulShutdownTimeoutMillis() {
        return this.gracefulShutdownTimeoutMillis;
    }

    public void gracefulShutdownTimeoutMillis(long gracefulShutdownTimeoutMillis) {
        if (gracefulShutdownTimeoutMillis < 0) {
            throw new IllegalArgumentException("gracefulShutdownTimeoutMillis: " + gracefulShutdownTimeoutMillis + " (expected: >= 0)");
        }
        this.gracefulShutdownTimeoutMillis = gracefulShutdownTimeoutMillis;
    }

    public Http2Connection connection() {
        return this.encoder.connection();
    }

    public Http2ConnectionDecoder decoder() {
        return this.decoder;
    }

    public Http2ConnectionEncoder encoder() {
        return this.encoder;
    }

    private boolean prefaceSent() {
        return this.byteDecoder != null && this.byteDecoder.prefaceSent();
    }

    public void onHttpClientUpgrade() throws Http2Exception {
        if (connection().isServer()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Client-side HTTP upgrade requested for a_isRightVersion server", new Object[0]);
        } else if (prefaceSent() || this.decoder.prefaceReceived()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP upgrade must occur before HTTP/2 preface is sent or received", new Object[0]);
        } else {
            connection().local().createStream(1, true);
        }
    }

    public void onHttpServerUpgrade(Http2Settings settings) throws Http2Exception {
        if (!connection().isServer()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server-side HTTP upgrade requested for a_isRightVersion client", new Object[0]);
        } else if (prefaceSent() || this.decoder.prefaceReceived()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP upgrade must occur before HTTP/2 preface is sent or received", new Object[0]);
        } else {
            this.encoder.remoteSettings(settings);
            connection().remote().createStream(1, true);
        }
    }

    public void flush(ChannelHandlerContext ctx) throws Http2Exception {
        this.encoder.flowController().writePendingBytes();
        try {
            ctx.flush();
        } catch (Throwable t) {
            Http2Exception http2Exception = new Http2Exception(Http2Error.INTERNAL_ERROR, "Error flushing", t);
        }
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.encoder.lifecycleManager(this);
        this.decoder.lifecycleManager(this);
        this.encoder.flowController().channelHandlerContext(ctx);
        this.decoder.flowController().channelHandlerContext(ctx);
        this.byteDecoder = new PrefaceDecoder(ctx);
    }

    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
        if (this.byteDecoder != null) {
            this.byteDecoder.handlerRemoved(ctx);
            this.byteDecoder = null;
        }
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (this.byteDecoder == null) {
            this.byteDecoder = new PrefaceDecoder(ctx);
        }
        this.byteDecoder.channelActive(ctx);
        super.channelActive(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (this.byteDecoder != null) {
            this.byteDecoder.channelInactive(ctx);
            this.byteDecoder = null;
        }
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        try {
            if (ctx.channel().isWritable()) {
                flush(ctx);
            }
            this.encoder.flowController().channelWritabilityChanged();
        } finally {
            super.channelWritabilityChanged(ctx);
        }
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        this.byteDecoder.decode(ctx, in, out);
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        if (ctx.channel().isActive()) {
            ChannelFuture future = connection().goAwaySent() ? ctx.write(Unpooled.EMPTY_BUFFER) : goAway(ctx, null);
            ctx.flush();
            doGracefulShutdown(ctx, future, promise);
            return;
        }
        ctx.close(promise);
    }

    private void doGracefulShutdown(ChannelHandlerContext ctx, ChannelFuture future, ChannelPromise promise) {
        if (isGracefulShutdownComplete()) {
            future.addListener(new ClosingChannelFutureListener(ctx, promise));
            return;
        }
        this.closeListener = new ClosingChannelFutureListener(ctx, promise, this.gracefulShutdownTimeoutMillis, TimeUnit.MILLISECONDS);
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }

    public void read(ChannelHandlerContext ctx) throws Exception {
        ctx.read();
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        try {
            flush(ctx);
        } finally {
            super.channelReadComplete(ctx);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (Http2CodecUtil.getEmbeddedHttp2Exception(cause) != null) {
            onError(ctx, cause);
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }

    public void closeStreamLocal(Http2Stream stream, ChannelFuture future) {
        switch (stream.state()) {
            case HALF_CLOSED_LOCAL:
            case OPEN:
                stream.closeLocalSide();
                return;
            default:
                closeStream(stream, future);
                return;
        }
    }

    public void closeStreamRemote(Http2Stream stream, ChannelFuture future) {
        switch (stream.state()) {
            case OPEN:
            case HALF_CLOSED_REMOTE:
                stream.closeRemoteSide();
                return;
            default:
                closeStream(stream, future);
                return;
        }
    }

    public void closeStream(Http2Stream stream, ChannelFuture future) {
        stream.close();
        if (future.isDone()) {
            checkCloseConnection(future);
        } else {
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    Http2ConnectionHandler.this.checkCloseConnection(future);
                }
            });
        }
    }

    public void onError(ChannelHandlerContext ctx, Throwable cause) {
        Http2Exception embedded = Http2CodecUtil.getEmbeddedHttp2Exception(cause);
        if (Http2Exception.isStreamError(embedded)) {
            onStreamError(ctx, cause, (StreamException) embedded);
        } else if (embedded instanceof CompositeStreamException) {
            Iterator i$ = ((CompositeStreamException) embedded).iterator();
            while (i$.hasNext()) {
                onStreamError(ctx, cause, (StreamException) i$.next());
            }
        } else {
            onConnectionError(ctx, cause, embedded);
        }
        ctx.flush();
    }

    protected boolean isGracefulShutdownComplete() {
        return connection().numActiveStreams() == 0;
    }

    protected void onConnectionError(ChannelHandlerContext ctx, Throwable cause, Http2Exception http2Ex) {
        if (http2Ex == null) {
            http2Ex = new Http2Exception(Http2Error.INTERNAL_ERROR, cause.getMessage(), cause);
        }
        ChannelPromise promise = ctx.newPromise();
        ChannelFuture future = goAway(ctx, http2Ex);
        switch (http2Ex.shutdownHint()) {
            case GRACEFUL_SHUTDOWN:
                doGracefulShutdown(ctx, future, promise);
                return;
            default:
                future.addListener(new ClosingChannelFutureListener(ctx, promise));
                return;
        }
    }

    protected void onStreamError(ChannelHandlerContext ctx, Throwable cause, StreamException http2Ex) {
        resetStream(ctx, http2Ex.streamId(), http2Ex.error().code(), ctx.newPromise());
    }

    protected Http2FrameWriter frameWriter() {
        return encoder().frameWriter();
    }

    public ChannelFuture resetStream(final ChannelHandlerContext ctx, int streamId, long errorCode, ChannelPromise promise) {
        final Http2Stream stream = connection().stream(streamId);
        if (stream == null || stream.isResetSent()) {
            return promise.setSuccess();
        }
        ChannelFuture future;
        if (stream.state() == State.IDLE) {
            future = promise.setSuccess();
        } else {
            future = frameWriter().writeRstStream(ctx, streamId, errorCode, promise);
        }
        stream.resetSent();
        if (future.isDone()) {
            processRstStreamWriteResult(ctx, stream, future);
            return future;
        }
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                Http2ConnectionHandler.this.processRstStreamWriteResult(ctx, stream, future);
            }
        });
        return future;
    }

    public ChannelFuture goAway(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData, ChannelPromise promise) {
        try {
            Http2Connection connection = connection();
            if (!connection.goAwaySent() || lastStreamId <= connection.remote().lastStreamKnownByPeer()) {
                connection.goAwaySent(lastStreamId, errorCode, debugData);
                debugData.retain();
                ChannelFuture future = frameWriter().writeGoAway(ctx, lastStreamId, errorCode, debugData, promise);
                if (future.isDone()) {
                    processGoAwayWriteResult(ctx, lastStreamId, errorCode, debugData, future);
                    return future;
                }
                final ChannelHandlerContext channelHandlerContext = ctx;
                final int i = lastStreamId;
                final long j = errorCode;
                final ByteBuf byteBuf = debugData;
                future.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        Http2ConnectionHandler.processGoAwayWriteResult(channelHandlerContext, i, j, byteBuf, future);
                    }
                });
                return future;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Last stream identifier must not increase between sending multiple GOAWAY frames (was '%d', is '%d').", Integer.valueOf(connection.remote().lastStreamKnownByPeer()), Integer.valueOf(lastStreamId));
        } catch (Throwable cause) {
            debugData.release();
            return promise.setFailure(cause);
        }
    }

    private void checkCloseConnection(ChannelFuture future) {
        if (this.closeListener != null && isGracefulShutdownComplete()) {
            ChannelFutureListener closeListener = this.closeListener;
            this.closeListener = null;
            try {
                closeListener.operationComplete(future);
            } catch (Exception e) {
                throw new IllegalStateException("Close listener threw an unexpected exception", e);
            }
        }
    }

    private ChannelFuture goAway(ChannelHandlerContext ctx, Http2Exception cause) {
        return goAway(ctx, connection().remote().lastStreamCreated(), cause != null ? cause.error().code() : Http2Error.NO_ERROR.code(), Http2CodecUtil.toByteBuf(ctx, cause), ctx.newPromise());
    }

    private void processRstStreamWriteResult(ChannelHandlerContext ctx, Http2Stream stream, ChannelFuture future) {
        if (future.isSuccess()) {
            closeStream(stream, future);
        } else {
            onConnectionError(ctx, future.cause(), null);
        }
    }

    private static ByteBuf clientPrefaceString(Http2Connection connection) {
        return connection.isServer() ? Http2CodecUtil.connectionPrefaceBuf() : null;
    }

    private static void processGoAwayWriteResult(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData, ChannelFuture future) {
        try {
            if (!future.isSuccess()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("{} Sending GOAWAY failed: lastStreamId '{}', errorCode '{}', debugData '{}'. Forcing shutdown of the connection.", ctx.channel(), Integer.valueOf(lastStreamId), Long.valueOf(errorCode), debugData.toString(CharsetUtil.UTF_8), future.cause());
                }
                ctx.close();
            } else if (errorCode != Http2Error.NO_ERROR.code()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("{} Sent GOAWAY: lastStreamId '{}', errorCode '{}', debugData '{}'. Forcing shutdown of the connection.", ctx.channel(), Integer.valueOf(lastStreamId), Long.valueOf(errorCode), debugData.toString(CharsetUtil.UTF_8), future.cause());
                }
                ctx.close();
            }
            debugData.release();
        } catch (Throwable th) {
            debugData.release();
        }
    }
}
