package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import java.util.Map.Entry;

class ServerBootstrap$1 extends ChannelInitializer<Channel> {
    final /* synthetic */ ServerBootstrap this$0;
    final /* synthetic */ Entry[] val$currentChildAttrs;
    final /* synthetic */ EventLoopGroup val$currentChildGroup;
    final /* synthetic */ ChannelHandler val$currentChildHandler;
    final /* synthetic */ Entry[] val$currentChildOptions;

    ServerBootstrap$1(ServerBootstrap serverBootstrap, EventLoopGroup eventLoopGroup, ChannelHandler channelHandler, Entry[] entryArr, Entry[] entryArr2) {
        this.this$0 = serverBootstrap;
        this.val$currentChildGroup = eventLoopGroup;
        this.val$currentChildHandler = channelHandler;
        this.val$currentChildOptions = entryArr;
        this.val$currentChildAttrs = entryArr2;
    }

    public void initChannel(Channel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        if (ServerBootstrap.access$000(this.this$0).handler() != null) {
            pipeline.addLast(ServerBootstrap.access$000(this.this$0).handler());
        }
        ch.eventLoop().execute(new Runnable() {
            public void run() {
                pipeline.addLast(new ServerBootstrap$ServerBootstrapAcceptor(ServerBootstrap$1.this.val$currentChildGroup, ServerBootstrap$1.this.val$currentChildHandler, ServerBootstrap$1.this.val$currentChildOptions, ServerBootstrap$1.this.val$currentChildAttrs));
            }
        });
    }
}
