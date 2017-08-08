package com.MCWorld.mojang.entity;

public class Villager extends Animal {
    public int getMaxHealth() {
        return 10;
    }

    public String toString() {
        return "Villager " + super.toString();
    }
}
