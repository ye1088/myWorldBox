package io.netty.channel.embedded;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

class EmbeddedChannel$2 extends ChannelInitializer<Channel> {
    final /* synthetic */ EmbeddedChannel this$0;
    final /* synthetic */ ChannelHandler[] val$handlers;

    EmbeddedChannel$2(EmbeddedChannel embeddedChannel, ChannelHandler[] channelHandlerArr) {
        this.this$0 = embeddedChannel;
        this.val$handlers = channelHandlerArr;
    }

    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        ChannelHandler[] arr$ = this.val$handlers;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$ && arr$[i$] != null) {
            pipeline.addLast(arr$[i$]);
            i$++;
        }
    }
}
