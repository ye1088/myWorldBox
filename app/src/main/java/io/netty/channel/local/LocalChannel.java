package io.netty.channel.local;

import io.netty.channel.AbstractChannel;
import io.netty.channel.AbstractChannel.AbstractUnsafe;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import java.net.ConnectException;
import java.net.SocketAddress;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class LocalChannel extends AbstractChannel {
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), LocalChannel.class, "doClose()"));
    private static final ClosedChannelException DO_WRITE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), LocalChannel.class, "doWrite(...)"));
    private static final AtomicReferenceFieldUpdater<LocalChannel, Future> FINISH_READ_FUTURE_UPDATER;
    private static final int MAX_READER_STACK_DEPTH = 8;
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private final ChannelConfig config = new DefaultChannelConfig(this);
    private volatile ChannelPromise connectPromise;
    private volatile Future<?> finishReadFuture;
    private final Queue<Object> inboundBuffer = PlatformDependent.newSpscQueue();
    private volatile LocalAddress localAddress;
    private volatile LocalChannel peer;
    private volatile boolean readInProgress;
    private final Runnable readTask = new Runnable() {
        public void run() {
            ChannelPipeline pipeline = LocalChannel.this.pipeline();
            while (true) {
                Object m = LocalChannel.this.inboundBuffer.poll();
                if (m == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(m);
            }
        }
    };
    private volatile boolean registerInProgress;
    private volatile LocalAddress remoteAddress;
    private final Runnable shutdownHook = new Runnable() {
        public void run() {
            LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
        }
    };
    private volatile State state;
    private volatile boolean writeInProgress;

    private class LocalUnsafe extends AbstractUnsafe {
        private LocalUnsafe() {
            super(LocalChannel.this);
        }

        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            if (!promise.setUncancellable() || !ensureOpen(promise)) {
                return;
            }
            if (LocalChannel.this.state == State.CONNECTED) {
                Exception cause = new AlreadyConnectedException();
                safeSetFailure(promise, cause);
                LocalChannel.this.pipeline().fireExceptionCaught(cause);
            } else if (LocalChannel.this.connectPromise != null) {
                throw new ConnectionPendingException();
            } else {
                LocalChannel.this.connectPromise = promise;
                if (LocalChannel.this.state != State.BOUND && localAddress == null) {
                    localAddress = new LocalAddress(LocalChannel.this);
                }
                if (localAddress != null) {
                    try {
                        LocalChannel.this.doBind(localAddress);
                    } catch (Throwable t) {
                        safeSetFailure(promise, t);
                        close(voidPromise());
                        return;
                    }
                }
                Channel boundChannel = LocalChannelRegistry.get(remoteAddress);
                if (boundChannel instanceof LocalServerChannel) {
                    LocalChannel.this.peer = ((LocalServerChannel) boundChannel).serve(LocalChannel.this);
                    return;
                }
                safeSetFailure(promise, new ConnectException("connection refused: " + remoteAddress));
                close(voidPromise());
            }
        }
    }

    private enum State {
        OPEN,
        BOUND,
        CONNECTED,
        CLOSED
    }

    static {
        AtomicReferenceFieldUpdater<LocalChannel, Future> finishReadFutureUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(LocalChannel.class, "finishReadFuture");
        if (finishReadFutureUpdater == null) {
            finishReadFutureUpdater = AtomicReferenceFieldUpdater.newUpdater(LocalChannel.class, Future.class, "finishReadFuture");
        }
        FINISH_READ_FUTURE_UPDATER = finishReadFutureUpdater;
    }

    public LocalChannel() {
        super(null);
    }

    LocalChannel(LocalServerChannel parent, LocalChannel peer) {
        super(parent);
        this.peer = peer;
        this.localAddress = parent.localAddress();
        this.remoteAddress = peer.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public ChannelConfig config() {
        return this.config;
    }

    public LocalServerChannel parent() {
        return (LocalServerChannel) super.parent();
    }

    public LocalAddress localAddress() {
        return (LocalAddress) super.localAddress();
    }

    public LocalAddress remoteAddress() {
        return (LocalAddress) super.remoteAddress();
    }

    public boolean isOpen() {
        return this.state != State.CLOSED;
    }

    public boolean isActive() {
        return this.state == State.CONNECTED;
    }

    protected AbstractUnsafe newUnsafe() {
        return new LocalUnsafe();
    }

    protected boolean isCompatible(EventLoop loop) {
        return loop instanceof SingleThreadEventLoop;
    }

    protected SocketAddress localAddress0() {
        return this.localAddress;
    }

    protected SocketAddress remoteAddress0() {
        return this.remoteAddress;
    }

    protected void doRegister() throws Exception {
        if (!(this.peer == null || parent() == null)) {
            final LocalChannel peer = this.peer;
            this.registerInProgress = true;
            this.state = State.CONNECTED;
            peer.remoteAddress = parent() == null ? null : parent().localAddress();
            peer.state = State.CONNECTED;
            peer.eventLoop().execute(new Runnable() {
                public void run() {
                    LocalChannel.this.registerInProgress = false;
                    ChannelPromise promise = peer.connectPromise;
                    if (promise != null && promise.trySuccess()) {
                        peer.pipeline().fireChannelActive();
                    }
                }
            });
        }
        ((SingleThreadEventExecutor) eventLoop()).addShutdownHook(this.shutdownHook);
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, localAddress);
        this.state = State.BOUND;
    }

    protected void doDisconnect() throws Exception {
        doClose();
    }

    protected void doClose() throws Exception {
        final LocalChannel peer = this.peer;
        if (this.state != State.CLOSED) {
            if (this.localAddress != null) {
                if (parent() == null) {
                    LocalChannelRegistry.unregister(this.localAddress);
                }
                this.localAddress = null;
            }
            this.state = State.CLOSED;
            ChannelPromise promise = this.connectPromise;
            if (promise != null) {
                promise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
                this.connectPromise = null;
            }
            if (this.writeInProgress && peer != null) {
                finishPeerRead(peer);
            }
        }
        if (peer != null && peer.isActive()) {
            if (!peer.eventLoop().inEventLoop() || this.registerInProgress) {
                final boolean peerWriteInProgress = peer.writeInProgress;
                try {
                    peer.eventLoop().execute(new Runnable() {
                        public void run() {
                            LocalChannel.this.doPeerClose(peer, peerWriteInProgress);
                        }
                    });
                } catch (RuntimeException e) {
                    releaseInboundBuffers();
                    throw e;
                }
            }
            doPeerClose(peer, peer.writeInProgress);
            this.peer = null;
        }
    }

    private void doPeerClose(LocalChannel peer, boolean peerWriteInProgress) {
        if (peerWriteInProgress) {
            finishPeerRead0(this);
        }
        peer.unsafe().close(peer.unsafe().voidPromise());
    }

    protected void doDeregister() throws Exception {
        ((SingleThreadEventExecutor) eventLoop()).removeShutdownHook(this.shutdownHook);
    }

    protected void doBeginRead() throws Exception {
        if (!this.readInProgress) {
            ChannelPipeline pipeline = pipeline();
            Queue<Object> inboundBuffer = this.inboundBuffer;
            if (inboundBuffer.isEmpty()) {
                this.readInProgress = true;
                return;
            }
            InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
            Integer stackDepth = Integer.valueOf(threadLocals.localChannelReaderStackDepth());
            if (stackDepth.intValue() < 8) {
                threadLocals.setLocalChannelReaderStackDepth(stackDepth.intValue() + 1);
                while (true) {
                    try {
                        Object received = inboundBuffer.poll();
                        if (received == null) {
                            break;
                        }
                        pipeline.fireChannelRead(received);
                    } finally {
                        threadLocals.setLocalChannelReaderStackDepth(stackDepth.intValue());
                    }
                }
                pipeline.fireChannelReadComplete();
                return;
            }
            try {
                eventLoop().execute(this.readTask);
            } catch (RuntimeException e) {
                releaseInboundBuffers();
                throw e;
            }
        }
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        switch (this.state) {
            case OPEN:
            case BOUND:
                throw new NotYetConnectedException();
            case CLOSED:
                throw DO_WRITE_CLOSED_CHANNEL_EXCEPTION;
            default:
                LocalChannel peer = this.peer;
                this.writeInProgress = true;
                while (true) {
                    try {
                        Object msg = in.current();
                        if (msg == null) {
                            this.writeInProgress = false;
                            finishPeerRead(peer);
                            return;
                        } else if (peer.state == State.CONNECTED) {
                            peer.inboundBuffer.add(ReferenceCountUtil.retain(msg));
                            in.remove();
                        } else {
                            in.remove(DO_WRITE_CLOSED_CHANNEL_EXCEPTION);
                        }
                    } catch (Throwable th) {
                        this.writeInProgress = false;
                    }
                }
        }
    }

    private void finishPeerRead(LocalChannel peer) {
        if (peer.eventLoop() != eventLoop() || peer.writeInProgress) {
            runFinishPeerReadTask(peer);
        } else {
            finishPeerRead0(peer);
        }
    }

    private void runFinishPeerReadTask(final LocalChannel peer) {
        Runnable finishPeerReadTask = new Runnable() {
            public void run() {
                LocalChannel.this.finishPeerRead0(peer);
            }
        };
        try {
            if (peer.writeInProgress) {
                peer.finishReadFuture = peer.eventLoop().submit(finishPeerReadTask);
            } else {
                peer.eventLoop().execute(finishPeerReadTask);
            }
        } catch (RuntimeException e) {
            peer.releaseInboundBuffers();
            throw e;
        }
    }

    private void releaseInboundBuffers() {
        while (true) {
            Object o = this.inboundBuffer.poll();
            if (o != null) {
                ReferenceCountUtil.release(o);
            } else {
                return;
            }
        }
    }

    private void finishPeerRead0(LocalChannel peer) {
        Future<?> peerFinishReadFuture = peer.finishReadFuture;
        if (peerFinishReadFuture != null) {
            if (peerFinishReadFuture.isDone()) {
                FINISH_READ_FUTURE_UPDATER.compareAndSet(peer, peerFinishReadFuture, null);
            } else {
                runFinishPeerReadTask(peer);
                return;
            }
        }
        ChannelPipeline peerPipeline = peer.pipeline();
        if (peer.readInProgress) {
            peer.readInProgress = false;
            while (true) {
                Object received = peer.inboundBuffer.poll();
                if (received == null) {
                    peerPipeline.fireChannelReadComplete();
                    return;
                }
                peerPipeline.fireChannelRead(received);
            }
        }
    }
}
