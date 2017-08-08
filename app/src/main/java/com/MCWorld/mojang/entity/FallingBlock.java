package com.MCWorld.mojang.entity;

public class FallingBlock extends Entity {
    private byte blockData;
    private int blockId = 12;
    private int time;

    public byte getBlockData() {
        return this.blockData;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int getTime() {
        return this.time;
    }

    public void setBlockData(byte paramByte) {
        this.blockData = paramByte;
    }

    public void setBlockId(int paramInt) {
        this.blockId = paramInt;
    }

    public void setTime(int paramInt) {
        this.time = paramInt;
    }

    public String toString() {
        return "FallingBlock{blockData=" + this.blockData + ", blockId=" + this.blockId + ", time=" + this.time + '}' + super.toString();
    }
}
