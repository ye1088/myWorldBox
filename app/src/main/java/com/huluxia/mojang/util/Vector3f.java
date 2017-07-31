package com.huluxia.mojang.util;

import java.io.Serializable;

public class Vector3f implements Serializable {
    public float x;
    public float y;
    public float z;

    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float paramFloat1, float paramFloat2, float paramFloat3) {
        this.x = paramFloat1;
        this.y = paramFloat2;
        this.z = paramFloat3;
    }

    public double distSquared(Vector3f paramVector3f) {
        return (Math.pow((double) (paramVector3f.x - this.x), 2.0d) + Math.pow((double) (paramVector3f.y - this.y), 2.0d)) + Math.pow((double) (paramVector3f.z - this.z), 2.0d);
    }

    public int getBlockX() {
        return (int) this.x;
    }

    public int getBlockY() {
        return (int) this.y;
    }

    public int getBlockZ() {
        return (int) this.z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public Vector3f setX(float paramFloat) {
        this.x = paramFloat;
        return this;
    }

    public Vector3f setY(float paramFloat) {
        this.y = paramFloat;
        return this;
    }

    public Vector3f setZ(float paramFloat) {
        this.z = paramFloat;
        return this;
    }

    public String toString() {
        return "Vector3f{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}
