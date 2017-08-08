package com.MCWorld.mconline.gameloc.udp.creator;

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

/* compiled from: ProxyGamePlayer */
public class a extends ChannelInboundHandlerAdapter {
    private static final String TAG = "ProxyGamePlayer";
    public boolean agK = false;
    public int alB = 0;
    public int alC = 0;
    public String alD;
    private ConcurrentLinkedQueue<DatagramPacket> alE = new ConcurrentLinkedQueue();
    private Channel ch;
    private EventLoopGroup group;

    public a(String inRemoteIp, int inRemotePort) {
        try {
            bI(false);
            G(inRemoteIp, inRemotePort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean G(String inRemoteIp, int inRemotePort) throws Exception {
        this.alD = inRemoteIp;
        this.alC = inRemotePort;
        this.alB = com.MCWorld.mconline.utils.a.BY();
        if (this.alB == 0) {
            HLog.verbose(TAG, "DTPrint 本地服务器没有空闲的端口号,创建玩家失败!!!", new Object[0]);
            return false;
        }
        this.group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            ((Bootstrap) ((Bootstrap) ((Bootstrap) b.group(this.group)).channel(NioDatagramChannel.class)).option(ChannelOption.SO_BROADCAST, Boolean.valueOf(true))).handler(this);
            this.ch = b.bind(this.alB).sync().channel();
            bI(true);
            return true;
        } catch (Exception e) {
            HLog.verbose(TAG, "DTERROR ProxyGamePlayer boot failed !", new Object[0]);
            this.group.shutdownGracefully();
            this.ch.close();
            return false;
        }
    }

    public void AV() {
        if (isValid()) {
            try {
                HLog.verbose(TAG, "DTPrint 端口[%d]开始销毁 ...", new Object[]{Integer.valueOf(this.alB)});
                this.ch.closeFuture();
                this.ch.close();
                this.group.shutdownGracefully();
                this.alD = null;
                this.alC = 0;
                bI(false);
            } catch (Exception e) {
            }
        }
        bI(false);
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

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.alE.add((DatagramPacket) msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public boolean isValid() {
        return this.agK;
    }

    public void bI(boolean valid) {
        this.agK = valid;
    }
}
