package io.netty.channel.embedded;

import io.netty.channel.AbstractChannel.AbstractUnsafe;
import io.netty.channel.ChannelPromise;
import java.net.SocketAddress;

class EmbeddedChannel$DefaultUnsafe extends AbstractUnsafe {
    final /* synthetic */ EmbeddedChannel this$0;

    private EmbeddedChannel$DefaultUnsafe(EmbeddedChannel embeddedChannel) {
        this.this$0 = embeddedChannel;
        super(embeddedChannel);
    }

    public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        safeSetSuccess(promise);
    }
}
