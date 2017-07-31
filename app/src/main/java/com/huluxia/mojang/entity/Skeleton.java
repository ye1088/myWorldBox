package com.huluxia.mojang.entity;

public class Skeleton extends Monster {
    public int getMaxHealth() {
        return 20;
    }

    public String toString() {
        return "Skeleton " + super.toString();
    }
}
