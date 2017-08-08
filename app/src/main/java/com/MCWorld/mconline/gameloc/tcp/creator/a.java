package com.MCWorld.mconline.gameloc.tcp.creator;

import com.MCWorld.framework.base.log.HLog;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/* compiled from: CreatorTCPClient */
public class a extends ChannelInboundHandlerAdapter {
    private static String TAG = "PlayerTcpClient";
    private static a aly;
    private Channel alx = null;

    public static synchronized a AT() {
        a aVar;
        synchronized (a.class) {
            if (aly == null) {
                aly = new a();
            }
            aVar = aly;
        }
        return aVar;
    }

    public void F(String inputOnlineServerIP, int inputOnlineServerPort) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            ((Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) b.group(group)).channel(NioSocketChannel.class)).option(ChannelOption.SO_SNDBUF, Integer.valueOf(2048))).option(ChannelOption.TCP_NODELAY, Boolean.valueOf(true))).handler(new c());
            this.alx = b.connect(inputOnlineServerIP, inputOnlineServerPort).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public void a(ByteBuf data) {
        if (this.alx != null) {
            this.alx.writeAndFlush(data);
        }
    }

    public void ao(Object data) {
        if (this.alx != null) {
            this.alx.writeAndFlush(data);
        }
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HLog.verbose(TAG, "[PlayerTcpClient] channelActive ... " + ctx.channel().localAddress(), new Object[0]);
        super.channelActive(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HLog.verbose(TAG, "[PlayerTcpClient] channelInactive ... " + ctx.channel().localAddress(), new Object[0]);
        super.channelInactive(ctx);
    }
}
