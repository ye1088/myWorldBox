package com.huluxia.mojang.entity;

public class Projectile extends Entity {
    private boolean inGround = false;
    private byte inTile = (byte) 0;
    private byte shake = (byte) 0;
    private short xTile = (short) 0;
    private short yTile = (short) 0;
    private short zTile = (short) 0;

    public byte getInBlock() {
        return this.inTile;
    }

    public byte getShake() {
        return this.shake;
    }

    public short getXTile() {
        return this.xTile;
    }

    public String toString() {
        return "Projectile{inGround=" + this.inGround + ", inTile=" + this.inTile + ", shake=" + this.shake + ", xTile=" + this.xTile + ", yTile=" + this.yTile + ", zTile=" + this.zTile + '}';
    }

    public short getYTile() {
        return this.yTile;
    }

    public short getZTile() {
        return this.zTile;
    }

    public boolean isInGround() {
        return this.inGround;
    }

    public void setInBlock(byte paramByte) {
        this.inTile = paramByte;
    }

    public void setInGround(boolean paramBoolean) {
        this.inGround = paramBoolean;
    }

    public void setShake(byte paramByte) {
        this.shake = paramByte;
    }

    public void setXTile(short paramShort) {
        this.xTile = paramShort;
    }

    public void setYTile(short paramShort) {
        this.yTile = paramShort;
    }

    public void setZTile(short paramShort) {
        this.zTile = paramShort;
    }
}
