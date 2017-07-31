package com.huluxia.mojang.entity;

import java.io.Serializable;

public class PlayerAbilities implements Serializable {
    public boolean flying = false;
    public boolean instabuild = false;
    public boolean invulnerable = false;
    public boolean mayFly = false;
    public float walkSpeed = 0.1f;

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public void initForGameType(int gameType) {
        if (gameType == 1) {
            setMayFly(true);
        } else {
            setMayFly(false);
        }
    }

    public boolean isFlying() {
        return this.flying;
    }

    public boolean isInstabuild() {
        return this.instabuild;
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public boolean isMayFly() {
        return this.mayFly;
    }

    public void setFlying(boolean paramBoolean) {
        this.flying = paramBoolean;
    }

    public void setInstabuild(boolean paramBoolean) {
        this.instabuild = paramBoolean;
    }

    public void setInvulnerable(boolean paramBoolean) {
        this.invulnerable = paramBoolean;
    }

    public void setMayFly(boolean paramBoolean) {
        this.mayFly = paramBoolean;
    }

    public void setWalkSpeed(float paramFloat) {
        this.walkSpeed = paramFloat;
    }

    public String toString() {
        return "PlayerAbilities{flying=" + this.flying + ", instabuild=" + this.instabuild + ", invulnerable=" + this.invulnerable + ", mayFly=" + this.mayFly + ", walkSpeed=" + this.walkSpeed + '}';
    }
}
