package com.MCWorld.mojang.entity;

public class PigZombie extends Monster {
    private short anger = (short) 0;

    public short getAnger() {
        return this.anger;
    }

    public int getMaxHealth() {
        return 20;
    }

    public void setAnger(short paramShort) {
        this.anger = paramShort;
    }

    public String toString() {
        return "PigZombie{anger=" + this.anger + '}';
    }
}
