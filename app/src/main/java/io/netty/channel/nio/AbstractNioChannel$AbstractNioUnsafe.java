package io.netty.channel.nio;

import io.netty.channel.AbstractChannel.AbstractUnsafe;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import java.net.SocketAddress;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.TimeUnit;

protected abstract class AbstractNioChannel$AbstractNioUnsafe extends AbstractUnsafe implements AbstractNioChannel$NioUnsafe {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractNioChannel.class.desiredAssertionStatus());
    final /* synthetic */ AbstractNioChannel this$0;

    protected AbstractNioChannel$AbstractNioUnsafe(AbstractNioChannel abstractNioChannel) {
        this.this$0 = abstractNioChannel;
        super(abstractNioChannel);
    }

    protected final void removeReadOp() {
        SelectionKey key = this.this$0.selectionKey();
        if (key.isValid()) {
            int interestOps = key.interestOps();
            if ((this.this$0.readInterestOp & interestOps) != 0) {
                key.interestOps((this.this$0.readInterestOp ^ -1) & interestOps);
            }
        }
    }

    public final SelectableChannel ch() {
        return this.this$0.javaChannel();
    }

    public final void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        if (promise.setUncancellable() && ensureOpen(promise)) {
            try {
                if (AbstractNioChannel.access$200(this.this$0) != null) {
                    throw new ConnectionPendingException();
                }
                boolean wasActive = this.this$0.isActive();
                if (this.this$0.doConnect(remoteAddress, localAddress)) {
                    fulfillConnectPromise(promise, wasActive);
                    return;
                }
                AbstractNioChannel.access$202(this.this$0, promise);
                AbstractNioChannel.access$302(this.this$0, remoteAddress);
                int connectTimeoutMillis = this.this$0.config().getConnectTimeoutMillis();
                if (connectTimeoutMillis > 0) {
                    AbstractNioChannel.access$402(this.this$0, this.this$0.eventLoop().schedule(new Runnable() {
                        public void run() {
                            ChannelPromise connectPromise = AbstractNioChannel.access$200(AbstractNioChannel$AbstractNioUnsafe.this.this$0);
                            ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
                            if (connectPromise != null && connectPromise.tryFailure(cause)) {
                                AbstractNioChannel$AbstractNioUnsafe.this.close(AbstractNioChannel$AbstractNioUnsafe.this.voidPromise());
                            }
                        }
                    }, (long) connectTimeoutMillis, TimeUnit.MILLISECONDS));
                }
                promise.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isCancelled()) {
                            if (AbstractNioChannel.access$400(AbstractNioChannel$AbstractNioUnsafe.this.this$0) != null) {
                                AbstractNioChannel.access$400(AbstractNioChannel$AbstractNioUnsafe.this.this$0).cancel(false);
                            }
                            AbstractNioChannel.access$202(AbstractNioChannel$AbstractNioUnsafe.this.this$0, null);
                            AbstractNioChannel$AbstractNioUnsafe.this.close(AbstractNioChannel$AbstractNioUnsafe.this.voidPromise());
                        }
                    }
                });
            } catch (Throwable t) {
                promise.tryFailure(annotateConnectException(t, remoteAddress));
                closeIfClosed();
            }
        }
    }

    private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
        if (promise != null) {
            boolean active = this.this$0.isActive();
            boolean promiseSet = promise.trySuccess();
            if (!wasActive && active) {
                this.this$0.pipeline().fireChannelActive();
            }
            if (!promiseSet) {
                close(voidPromise());
            }
        }
    }

    private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
        if (promise != null) {
            promise.tryFailure(cause);
            closeIfClosed();
        }
    }

    public final void finishConnect() {
        if ($assertionsDisabled || this.this$0.eventLoop().inEventLoop()) {
            try {
                boolean wasActive = this.this$0.isActive();
                this.this$0.doFinishConnect();
                fulfillConnectPromise(AbstractNioChannel.access$200(this.this$0), wasActive);
            } catch (Throwable t) {
                fulfillConnectPromise(AbstractNioChannel.access$200(this.this$0), annotateConnectException(t, AbstractNioChannel.access$300(this.this$0)));
            } finally {
                if (AbstractNioChannel.access$400(this.this$0) != null) {
                    AbstractNioChannel.access$400(this.this$0).cancel(false);
                }
                AbstractNioChannel.access$202(this.this$0, null);
            }
            return;
        }
        throw new AssertionError();
    }

    protected final void flush0() {
        if (!isFlushPending()) {
            super.flush0();
        }
    }

    public final void forceFlush() {
        super.flush0();
    }

    private boolean isFlushPending() {
        SelectionKey selectionKey = this.this$0.selectionKey();
        return selectionKey.isValid() && (selectionKey.interestOps() & 4) != 0;
    }
}
