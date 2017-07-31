package io.netty.channel.embedded;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

class EmbeddedChannel$1 implements ChannelFutureListener {
    final /* synthetic */ EmbeddedChannel this$0;

    EmbeddedChannel$1(EmbeddedChannel embeddedChannel) {
        this.this$0 = embeddedChannel;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        EmbeddedChannel.access$000(this.this$0, future);
    }
}
