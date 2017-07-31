package io.netty.channel.oio;

import io.netty.channel.AbstractChannel.AbstractUnsafe;
import io.netty.channel.ChannelPromise;
import java.net.SocketAddress;

final class AbstractOioChannel$DefaultOioUnsafe extends AbstractUnsafe {
    final /* synthetic */ AbstractOioChannel this$0;

    private AbstractOioChannel$DefaultOioUnsafe(AbstractOioChannel abstractOioChannel) {
        this.this$0 = abstractOioChannel;
        super(abstractOioChannel);
    }

    public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        if (promise.setUncancellable() && ensureOpen(promise)) {
            try {
                boolean wasActive = this.this$0.isActive();
                this.this$0.doConnect(remoteAddress, localAddress);
                boolean active = this.this$0.isActive();
                safeSetSuccess(promise);
                if (!wasActive && active) {
                    this.this$0.pipeline().fireChannelActive();
                }
            } catch (Throwable t) {
                safeSetFailure(promise, annotateConnectException(t, remoteAddress));
                closeIfClosed();
            }
        }
    }
}
