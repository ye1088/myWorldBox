package com.huluxia.mojang.entity;

public class Arrow extends Projectile {
    private byte inData = (byte) 0;
    private boolean player = false;

    public byte getInData() {
        return this.inData;
    }

    public boolean isShotByPlayer() {
        return this.player;
    }

    public void setInData(byte paramByte) {
        this.inData = paramByte;
    }

    public void setShotByPlayer(boolean paramBoolean) {
        this.player = paramBoolean;
    }

    public String toString() {
        return "Arrow{inData=" + this.inData + ", player=" + this.player + '}';
    }
}
