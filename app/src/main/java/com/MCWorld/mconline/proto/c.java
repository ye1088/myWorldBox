package com.MCWorld.mconline.proto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.Arrays;
import org.apache.tools.tar.TarConstants;

/* compiled from: MCTCPSessionPack */
public class c {
    private static final boolean alz = false;
    private static final int amV = 20;
    private static final int amW = 4;
    public String amI;
    public int amJ;
    public String amK;
    public int amL;
    public int amM;
    public int amN;
    public int amO;
    public int amP;
    byte[] amQ;
    byte[] amR;
    public int amS;
    public byte[] amT;
    public byte amU;

    public c() {
        this.amQ = new byte[20];
        this.amR = new byte[20];
        this.amM = 0;
        this.amN = 0;
        this.amI = TarConstants.VERSION_POSIX;
        this.amJ = 0;
        this.amK = TarConstants.VERSION_POSIX;
        this.amL = 0;
        this.amO = 2;
        this.amP = 2;
    }

    public c(int inputProxyId, int inputRoomId, String inputSenderIP, int inputSenderPort, String inputRecipientIP, int inputRecipientPort) {
        this.amQ = new byte[20];
        this.amR = new byte[20];
        this.amM = inputProxyId;
        this.amN = inputRoomId;
        this.amI = inputSenderIP;
        this.amJ = inputSenderPort;
        this.amK = inputRecipientIP;
        this.amL = inputRecipientPort;
    }

    public byte[] b(int gameDataLen, byte[] gameData) {
        ByteBuf data = Unpooled.buffer();
        data.writeInt(this.amM);
        data.writeInt(this.amN);
        data.writeInt(this.amI.getBytes().length);
        data.writeBytes(this.amI.getBytes());
        data.writeInt(this.amJ);
        data.writeInt(this.amK.getBytes().length);
        data.writeBytes(this.amK.getBytes());
        data.writeInt(this.amL);
        data.writeInt(gameDataLen);
        data.writeBytes(gameData);
        this.amT = new byte[data.readableBytes()];
        data.readBytes(this.amT);
        return this.amT;
    }

    public ByteBuf c(int gameDataLen, byte[] gameData) {
        ByteBuf data = Unpooled.buffer();
        data.writeInt(this.amM);
        data.writeInt(this.amN);
        data.writeInt(this.amI.getBytes().length);
        data.writeBytes(this.amI.getBytes());
        data.writeInt(this.amJ);
        data.writeInt(this.amK.getBytes().length);
        data.writeBytes(this.amK.getBytes());
        data.writeInt(this.amL);
        data.writeInt(gameDataLen);
        if (gameData != null) {
            data.writeBytes(gameData);
        }
        return data;
    }

    public int a(ByteBuf data, boolean readcmd) {
        this.amM = data.readInt();
        int gameDataStartIndex = 0 + 4;
        this.amN = data.readInt();
        gameDataStartIndex += 4;
        this.amO = data.readInt();
        if (this.amO > 20) {
            this.amO = 20;
        }
        gameDataStartIndex += 4;
        Arrays.fill(this.amQ, (byte) 0);
        data.readBytes(this.amQ, 0, this.amO);
        this.amI = new String(this.amQ, 0, this.amO);
        gameDataStartIndex = this.amO + 12;
        this.amJ = data.readInt();
        gameDataStartIndex += 4;
        this.amP = data.readInt();
        if (this.amP > 20) {
            this.amP = 20;
        }
        gameDataStartIndex += 4;
        Arrays.fill(this.amR, (byte) 0);
        data.readBytes(this.amR, 0, this.amP);
        this.amK = new String(this.amR, 0, this.amP);
        gameDataStartIndex += this.amP;
        this.amL = data.readInt();
        gameDataStartIndex += 4;
        this.amS = data.readInt();
        gameDataStartIndex += 4;
        if (readcmd && this.amS > 0) {
            this.amU = data.readByte();
        }
        return gameDataStartIndex;
    }

    public int w(byte[] packetBuffer) {
        ByteBuf data = Unpooled.buffer();
        data.writeBytes(packetBuffer);
        this.amM = data.readInt();
        int gameDataStartIndex = 0 + 4;
        this.amN = data.readInt();
        gameDataStartIndex += 4;
        this.amO = data.readInt();
        if (this.amO > 20) {
            this.amO = 20;
        }
        gameDataStartIndex += 4;
        Arrays.fill(this.amQ, (byte) 0);
        data.readBytes(this.amQ, 0, this.amO);
        this.amI = new String(this.amQ, 0, this.amO);
        gameDataStartIndex = this.amO + 12;
        this.amJ = data.readInt();
        gameDataStartIndex += 4;
        this.amP = data.readInt();
        if (this.amP > 20) {
            this.amP = 20;
        }
        gameDataStartIndex += 4;
        Arrays.fill(this.amR, (byte) 0);
        data.readBytes(this.amR, 0, this.amP);
        this.amK = new String(this.amR, 0, this.amP);
        gameDataStartIndex += this.amP;
        this.amL = data.readInt();
        gameDataStartIndex += 4;
        this.amS = data.readInt();
        return gameDataStartIndex + 4;
    }

    public void BV() {
    }
}
