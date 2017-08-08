package com.MCWorld.mojang.entity;

import com.MCWorld.mojang.util.Vector3f;

public class Painting extends Entity {
    private String artType = "Alban";
    private Vector3f blockCoordinates = new Vector3f(0.0f, 0.0f, 0.0f);
    private byte direction = (byte) 0;

    public String getArt() {
        return this.artType;
    }

    public Vector3f getBlockCoordinates() {
        return this.blockCoordinates;
    }

    public byte getDirection() {
        return this.direction;
    }

    public void setArt(String paramString) {
        this.artType = paramString;
    }

    public void setDirection(byte paramByte) {
        this.direction = paramByte;
    }

    public String toString() {
        return "Painting{artType='" + this.artType + '\'' + ", blockCoordinates=" + this.blockCoordinates + ", direction=" + this.direction + '}';
    }
}
