package com.MCWorld.mconline.gameloc.udp.player;

import com.MCWorld.framework.base.log.HLog;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: NormalGamePlayer */
public final class a extends ChannelInboundHandlerAdapter {
    private static final String TAG = "NormalGamePlayer";
    public static volatile String alM = "";
    public static volatile int alN = 0;
    public static volatile String alO = "";
    public static volatile int alP = 0;
    public static volatile int alQ = 0;
    private static a alR;
    protected ConcurrentLinkedQueue<DatagramPacket> alE = new ConcurrentLinkedQueue();
    private Channel ch;

    public static synchronized a Ba() {
        a aVar;
        synchronized (a.class) {
            if (alR == null) {
                alR = new a();
            }
            aVar = alR;
        }
        return aVar;
    }

    public static void I(String inputServerIP, int inputServerPort) {
        alM = inputServerIP;
        alN = inputServerPort;
    }

    public void Bb() throws Exception {
        try {
            if (this.ch != null) {
                this.ch.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            ((Bootstrap) ((Bootstrap) ((Bootstrap) b.group(group)).channel(NioDatagramChannel.class)).option(ChannelOption.SO_BROADCAST, Boolean.valueOf(true))).handler(this);
            this.ch = b.bind(b.alt).sync().channel();
        } catch (Exception e2) {
            HLog.error(TAG, "DTERROR NormalGamePlayer boot failed !" + e2, new Object[0]);
            group.shutdownGracefully();
            this.ch.close();
        }
    }

    public void a(DatagramPacket data) {
        try {
            this.ch.writeAndFlush(data).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public DatagramPacket AW() throws IOException {
        return (DatagramPacket) this.alE.poll();
    }

    public void Bc() throws IOException {
        this.alE.clear();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.alE.add((DatagramPacket) msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
