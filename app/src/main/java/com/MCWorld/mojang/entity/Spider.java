package com.MCWorld.mojang.entity;

public class Spider extends Monster {
    public int getMaxHealth() {
        return 20;
    }

    public String toString() {
        return "Spider " + super.toString();
    }
}
