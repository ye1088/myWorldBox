package com.MCWorld.mconline.gameloc.tcp.creator;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mconline.gameloc.udp.creator.a;
import com.MCWorld.mconline.proto.c;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import java.net.InetSocketAddress;

/* compiled from: CreatorTCPClientHandler */
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
        a _tmpProxyGamePlayer;
        switch (_receivePackData.amM) {
            case 1282:
                if (-1 == _receivePackData.amN) {
                    com.MCWorld.mconline.gamerole.b.Bh().hR(-1);
                    com.MCWorld.mconline.gameloc.udp.creator.c.cf(false);
                    HLog.error(TAG, "DTPrint 服务器返回玩家进入房间失败 ... \n", new Object[0]);
                    break;
                }
                com.MCWorld.mconline.gamerole.b.Bh().hR(_receivePackData.amN);
                com.MCWorld.mconline.gamerole.b.Bh().dj(_receivePackData.amK);
                com.MCWorld.mconline.gamerole.b.Bh().hQ(_receivePackData.amL);
                com.MCWorld.mconline.gameloc.udp.creator.c.cf(true);
                break;
            case com.MCWorld.mconline.proto.a.amx /*1287*/:
                _tmpProxyGamePlayer = com.MCWorld.mconline.gameloc.udp.creator.b.AX().H(_receivePackData.amI, _receivePackData.amJ);
                if (_tmpProxyGamePlayer == null) {
                    HLog.error(TAG, "DTPrint ERROR 线上服务器->[19132] 代理玩家获取失败\n", new Object[0]);
                }
                msg.readerIndex(gameDataStartIndex);
                _tmpProxyGamePlayer.a(new DatagramPacket(msg.retain(), new InetSocketAddress(com.MCWorld.mconline.utils.a.getHostAddress(), com.MCWorld.mconline.gameloc.config.b.alt)));
                break;
            case com.MCWorld.mconline.proto.a.amz /*1289*/:
                HLog.verbose(TAG, "EDTPrint [游戏玩家]->[线上服务器]->[本地服务器] => 请求从游戏房间移除玩家 CMD [%02X] -- [%s:%d] \n", new Object[]{Integer.valueOf(gameProxyId), _receivePackData.amI, Integer.valueOf(_receivePackData.amJ)});
                _tmpProxyGamePlayer = com.MCWorld.mconline.gameloc.udp.creator.b.AX().H(_receivePackData.amI, _receivePackData.amJ);
                if (_tmpProxyGamePlayer != null) {
                    _tmpProxyGamePlayer.AV();
                    com.MCWorld.mconline.gameloc.udp.creator.b.AX().a(_tmpProxyGamePlayer);
                    break;
                }
                HLog.error(TAG, "DTPrint ERROR 线上服务器->[19132] 代理玩家获取失败\n", new Object[0]);
                break;
            case com.MCWorld.mconline.proto.a.amD /*1293*/:
                com.MCWorld.mconline.proto.b.BU().a(ctx.channel(), _receivePackData.amN, _receivePackData.amK, _receivePackData.amL);
                break;
            case 2048:
                HLog.verbose(TAG, "DTPrint  PROTO_ID_TEST 线上服务器->[本地服务器] 测试消息\n", new Object[0]);
                break;
        }
        msg.release();
    }
}
