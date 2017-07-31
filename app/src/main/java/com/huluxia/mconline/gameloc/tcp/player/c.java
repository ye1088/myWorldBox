package com.huluxia.mconline.gameloc.tcp.player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/* compiled from: PlayerTcpClientInitializer */
public class c extends ChannelInitializer<SocketChannel> {
    protected /* synthetic */ void initChannel(Channel channel) throws Exception {
        a((SocketChannel) channel);
    }

    protected void a(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast("handler", new b());
        pipeline.addLast("encoder", new LengthFieldPrepender(4, false));
    }
}
