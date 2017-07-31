package io.netty.channel.embedded;

import io.netty.channel.DefaultChannelPipeline;

final class EmbeddedChannel$EmbeddedChannelPipeline extends DefaultChannelPipeline {
    final /* synthetic */ EmbeddedChannel this$0;

    public EmbeddedChannel$EmbeddedChannelPipeline(EmbeddedChannel embeddedChannel, EmbeddedChannel channel) {
        this.this$0 = embeddedChannel;
        super(channel);
    }

    protected void onUnhandledInboundException(Throwable cause) {
        EmbeddedChannel.access$200(this.this$0, cause);
    }

    protected void onUnhandledInboundMessage(Object msg) {
        this.this$0.inboundMessages().add(msg);
    }
}
