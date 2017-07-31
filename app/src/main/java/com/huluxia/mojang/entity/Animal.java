package com.huluxia.mojang.entity;

public class Animal extends LivingEntity {
    private int age = 0;
    private int inLove = 0;

    public int getAge() {
        return this.age;
    }

    public int getInLove() {
        return this.inLove;
    }

    public void setAge(int paramInt) {
        this.age = paramInt;
    }

    public void setInLove(int paramInt) {
        this.inLove = paramInt;
    }

    public String toString() {
        return "Animal{age=" + this.age + ", inLove=" + this.inLove + '}';
    }
}
