package io.netty.handler.codec.http2;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http2.Http2Exception.StreamException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.collection.IntObjectMap.PrimitiveEntry;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.List;

public final class Http2MultiplexCodec extends ChannelDuplexHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!Http2MultiplexCodec.class.desiredAssertionStatus());
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Http2MultiplexCodec.class);
    private final List<Http2StreamChannel> channelsToFireChildReadComplete = new ArrayList();
    private final IntObjectMap<Http2StreamChannel> childChannels = new IntObjectHashMap();
    private ChannelHandlerContext ctx;
    private volatile Runnable flushTask;
    private final boolean server;
    private final EventLoopGroup streamGroup;
    private final ChannelHandler streamHandler;

    final class Http2StreamChannel extends AbstractHttp2StreamChannel {
        boolean inStreamsToFireChildReadComplete;
        boolean onStreamClosedFired;
        private final int streamId;

        Http2StreamChannel(int streamId) {
            super(Http2MultiplexCodec.this.ctx.channel());
            this.streamId = streamId;
        }

        protected void doClose() throws Exception {
            if (!this.onStreamClosedFired) {
                Http2MultiplexCodec.this.writeFromStreamChannel(new DefaultHttp2ResetFrame(Http2Error.CANCEL).setStreamId(this.streamId), true);
            }
            super.doClose();
        }

        protected void doWrite(Object msg) {
            if (msg instanceof Http2StreamFrame) {
                Http2StreamFrame frame = (Http2StreamFrame) msg;
                if (frame.streamId() != -1) {
                    ReferenceCountUtil.release(frame);
                    throw new IllegalArgumentException("Stream must not be set on the frame");
                }
                frame.setStreamId(this.streamId);
                Http2MultiplexCodec.this.writeFromStreamChannel(msg, false);
                return;
            }
            ReferenceCountUtil.release(msg);
            throw new IllegalArgumentException("Message must be an Http2StreamFrame: " + msg);
        }

        protected void doWriteComplete() {
            Http2MultiplexCodec.this.flushFromStreamChannel();
        }

        protected EventExecutor preferredEventExecutor() {
            return Http2MultiplexCodec.this.ctx.executor();
        }

        protected void bytesConsumed(int bytes) {
            Http2MultiplexCodec.this.ctx.write(new DefaultHttp2WindowUpdateFrame(bytes).setStreamId(this.streamId));
        }
    }

    public Http2MultiplexCodec(boolean server, EventLoopGroup streamGroup, ChannelHandler streamHandler) {
        if (streamHandler.getClass().isAnnotationPresent(Sharable.class)) {
            this.server = server;
            this.streamHandler = streamHandler;
            this.streamGroup = streamGroup;
            return;
        }
        throw new IllegalArgumentException("streamHandler must be Sharable");
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof StreamException) {
            Throwable streamEx = (StreamException) cause;
            try {
                Http2StreamChannel childChannel = (Http2StreamChannel) this.childChannels.get(streamEx.streamId());
                if (childChannel != null) {
                    childChannel.pipeline().fireExceptionCaught(streamEx);
                } else {
                    logger.warn(String.format("Exception caught for unknown HTTP/2 stream '%d'", new Object[]{Integer.valueOf(streamEx.streamId())}), streamEx);
                }
                onStreamClosed(streamEx.streamId());
            } catch (Throwable th) {
                onStreamClosed(streamEx.streamId());
            }
        } else {
            ctx.fireExceptionCaught(cause);
        }
    }

    public void flush(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Http2Frame)) {
            ctx.fireChannelRead(msg);
        } else if (msg instanceof Http2StreamFrame) {
            Http2StreamFrame frame = (Http2StreamFrame) msg;
            streamId = frame.streamId();
            childChannel = (Http2StreamChannel) this.childChannels.get(streamId);
            if (childChannel == null) {
                ReferenceCountUtil.release(msg);
                throw new StreamException(streamId, Http2Error.STREAM_CLOSED, String.format("Received %s frame for an unknown stream %d", new Object[]{frame.name(), Integer.valueOf(streamId)}));
            }
            fireChildReadAndRegister(childChannel, frame);
        } else if (msg instanceof Http2GoAwayFrame) {
            Http2GoAwayFrame goAwayFrame = (Http2GoAwayFrame) msg;
            for (PrimitiveEntry<Http2StreamChannel> entry : this.childChannels.entries()) {
                childChannel = (Http2StreamChannel) entry.value();
                streamId = entry.key();
                if (streamId > goAwayFrame.lastStreamId() && isLocalStream(streamId)) {
                    childChannel.pipeline().fireUserEventTriggered(goAwayFrame.retainedDuplicate());
                }
            }
            goAwayFrame.release();
        } else {
            ReferenceCountUtil.release(msg);
            throw new UnsupportedMessageTypeException(msg, new Class[0]);
        }
    }

    private void fireChildReadAndRegister(Http2StreamChannel childChannel, Http2StreamFrame frame) {
        childChannel.fireChildRead(frame);
        if (!childChannel.inStreamsToFireChildReadComplete) {
            this.channelsToFireChildReadComplete.add(childChannel);
            childChannel.inStreamsToFireChildReadComplete = true;
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof Http2StreamStateEvent) {
            try {
                int streamId = ((Http2StreamStateEvent) evt).streamId();
                if (evt instanceof Http2StreamActiveEvent) {
                    onStreamActive(streamId);
                } else if (evt instanceof Http2StreamClosedEvent) {
                    onStreamClosed(streamId);
                } else {
                    throw new UnsupportedMessageTypeException(evt, new Class[0]);
                }
                ReferenceCountUtil.release(evt);
            } catch (Throwable th) {
                ReferenceCountUtil.release(evt);
            }
        } else {
            ctx.fireUserEventTriggered(evt);
        }
    }

    private void onStreamActive(int streamId) {
        Http2StreamChannel oldChannel = (Http2StreamChannel) this.childChannels.put(streamId, (Http2StreamChannel) createStreamChannel(this.ctx, streamId, this.streamHandler).channel());
        if (!$assertionsDisabled && oldChannel != null) {
            throw new AssertionError();
        }
    }

    private void onStreamClosed(int streamId) {
        final Http2StreamChannel childChannel = (Http2StreamChannel) this.childChannels.remove(streamId);
        if (childChannel != null) {
            EventLoop eventLoop = childChannel.eventLoop();
            if (eventLoop.inEventLoop()) {
                onStreamClosed0(childChannel);
            } else {
                eventLoop.execute(new Runnable() {
                    public void run() {
                        Http2MultiplexCodec.this.onStreamClosed0(childChannel);
                    }
                });
            }
        }
    }

    private void onStreamClosed0(Http2StreamChannel childChannel) {
        if ($assertionsDisabled || childChannel.eventLoop().inEventLoop()) {
            childChannel.onStreamClosedFired = true;
            childChannel.fireChildRead(AbstractHttp2StreamChannel.CLOSE_MESSAGE);
            return;
        }
        throw new AssertionError();
    }

    void flushFromStreamChannel() {
        EventExecutor executor = this.ctx.executor();
        if (executor.inEventLoop()) {
            flush(this.ctx);
            return;
        }
        Runnable task = this.flushTask;
        if (task == null) {
            task = new Runnable() {
                public void run() {
                    Http2MultiplexCodec.this.flush(Http2MultiplexCodec.this.ctx);
                }
            };
            this.flushTask = task;
        }
        executor.execute(task);
    }

    void writeFromStreamChannel(final Object msg, final boolean flush) {
        final ChannelPromise promise = this.ctx.newPromise();
        EventExecutor executor = this.ctx.executor();
        if (executor.inEventLoop()) {
            writeFromStreamChannel0(msg, flush, promise);
            return;
        }
        try {
            executor.execute(new Runnable() {
                public void run() {
                    Http2MultiplexCodec.this.writeFromStreamChannel0(msg, flush, promise);
                }
            });
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
    }

    private void writeFromStreamChannel0(Object msg, boolean flush, ChannelPromise promise) {
        try {
            write(this.ctx, msg, promise);
        } catch (Throwable cause) {
            promise.tryFailure(cause);
        }
        if (flush) {
            flush(this.ctx);
        }
    }

    private ChannelFuture createStreamChannel(ChannelHandlerContext ctx, int streamId, ChannelHandler handler) {
        EventLoopGroup group;
        if (this.streamGroup != null) {
            group = this.streamGroup;
        } else {
            group = ctx.channel().eventLoop();
        }
        Http2StreamChannel channel = new Http2StreamChannel(streamId);
        channel.pipeline().addLast(new ChannelHandler[]{handler});
        ChannelFuture future = group.register(channel);
        if (future.cause() != null) {
            if (channel.isRegistered()) {
                channel.close();
            } else {
                channel.unsafe().closeForcibly();
            }
        }
        return future;
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        for (int i = 0; i < this.channelsToFireChildReadComplete.size(); i++) {
            Http2StreamChannel childChannel = (Http2StreamChannel) this.channelsToFireChildReadComplete.get(i);
            childChannel.inStreamsToFireChildReadComplete = false;
            childChannel.fireChildReadComplete();
        }
        this.channelsToFireChildReadComplete.clear();
    }

    private boolean isLocalStream(int streamId) {
        boolean even;
        if ((streamId & 1) == 0) {
            even = true;
        } else {
            even = false;
        }
        return streamId > 0 && this.server == even;
    }
}
