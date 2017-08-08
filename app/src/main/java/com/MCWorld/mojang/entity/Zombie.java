package com.MCWorld.mojang.entity;

public class Zombie extends Monster {
    public int getMaxHealth() {
        return 20;
    }

    public String toString() {
        return "Zombie " + super.toString();
    }
}
