package com.huluxia.mconline.gameloc.udp.player;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.mconline.proto.c;
import com.huluxia.mconline.utils.a;
import com.tencent.connect.common.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;
import java.io.IOException;
import java.net.InetSocketAddress;

/* compiled from: NormalGamePlayerSession */
public class b {
    private static final String TAG = "NormalGamePlayerSession";
    private static volatile boolean alS = false;
    static int alT = 0;
    private static b alU = null;
    private static final boolean alz = true;

    public static synchronized b Bd() {
        b bVar;
        synchronized (b.class) {
            if (alU == null) {
                alU = new b();
            } else {
                alU.Be();
            }
            bVar = alU;
        }
        return bVar;
    }

    public void Be() {
        try {
            a.Ba().Bc();
            cg(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public b() {
        try {
            cg(false);
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws Exception {
        AZ();
    }

    private boolean Z(long startTimeStack) throws Exception {
        DatagramPacket datagramPacket = a.Ba().AW();
        if (datagramPacket != null) {
            String _tmpSenderIP = ((InetSocketAddress) datagramPacket.sender()).getAddress().getHostAddress();
            int _tmpSenderPort = ((InetSocketAddress) datagramPacket.sender()).getPort();
            String _tmpRecipientIP = ((InetSocketAddress) datagramPacket.recipient()).getAddress().getHostAddress();
            int _tmpRecipientPort = ((InetSocketAddress) datagramPacket.recipient()).getPort();
            System.out.println("[GameProcess]: DTPrint src[" + _tmpSenderIP + ":" + _tmpSenderPort + "]=>dst[" + _tmpRecipientIP + ":" + _tmpRecipientPort + "]");
            ByteBuf byteBuf = (ByteBuf) datagramPacket.content();
            byte[] packetBuffer = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(packetBuffer);
            byteBuf.release();
            if (packetBuffer.length > 0) {
                HLog.verbose(TAG, "EDTPrint [游戏玩家]->[线上服务器]->[游戏服务器] 00 => [%d] CMD [%02X] -- Len[%d] \n", new Object[]{Integer.valueOf(_tmpSenderPort), Integer.valueOf(packetBuffer[0] & 255), Integer.valueOf(packetBuffer.length)});
                if (_tmpSenderIP.equals(a.getHostAddress())) {
                    if (!alS) {
                        return true;
                    }
                    if (com.huluxia.mconline.gameloc.config.b.alt != _tmpSenderPort) {
                        if (gameProxyId == 1) {
                            if (com.huluxia.mconline.gamerole.a.Bf().alY != _tmpSenderPort) {
                                HLog.verbose(TAG, "[GameProcess]: DTPrint 玩家->发出数据 更新端口号 CMD[%d] [%s:%d => %s:%d]", new Object[]{Integer.valueOf(gameProxyId), _tmpSenderIP, Integer.valueOf(_tmpSenderPort), _tmpRecipientIP, Integer.valueOf(_tmpRecipientPort)});
                                com.huluxia.mconline.gamerole.a.Bf().alY = _tmpSenderPort;
                            }
                        } else if (com.huluxia.mconline.gamerole.a.Bf().alW != _tmpSenderPort) {
                            HLog.verbose(TAG, "[GameProcess]: DTPrint 玩家->发出数据 更新端口号 CMD[%d] [%s:%d => %s:%d]", new Object[]{Integer.valueOf(gameProxyId), _tmpSenderIP, Integer.valueOf(_tmpSenderPort), _tmpRecipientIP, Integer.valueOf(_tmpRecipientPort)});
                            com.huluxia.mconline.gamerole.a.Bf().alX = _tmpSenderIP;
                            com.huluxia.mconline.gamerole.a.Bf().alW = _tmpSenderPort;
                            com.huluxia.mconline.gamerole.a.Bf().ama = _tmpRecipientIP;
                            com.huluxia.mconline.gamerole.a.Bf().serverPort = _tmpRecipientPort;
                        }
                    }
                    HLog.verbose(TAG, "EDTPrint [游戏玩家]->[线上服务器]->[游戏服务器] [%d] => CMD [%02X] -- Len[%d] \n", new Object[]{Integer.valueOf(alT), Integer.valueOf(gameProxyId), Integer.valueOf(packetBuffer.length)});
                    com.huluxia.mconline.gameloc.tcp.player.a.AU().a(new c(com.huluxia.mconline.proto.a.amx, a.alQ, a.alO, a.alP, a.alM, a.alN).c(packetBuffer.length, packetBuffer));
                    datagramPacket.release();
                }
            }
        } else {
            long time = System.currentTimeMillis() - startTimeStack;
            if (time < 10) {
                try {
                    Thread.sleep(10 - time);
                } catch (InterruptedException e) {
                }
            }
        }
        return true;
    }

    private void AZ() throws Exception {
        while (Z(System.currentTimeMillis())) {
            try {
            } catch (Exception e) {
            }
            tick();
        }
    }

    private void tick() {
        if (!alS) {
            HLog.verbose(TAG, "[GameProcess] DTPrint 请求进入房间 ... ", new Object[0]);
            com.huluxia.mconline.gameloc.tcp.player.a.AU().a(new c(1283, com.huluxia.mconline.gamerole.a.Bf().Bg(), Constants.VIA_REPORT_TYPE_SET_AVATAR, 34, a.alM, a.alN).c(0, null));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void cg(boolean enterRoomSuccess) {
        alS = enterRoomSuccess;
    }
}
