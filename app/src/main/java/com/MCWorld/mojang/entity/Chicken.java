package com.MCWorld.mojang.entity;

public class Chicken extends Animal {
    public int getMaxHealth() {
        return 4;
    }

    public String toString() {
        return "Chicken " + super.toString();
    }
}
