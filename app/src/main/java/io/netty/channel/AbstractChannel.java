package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel.Unsafe;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public abstract class AbstractChannel extends DefaultAttributeMap implements Channel {
    private static final ClosedChannelException CLOSE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "close(...)"));
    private static final ClosedChannelException ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "ensureOpen(...)"));
    private static final ClosedChannelException FLUSH0_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "flush0()"));
    private static final NotYetConnectedException FLUSH0_NOT_YET_CONNECTED_EXCEPTION = ((NotYetConnectedException) ThrowableUtil.unknownStackTrace(new NotYetConnectedException(), AbstractUnsafe.class, "flush0()"));
    private static final ClosedChannelException WRITE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "write(...)"));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
    private final CloseFuture closeFuture = new CloseFuture(this);
    private volatile EventLoop eventLoop;
    private final ChannelId id;
    private volatile SocketAddress localAddress;
    private final Channel parent;
    private final DefaultChannelPipeline pipeline;
    private volatile boolean registered;
    private volatile SocketAddress remoteAddress;
    private String strVal;
    private boolean strValActive;
    private final Unsafe unsafe;
    private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);

    protected abstract class AbstractUnsafe implements Unsafe {
        static final /* synthetic */ boolean $assertionsDisabled = (!AbstractChannel.class.desiredAssertionStatus());
        private boolean inFlush0;
        private boolean neverRegistered = true;
        private volatile ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
        private Handle recvHandle;

        protected AbstractUnsafe() {
        }

        private void assertEventLoop() {
            if (!$assertionsDisabled && AbstractChannel.this.registered && !AbstractChannel.this.eventLoop.inEventLoop()) {
                throw new AssertionError();
            }
        }

        public Handle recvBufAllocHandle() {
            if (this.recvHandle == null) {
                this.recvHandle = AbstractChannel.this.config().getRecvByteBufAllocator().newHandle();
            }
            return this.recvHandle;
        }

        public final ChannelOutboundBuffer outboundBuffer() {
            return this.outboundBuffer;
        }

        public final SocketAddress localAddress() {
            return AbstractChannel.this.localAddress0();
        }

        public final SocketAddress remoteAddress() {
            return AbstractChannel.this.remoteAddress0();
        }

        public final void register(EventLoop eventLoop, ChannelPromise promise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            } else if (AbstractChannel.this.isRegistered()) {
                promise.setFailure(new IllegalStateException("registered to an event loop already"));
            } else if (AbstractChannel.this.isCompatible(eventLoop)) {
                AbstractChannel.this.eventLoop = eventLoop;
                if (eventLoop.inEventLoop()) {
                    register0(promise);
                    return;
                }
                try {
                    eventLoop.execute(new 1(this, promise));
                } catch (Throwable t) {
                    AbstractChannel.logger.warn("Force-closing a_isRightVersion channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, t);
                    closeForcibly();
                    AbstractChannel.this.closeFuture.setClosed();
                    safeSetFailure(promise, t);
                }
            } else {
                promise.setFailure(new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
            }
        }

        private void register0(ChannelPromise promise) {
            try {
                if (promise.setUncancellable() && ensureOpen(promise)) {
                    boolean firstRegistration = this.neverRegistered;
                    AbstractChannel.this.doRegister();
                    this.neverRegistered = false;
                    AbstractChannel.this.registered = true;
                    AbstractChannel.this.pipeline.invokeHandlerAddedIfNeeded();
                    safeSetSuccess(promise);
                    AbstractChannel.this.pipeline.fireChannelRegistered();
                    if (!AbstractChannel.this.isActive()) {
                        return;
                    }
                    if (firstRegistration) {
                        AbstractChannel.this.pipeline.fireChannelActive();
                    } else if (AbstractChannel.this.config().isAutoRead()) {
                        beginRead();
                    }
                }
            } catch (Throwable t) {
                closeForcibly();
                AbstractChannel.this.closeFuture.setClosed();
                safeSetFailure(promise, t);
            }
        }

        public final void bind(SocketAddress localAddress, ChannelPromise promise) {
            assertEventLoop();
            if (promise.setUncancellable() && ensureOpen(promise)) {
                if (!(!Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST)) || !(localAddress instanceof InetSocketAddress) || ((InetSocketAddress) localAddress).getAddress().isAnyLocalAddress() || PlatformDependent.isWindows() || PlatformDependent.isRoot())) {
                    AbstractChannel.logger.warn("A non-root user can't receive a_isRightVersion broadcast packet if the socket is not bound to a_isRightVersion wildcard address; binding to a_isRightVersion non-wildcard address (" + localAddress + ") anyway as requested.");
                }
                boolean wasActive = AbstractChannel.this.isActive();
                try {
                    AbstractChannel.this.doBind(localAddress);
                    if (!wasActive && AbstractChannel.this.isActive()) {
                        invokeLater(new 2(this));
                    }
                    safeSetSuccess(promise);
                } catch (Throwable t) {
                    safeSetFailure(promise, t);
                    closeIfClosed();
                }
            }
        }

        public final void disconnect(ChannelPromise promise) {
            assertEventLoop();
            if (promise.setUncancellable()) {
                boolean wasActive = AbstractChannel.this.isActive();
                try {
                    AbstractChannel.this.doDisconnect();
                    if (wasActive && !AbstractChannel.this.isActive()) {
                        invokeLater(new 3(this));
                    }
                    safeSetSuccess(promise);
                    closeIfClosed();
                } catch (Throwable t) {
                    safeSetFailure(promise, t);
                    closeIfClosed();
                }
            }
        }

        public final void close(ChannelPromise promise) {
            assertEventLoop();
            close(promise, AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION, AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION, false);
        }

        private void close(ChannelPromise promise, Throwable cause, ClosedChannelException closeCause, boolean notify) {
            if (promise.setUncancellable()) {
                ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
                if (outboundBuffer == null) {
                    if (!(promise instanceof VoidChannelPromise)) {
                        AbstractChannel.this.closeFuture.addListener(new 4(this, promise));
                    }
                } else if (AbstractChannel.this.closeFuture.isDone()) {
                    safeSetSuccess(promise);
                } else {
                    boolean wasActive = AbstractChannel.this.isActive();
                    this.outboundBuffer = null;
                    Executor closeExecutor = prepareToClose();
                    if (closeExecutor != null) {
                        closeExecutor.execute(new 5(this, promise, outboundBuffer, cause, notify, closeCause, wasActive));
                        return;
                    }
                    try {
                        doClose0(promise);
                        if (this.inFlush0) {
                            invokeLater(new 6(this, wasActive));
                        } else {
                            fireChannelInactiveAndDeregister(wasActive);
                        }
                    } finally {
                        outboundBuffer.failFlushed(cause, notify);
                        outboundBuffer.close(closeCause);
                    }
                }
            }
        }

        private void doClose0(ChannelPromise promise) {
            try {
                AbstractChannel.this.doClose();
                AbstractChannel.this.closeFuture.setClosed();
                safeSetSuccess(promise);
            } catch (Throwable t) {
                AbstractChannel.this.closeFuture.setClosed();
                safeSetFailure(promise, t);
            }
        }

        private void fireChannelInactiveAndDeregister(boolean wasActive) {
            ChannelPromise voidPromise = voidPromise();
            boolean z = wasActive && !AbstractChannel.this.isActive();
            deregister(voidPromise, z);
        }

        public final void closeForcibly() {
            assertEventLoop();
            try {
                AbstractChannel.this.doClose();
            } catch (Exception e) {
                AbstractChannel.logger.warn("Failed to close a_isRightVersion channel.", e);
            }
        }

        public final void deregister(ChannelPromise promise) {
            assertEventLoop();
            deregister(promise, false);
        }

        private void deregister(ChannelPromise promise, boolean fireChannelInactive) {
            if (!promise.setUncancellable()) {
                return;
            }
            if (AbstractChannel.this.registered) {
                invokeLater(new 7(this, fireChannelInactive, promise));
            } else {
                safeSetSuccess(promise);
            }
        }

        public final void beginRead() {
            assertEventLoop();
            if (AbstractChannel.this.isActive()) {
                try {
                    AbstractChannel.this.doBeginRead();
                } catch (Exception e) {
                    invokeLater(new 8(this, e));
                    close(voidPromise());
                }
            }
        }

        public final void write(Object msg, ChannelPromise promise) {
            assertEventLoop();
            ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
            if (outboundBuffer == null) {
                safeSetFailure(promise, AbstractChannel.WRITE_CLOSED_CHANNEL_EXCEPTION);
                ReferenceCountUtil.release(msg);
                return;
            }
            try {
                msg = AbstractChannel.this.filterOutboundMessage(msg);
                int size = AbstractChannel.this.pipeline.estimatorHandle().size(msg);
                if (size < 0) {
                    size = 0;
                }
                outboundBuffer.addMessage(msg, size, promise);
            } catch (Throwable t) {
                safeSetFailure(promise, t);
                ReferenceCountUtil.release(msg);
            }
        }

        public final void flush() {
            assertEventLoop();
            ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
            if (outboundBuffer != null) {
                outboundBuffer.addFlush();
                flush0();
            }
        }

        protected void flush0() {
            if (!this.inFlush0) {
                ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
                if (outboundBuffer != null && !outboundBuffer.isEmpty()) {
                    this.inFlush0 = true;
                    if (AbstractChannel.this.isActive()) {
                        try {
                            AbstractChannel.this.doWrite(outboundBuffer);
                            this.inFlush0 = false;
                        } catch (Throwable th) {
                            this.inFlush0 = false;
                        }
                    } else {
                        try {
                            if (AbstractChannel.this.isOpen()) {
                                outboundBuffer.failFlushed(AbstractChannel.FLUSH0_NOT_YET_CONNECTED_EXCEPTION, true);
                            } else {
                                outboundBuffer.failFlushed(AbstractChannel.FLUSH0_CLOSED_CHANNEL_EXCEPTION, false);
                            }
                            this.inFlush0 = false;
                        } catch (Throwable th2) {
                            this.inFlush0 = false;
                        }
                    }
                }
            }
        }

        public final ChannelPromise voidPromise() {
            assertEventLoop();
            return AbstractChannel.this.unsafeVoidPromise;
        }

        @Deprecated
        protected final boolean ensureOpen(ChannelPromise promise) {
            if (AbstractChannel.this.isOpen()) {
                return true;
            }
            safeSetFailure(promise, AbstractChannel.ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION);
            return false;
        }

        protected final void safeSetSuccess(ChannelPromise promise) {
            if (!(promise instanceof VoidChannelPromise) && !promise.trySuccess()) {
                AbstractChannel.logger.warn("Failed to mark a_isRightVersion promise as success because it is done already: {}", promise);
            }
        }

        protected final void safeSetFailure(ChannelPromise promise, Throwable cause) {
            if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
                AbstractChannel.logger.warn("Failed to mark a_isRightVersion promise as failure because it's done already: {}", promise, cause);
            }
        }

        protected final void closeIfClosed() {
            if (!AbstractChannel.this.isOpen()) {
                close(voidPromise());
            }
        }

        private void invokeLater(Runnable task) {
            try {
                AbstractChannel.this.eventLoop().execute(task);
            } catch (RejectedExecutionException e) {
                AbstractChannel.logger.warn("Can't invoke task later as EventLoop rejected it", e);
            }
        }

        protected final Throwable annotateConnectException(Throwable cause, SocketAddress remoteAddress) {
            if (cause instanceof ConnectException) {
                return new AnnotatedConnectException((ConnectException) cause, remoteAddress);
            }
            if (cause instanceof NoRouteToHostException) {
                return new AnnotatedNoRouteToHostException((NoRouteToHostException) cause, remoteAddress);
            }
            if (cause instanceof SocketException) {
                return new AnnotatedSocketException((SocketException) cause, remoteAddress);
            }
            return cause;
        }

        protected Executor prepareToClose() {
            return null;
        }
    }

    protected abstract void doBeginRead() throws Exception;

    protected abstract void doBind(SocketAddress socketAddress) throws Exception;

    protected abstract void doClose() throws Exception;

    protected abstract void doDisconnect() throws Exception;

    protected abstract void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception;

    protected abstract boolean isCompatible(EventLoop eventLoop);

    protected abstract SocketAddress localAddress0();

    protected abstract AbstractUnsafe newUnsafe();

    protected abstract SocketAddress remoteAddress0();

    protected AbstractChannel(Channel parent) {
        this.parent = parent;
        this.id = newId();
        this.unsafe = newUnsafe();
        this.pipeline = newChannelPipeline();
    }

    protected AbstractChannel(Channel parent, ChannelId id) {
        this.parent = parent;
        this.id = id;
        this.unsafe = newUnsafe();
        this.pipeline = newChannelPipeline();
    }

    public final ChannelId id() {
        return this.id;
    }

    protected ChannelId newId() {
        return DefaultChannelId.newInstance();
    }

    protected DefaultChannelPipeline newChannelPipeline() {
        return new DefaultChannelPipeline(this);
    }

    public boolean isWritable() {
        ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
        return buf != null && buf.isWritable();
    }

    public long bytesBeforeUnwritable() {
        ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
        return buf != null ? buf.bytesBeforeUnwritable() : 0;
    }

    public long bytesBeforeWritable() {
        ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
        return buf != null ? buf.bytesBeforeWritable() : Long.MAX_VALUE;
    }

    public Channel parent() {
        return this.parent;
    }

    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    public ByteBufAllocator alloc() {
        return config().getAllocator();
    }

    public EventLoop eventLoop() {
        EventLoop eventLoop = this.eventLoop;
        if (eventLoop != null) {
            return eventLoop;
        }
        throw new IllegalStateException("channel not registered to an event loop");
    }

    public SocketAddress localAddress() {
        SocketAddress localAddress = this.localAddress;
        if (localAddress == null) {
            try {
                localAddress = unsafe().localAddress();
                this.localAddress = localAddress;
            } catch (Throwable th) {
                return null;
            }
        }
        return localAddress;
    }

    @Deprecated
    protected void invalidateLocalAddress() {
        this.localAddress = null;
    }

    public SocketAddress remoteAddress() {
        SocketAddress remoteAddress = this.remoteAddress;
        if (remoteAddress == null) {
            try {
                remoteAddress = unsafe().remoteAddress();
                this.remoteAddress = remoteAddress;
            } catch (Throwable th) {
                return null;
            }
        }
        return remoteAddress;
    }

    @Deprecated
    protected void invalidateRemoteAddress() {
        this.remoteAddress = null;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public ChannelFuture bind(SocketAddress localAddress) {
        return this.pipeline.bind(localAddress);
    }

    public ChannelFuture connect(SocketAddress remoteAddress) {
        return this.pipeline.connect(remoteAddress);
    }

    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return this.pipeline.connect(remoteAddress, localAddress);
    }

    public ChannelFuture disconnect() {
        return this.pipeline.disconnect();
    }

    public ChannelFuture close() {
        return this.pipeline.close();
    }

    public ChannelFuture deregister() {
        return this.pipeline.deregister();
    }

    public Channel flush() {
        this.pipeline.flush();
        return this;
    }

    public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
        return this.pipeline.bind(localAddress, promise);
    }

    public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
        return this.pipeline.connect(remoteAddress, promise);
    }

    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        return this.pipeline.connect(remoteAddress, localAddress, promise);
    }

    public ChannelFuture disconnect(ChannelPromise promise) {
        return this.pipeline.disconnect(promise);
    }

    public ChannelFuture close(ChannelPromise promise) {
        return this.pipeline.close(promise);
    }

    public ChannelFuture deregister(ChannelPromise promise) {
        return this.pipeline.deregister(promise);
    }

    public Channel read() {
        this.pipeline.read();
        return this;
    }

    public ChannelFuture write(Object msg) {
        return this.pipeline.write(msg);
    }

    public ChannelFuture write(Object msg, ChannelPromise promise) {
        return this.pipeline.write(msg, promise);
    }

    public ChannelFuture writeAndFlush(Object msg) {
        return this.pipeline.writeAndFlush(msg);
    }

    public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
        return this.pipeline.writeAndFlush(msg, promise);
    }

    public ChannelPromise newPromise() {
        return this.pipeline.newPromise();
    }

    public ChannelProgressivePromise newProgressivePromise() {
        return this.pipeline.newProgressivePromise();
    }

    public ChannelFuture newSucceededFuture() {
        return this.pipeline.newSucceededFuture();
    }

    public ChannelFuture newFailedFuture(Throwable cause) {
        return this.pipeline.newFailedFuture(cause);
    }

    public ChannelFuture closeFuture() {
        return this.closeFuture;
    }

    public Unsafe unsafe() {
        return this.unsafe;
    }

    public final int hashCode() {
        return this.id.hashCode();
    }

    public final boolean equals(Object o) {
        return this == o;
    }

    public final int compareTo(Channel o) {
        if (this == o) {
            return 0;
        }
        return id().compareTo(o.id());
    }

    public String toString() {
        boolean active = isActive();
        if (this.strValActive == active && this.strVal != null) {
            return this.strVal;
        }
        SocketAddress remoteAddr = remoteAddress();
        SocketAddress localAddr = localAddress();
        if (remoteAddr != null) {
            this.strVal = "[id: 0x" + this.id.asShortText() + ", L:" + localAddr + (active ? " - " : " ! ") + "R:" + remoteAddr + ']';
        } else if (localAddr != null) {
            this.strVal = "[id: 0x" + this.id.asShortText() + ", L:" + localAddr + ']';
        } else {
            this.strVal = "[id: 0x" + this.id.asShortText() + ']';
        }
        this.strValActive = active;
        return this.strVal;
    }

    public final ChannelPromise voidPromise() {
        return this.pipeline.voidPromise();
    }

    protected void doRegister() throws Exception {
    }

    protected void doDeregister() throws Exception {
    }

    protected Object filterOutboundMessage(Object msg) throws Exception {
        return msg;
    }
}
