package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;

public class CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler> extends ChannelDuplexHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!CombinedChannelDuplexHandler.class.desiredAssertionStatus());
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(CombinedChannelDuplexHandler.class);
    private volatile boolean handlerAdded;
    private DelegatingChannelHandlerContext inboundCtx;
    private I inboundHandler;
    private DelegatingChannelHandlerContext outboundCtx;
    private O outboundHandler;

    private static class DelegatingChannelHandlerContext implements ChannelHandlerContext {
        private final ChannelHandlerContext ctx;
        private final ChannelHandler handler;
        boolean removed;

        DelegatingChannelHandlerContext(ChannelHandlerContext ctx, ChannelHandler handler) {
            this.ctx = ctx;
            this.handler = handler;
        }

        public Channel channel() {
            return this.ctx.channel();
        }

        public EventExecutor executor() {
            return this.ctx.executor();
        }

        public String name() {
            return this.ctx.name();
        }

        public ChannelHandler handler() {
            return this.ctx.handler();
        }

        public boolean isRemoved() {
            return this.removed || this.ctx.isRemoved();
        }

        public ChannelHandlerContext fireChannelRegistered() {
            this.ctx.fireChannelRegistered();
            return this;
        }

        public ChannelHandlerContext fireChannelUnregistered() {
            this.ctx.fireChannelUnregistered();
            return this;
        }

        public ChannelHandlerContext fireChannelActive() {
            this.ctx.fireChannelActive();
            return this;
        }

        public ChannelHandlerContext fireChannelInactive() {
            this.ctx.fireChannelInactive();
            return this;
        }

        public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
            this.ctx.fireExceptionCaught(cause);
            return this;
        }

        public ChannelHandlerContext fireUserEventTriggered(Object event) {
            this.ctx.fireUserEventTriggered(event);
            return this;
        }

        public ChannelHandlerContext fireChannelRead(Object msg) {
            this.ctx.fireChannelRead(msg);
            return this;
        }

        public ChannelHandlerContext fireChannelReadComplete() {
            this.ctx.fireChannelReadComplete();
            return this;
        }

        public ChannelHandlerContext fireChannelWritabilityChanged() {
            this.ctx.fireChannelWritabilityChanged();
            return this;
        }

        public ChannelFuture bind(SocketAddress localAddress) {
            return this.ctx.bind(localAddress);
        }

        public ChannelFuture connect(SocketAddress remoteAddress) {
            return this.ctx.connect(remoteAddress);
        }

        public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
            return this.ctx.connect(remoteAddress, localAddress);
        }

        public ChannelFuture disconnect() {
            return this.ctx.disconnect();
        }

        public ChannelFuture close() {
            return this.ctx.close();
        }

        public ChannelFuture deregister() {
            return this.ctx.deregister();
        }

        public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
            return this.ctx.bind(localAddress, promise);
        }

        public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
            return this.ctx.connect(remoteAddress, promise);
        }

        public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            return this.ctx.connect(remoteAddress, localAddress, promise);
        }

        public ChannelFuture disconnect(ChannelPromise promise) {
            return this.ctx.disconnect(promise);
        }

        public ChannelFuture close(ChannelPromise promise) {
            return this.ctx.close(promise);
        }

        public ChannelFuture deregister(ChannelPromise promise) {
            return this.ctx.deregister(promise);
        }

        public ChannelHandlerContext read() {
            this.ctx.read();
            return this;
        }

        public ChannelFuture write(Object msg) {
            return this.ctx.write(msg);
        }

        public ChannelFuture write(Object msg, ChannelPromise promise) {
            return this.ctx.write(msg, promise);
        }

        public ChannelHandlerContext flush() {
            this.ctx.flush();
            return this;
        }

        public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
            return this.ctx.writeAndFlush(msg, promise);
        }

        public ChannelFuture writeAndFlush(Object msg) {
            return this.ctx.writeAndFlush(msg);
        }

        public ChannelPipeline pipeline() {
            return this.ctx.pipeline();
        }

        public ByteBufAllocator alloc() {
            return this.ctx.alloc();
        }

        public ChannelPromise newPromise() {
            return this.ctx.newPromise();
        }

        public ChannelProgressivePromise newProgressivePromise() {
            return this.ctx.newProgressivePromise();
        }

        public ChannelFuture newSucceededFuture() {
            return this.ctx.newSucceededFuture();
        }

        public ChannelFuture newFailedFuture(Throwable cause) {
            return this.ctx.newFailedFuture(cause);
        }

        public ChannelPromise voidPromise() {
            return this.ctx.voidPromise();
        }

        public <T> Attribute<T> attr(AttributeKey<T> key) {
            return this.ctx.attr(key);
        }

        public <T> boolean hasAttr(AttributeKey<T> key) {
            return this.ctx.hasAttr(key);
        }

        final void remove() {
            EventExecutor executor = executor();
            if (executor.inEventLoop()) {
                remove0();
            } else {
                executor.execute(new Runnable() {
                    public void run() {
                        DelegatingChannelHandlerContext.this.remove0();
                    }
                });
            }
        }

        private void remove0() {
            if (!this.removed) {
                this.removed = true;
                try {
                    this.handler.handlerRemoved(this);
                } catch (Throwable cause) {
                    fireExceptionCaught(new ChannelPipelineException(this.handler.getClass().getName() + ".handlerRemoved() has thrown an exception.", cause));
                }
            }
        }
    }

    protected CombinedChannelDuplexHandler() {
    }

    public CombinedChannelDuplexHandler(I inboundHandler, O outboundHandler) {
        init(inboundHandler, outboundHandler);
    }

    protected final void init(I inboundHandler, O outboundHandler) {
        validate(inboundHandler, outboundHandler);
        this.inboundHandler = inboundHandler;
        this.outboundHandler = outboundHandler;
    }

    private void validate(I inboundHandler, O outboundHandler) {
        if (this.inboundHandler != null) {
            throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
        } else if (inboundHandler == null) {
            throw new NullPointerException("inboundHandler");
        } else if (outboundHandler == null) {
            throw new NullPointerException("outboundHandler");
        } else if (inboundHandler instanceof ChannelOutboundHandler) {
            throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
        } else if (outboundHandler instanceof ChannelInboundHandler) {
            throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
        }
    }

    protected final I inboundHandler() {
        return this.inboundHandler;
    }

    protected final O outboundHandler() {
        return this.outboundHandler;
    }

    private void checkAdded() {
        if (!this.handlerAdded) {
            throw new IllegalStateException("handler not added to pipeline yet");
        }
    }

    public final void removeInboundHandler() {
        checkAdded();
        this.inboundCtx.remove();
    }

    public final void removeOutboundHandler() {
        checkAdded();
        this.outboundCtx.remove();
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (this.inboundHandler == null) {
            throw new IllegalStateException("init() must be invoked before being added to a_isRightVersion " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
        }
        this.outboundCtx = new DelegatingChannelHandlerContext(ctx, this.outboundHandler);
        this.inboundCtx = new DelegatingChannelHandlerContext(ctx, this.inboundHandler) {
            public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
                if (CombinedChannelDuplexHandler.this.outboundCtx.removed) {
                    super.fireExceptionCaught(cause);
                } else {
                    try {
                        CombinedChannelDuplexHandler.this.outboundHandler.exceptionCaught(CombinedChannelDuplexHandler.this.outboundCtx, cause);
                    } catch (Throwable error) {
                        if (CombinedChannelDuplexHandler.logger.isDebugEnabled()) {
                            CombinedChannelDuplexHandler.logger.debug("An exception {}was thrown by a_isRightVersion user handler's exceptionCaught() method while handling the following exception:", ThrowableUtil.stackTraceToString(error), cause);
                        } else if (CombinedChannelDuplexHandler.logger.isWarnEnabled()) {
                            CombinedChannelDuplexHandler.logger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a_isRightVersion user handler's exceptionCaught() method while handling the following exception:", error, cause);
                        }
                    }
                }
                return this;
            }
        };
        this.handlerAdded = true;
        try {
            this.inboundHandler.handlerAdded(this.inboundCtx);
        } finally {
            this.outboundHandler.handlerAdded(this.outboundCtx);
        }
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        try {
            this.inboundCtx.remove();
        } finally {
            this.outboundCtx.remove();
        }
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireChannelRegistered();
        } else {
            this.inboundHandler.channelRegistered(this.inboundCtx);
        }
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireChannelUnregistered();
        } else {
            this.inboundHandler.channelUnregistered(this.inboundCtx);
        }
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireChannelActive();
        } else {
            this.inboundHandler.channelActive(this.inboundCtx);
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireChannelInactive();
        } else {
            this.inboundHandler.channelInactive(this.inboundCtx);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireExceptionCaught(cause);
        } else {
            this.inboundHandler.exceptionCaught(this.inboundCtx, cause);
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireUserEventTriggered(evt);
        } else {
            this.inboundHandler.userEventTriggered(this.inboundCtx, evt);
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireChannelRead(msg);
        } else {
            this.inboundHandler.channelRead(this.inboundCtx, msg);
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireChannelReadComplete();
        } else {
            this.inboundHandler.channelReadComplete(this.inboundCtx);
        }
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        if (!$assertionsDisabled && ctx != this.inboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.inboundCtx.removed) {
            this.inboundCtx.fireChannelWritabilityChanged();
        } else {
            this.inboundHandler.channelWritabilityChanged(this.inboundCtx);
        }
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        if (!$assertionsDisabled && ctx != this.outboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.outboundCtx.removed) {
            this.outboundCtx.bind(localAddress, promise);
        } else {
            this.outboundHandler.bind(this.outboundCtx, localAddress, promise);
        }
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        if (!$assertionsDisabled && ctx != this.outboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.outboundCtx.removed) {
            this.outboundCtx.connect(localAddress, promise);
        } else {
            this.outboundHandler.connect(this.outboundCtx, remoteAddress, localAddress, promise);
        }
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        if (!$assertionsDisabled && ctx != this.outboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.outboundCtx.removed) {
            this.outboundCtx.disconnect(promise);
        } else {
            this.outboundHandler.disconnect(this.outboundCtx, promise);
        }
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        if (!$assertionsDisabled && ctx != this.outboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.outboundCtx.removed) {
            this.outboundCtx.close(promise);
        } else {
            this.outboundHandler.close(this.outboundCtx, promise);
        }
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        if (!$assertionsDisabled && ctx != this.outboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.outboundCtx.removed) {
            this.outboundCtx.deregister(promise);
        } else {
            this.outboundHandler.deregister(this.outboundCtx, promise);
        }
    }

    public void read(ChannelHandlerContext ctx) throws Exception {
        if (!$assertionsDisabled && ctx != this.outboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.outboundCtx.removed) {
            this.outboundCtx.read();
        } else {
            this.outboundHandler.read(this.outboundCtx);
        }
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!$assertionsDisabled && ctx != this.outboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.outboundCtx.removed) {
            this.outboundCtx.write(msg, promise);
        } else {
            this.outboundHandler.write(this.outboundCtx, msg, promise);
        }
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        if (!$assertionsDisabled && ctx != this.outboundCtx.ctx) {
            throw new AssertionError();
        } else if (this.outboundCtx.removed) {
            this.outboundCtx.flush();
        } else {
            this.outboundHandler.flush(this.outboundCtx);
        }
    }
}
