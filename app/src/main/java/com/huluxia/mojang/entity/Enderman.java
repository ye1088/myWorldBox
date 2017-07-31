package com.huluxia.mojang.entity;

public class Enderman extends Monster {
    private short carried = (short) 0;
    private short carriedData = (short) 0;

    public short getCarried() {
        return this.carried;
    }

    public short getCarriedData() {
        return this.carriedData;
    }

    public int getMaxHealth() {
        return 40;
    }

    public void setCarried(short paramShort) {
    }

    public void setCarriedData(short paramShort) {
    }

    public String toString() {
        return "Enderman " + super.toString();
    }
}
