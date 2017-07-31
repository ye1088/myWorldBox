package com.huluxia.mojang.entity;

public class Pig extends Animal {
    public int getMaxHealth() {
        return 10;
    }

    public String toString() {
        return "Pig " + super.toString();
    }
}
