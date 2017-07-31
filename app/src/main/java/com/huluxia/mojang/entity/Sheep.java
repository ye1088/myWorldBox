package com.huluxia.mojang.entity;

public class Sheep extends Animal {
    private byte color = (byte) 0;
    private boolean sheared = false;

    public byte getColor() {
        return this.color;
    }

    public int getMaxHealth() {
        return 8;
    }

    public boolean isSheared() {
        return this.sheared;
    }

    public void setColor(byte paramByte) {
        this.color = paramByte;
    }

    public void setSheared(boolean paramBoolean) {
        this.sheared = paramBoolean;
    }

    public String toString() {
        return "Sheep{color=" + this.color + ", sheared=" + this.sheared + '}';
    }
}
