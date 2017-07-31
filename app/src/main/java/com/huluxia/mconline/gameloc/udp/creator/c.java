package com.huluxia.mconline.gameloc.udp.creator;

import com.huluxia.mconline.gamerole.b;
import com.huluxia.mconline.utils.a;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.Iterator;

/* compiled from: ProxyGamePlayerSession */
public class c {
    private static final String TAG = "ProxyGamePlayerSession";
    private static boolean alI = false;
    static int alL = 0;
    private static final boolean alz = false;
    private String alJ = "";
    private int alK = 0;

    public c(String inputOnlineServerIp, int inputOnlineServerPort) throws Exception {
        this.alJ = inputOnlineServerIp;
        this.alK = inputOnlineServerPort;
        run();
    }

    public void run() throws Exception {
        AZ();
    }

    private boolean Z(long startTimeStack) throws Exception {
        boolean _tmpHavePacket = false;
        Iterator it = b.AX().alH.iterator();
        while (it.hasNext()) {
            a _curProxyGamePlayer = (a) it.next();
            DatagramPacket datagramPacket = _curProxyGamePlayer.AW();
            if (datagramPacket != null) {
                _tmpHavePacket = true;
                String _tmpSenderIP = ((InetSocketAddress) datagramPacket.sender()).getAddress().getHostAddress();
                int _tmpSenderPort = ((InetSocketAddress) datagramPacket.sender()).getPort();
                String _tmpRecipientIP = ((InetSocketAddress) datagramPacket.recipient()).getAddress().getHostAddress();
                int _tmpRecipientPort = ((InetSocketAddress) datagramPacket.recipient()).getPort();
                ByteBuf byteBuf = (ByteBuf) datagramPacket.content();
                byte[] packetBuffer = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(packetBuffer);
                byteBuf.release();
                if (packetBuffer.length > 0) {
                    if (!alI) {
                        return true;
                    }
                    if (_tmpSenderIP.equals(a.getHostAddress())) {
                        com.huluxia.mconline.gameloc.tcp.creator.a.AT().a(new com.huluxia.mconline.proto.c(com.huluxia.mconline.proto.a.amy, b.Bh().Bk(), b.Bh().Bi(), b.Bh().Bj(), _curProxyGamePlayer.alD, _curProxyGamePlayer.alC).c(packetBuffer.length, packetBuffer));
                    }
                }
                datagramPacket.release();
            }
        }
        if (!_tmpHavePacket) {
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
        }
    }

    private void tick() {
    }

    public static void cf(boolean roomCreatSuccess) {
        alI = roomCreatSuccess;
    }
}
