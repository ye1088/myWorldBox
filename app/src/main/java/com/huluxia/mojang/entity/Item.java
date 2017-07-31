package com.huluxia.mojang.entity;

import com.huluxia.mojang.converter.ItemStack;

public class Item extends Entity {
    private short age = (short) 0;
    private short health = (short) 5;
    private ItemStack stack;

    public short getAge() {
        return this.age;
    }

    public short getHealth() {
        return this.health;
    }

    public ItemStack getItemStack() {
        return null;
    }

    public void setAge(short paramShort) {
        this.age = paramShort;
    }

    public void setHealth(short paramShort) {
        this.health = paramShort;
    }

    public void setItemStack(ItemStack paramItemStack) {
        this.stack = paramItemStack;
    }
}
