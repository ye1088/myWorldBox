package com.MCWorld.mojang.entity;

public class Cow extends Animal {
    public int getMaxHealth() {
        return 10;
    }

    public String toString() {
        return "Cow " + super.toString();
    }
}
