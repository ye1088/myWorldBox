package com.huluxia.mojang.util;

import java.io.Serializable;

public class Vector2f implements Serializable {
    public float x;
    public float y;

    public Vector2f() {
        this(0.0f, 0.0f);
    }

    public Vector2f(float yaw, float pitch) {
        this.x = yaw;
        this.y = pitch;
    }

    public int getYaw() {
        return (int) this.x;
    }

    public int getPitch() {
        return (int) this.y;
    }

    public Vector2f setYaw(float paramFloat) {
        this.x = paramFloat;
        return this;
    }

    public Vector2f setPitch(float paramFloat) {
        this.y = paramFloat;
        return this;
    }

    public String toString() {
        return "Vector2f{x=" + this.x + ", y=" + this.y + '}';
    }
}
