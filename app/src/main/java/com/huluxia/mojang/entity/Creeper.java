package com.huluxia.mojang.entity;

public class Creeper extends Monster {
    public int getMaxHealth() {
        return 20;
    }

    public String toString() {
        return "Creeper " + super.toString();
    }
}
