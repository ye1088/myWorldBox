package com.MCWorld.mconline.gameloc.tcp.player;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mconline.gameloc.udp.player.a;
import com.MCWorld.mconline.proto.c;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import java.net.InetSocketAddress;

/* compiled from: PlayerTcpClientHandler */
public class b extends SimpleChannelInboundHandler<ByteBuf> {
    private static String TAG = "PlayerTcpClientHandler";
    private static final boolean alz = false;

    protected /* synthetic */ void channelRead0(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        a(channelHandlerContext, (ByteBuf) obj);
    }

    protected void a(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        c _receivePackData = new c();
        msg.markReaderIndex();
        int gameDataStartIndex = _receivePackData.a(msg.retain(), true);
        int gameProxyId = _receivePackData.amU & 255;
        msg.resetReaderIndex();
        switch (_receivePackData.amM) {
            case 1284:
                a.alQ = _receivePackData.amN;
                a.alO = _receivePackData.amK;
                a.alP = _receivePackData.amL;
                a.alM = _receivePackData.amI;
                a.alN = _receivePackData.amJ;
                com.MCWorld.mconline.gameloc.udp.player.b.cg(true);
                break;
            case com.MCWorld.mconline.proto.a.amy /*1288*/:
                msg.readerIndex(gameDataStartIndex);
                if (gameProxyId != 28) {
                    a.Ba().a(new DatagramPacket(msg.retain(), new InetSocketAddress(com.MCWorld.mconline.utils.a.getHostAddress(), com.MCWorld.mconline.gamerole.a.Bf().alW)));
                    break;
                }
                a.Ba().a(new DatagramPacket(msg.retain(), new InetSocketAddress(com.MCWorld.mconline.utils.a.getHostAddress(), com.MCWorld.mconline.gamerole.a.Bf().alY)));
                break;
            case com.MCWorld.mconline.proto.a.amz /*1289*/:
                HLog.verbose(TAG, "EDTPrint [线上服务器]->[游戏玩家]=> 从游戏房间移除玩家 CMD [%02X] -- [%s:%d]=>[%s:%d] \n", new Object[]{Integer.valueOf(gameProxyId), _receivePackData.amI, Integer.valueOf(_receivePackData.amJ), _receivePackData.amK, Integer.valueOf(_receivePackData.amL)});
                com.MCWorld.mconline.proto.b.BU().a(ctx.channel(), _receivePackData.amN, _receivePackData.amI, _receivePackData.amJ, _receivePackData.amK, _receivePackData.amL);
                break;
            case com.MCWorld.mconline.proto.a.amB /*1291*/:
                HLog.verbose(TAG, "EDTPrint [线上服务器]->[游戏玩家]=> 从游戏房间移除服主 CMD [%02X] -- [%s:%d]=>[%s:%d] \n", new Object[]{Integer.valueOf(gameProxyId), _receivePackData.amI, Integer.valueOf(_receivePackData.amJ), _receivePackData.amK, Integer.valueOf(_receivePackData.amL)});
                break;
        }
        msg.release();
    }
}
