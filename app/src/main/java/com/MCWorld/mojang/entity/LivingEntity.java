package com.MCWorld.mojang.entity;

public class LivingEntity extends Entity {
    private short attackTime;
    private short deathTime;
    private short health = ((short) getMaxHealth());
    private short hurtTime;

    public short getAttackTime() {
        return this.attackTime;
    }

    public short getDeathTime() {
        return this.deathTime;
    }

    public short getHealth() {
        return this.health;
    }

    public short getHurtTime() {
        return this.hurtTime;
    }

    public int getMaxHealth() {
        return 20;
    }

    public void setAttackTime(short paramShort) {
        this.attackTime = paramShort;
    }

    public void setDeathTime(short paramShort) {
        this.deathTime = paramShort;
    }

    public void setHealth(short paramShort) {
        this.health = paramShort;
    }

    public void setHurtTime(short paramShort) {
        this.hurtTime = paramShort;
    }

    public String toString() {
        return "LivingEntity{attackTime=" + this.attackTime + ", deathTime=" + this.deathTime + ", health=" + this.health + ", hurtTime=" + this.hurtTime + '}';
    }
}
