package com.huluxia.mojang.entity;

public class TNTPrimed extends Entity {
    private byte fuse = (byte) 0;

    public byte getFuseTicks() {
        return this.fuse;
    }

    public void setFuseTicks(byte paramByte) {
        this.fuse = paramByte;
    }

    public String toString() {
        return "TNTPrimed{fuse=" + this.fuse + '}';
    }
}
