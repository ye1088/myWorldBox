package com.huluxia.mojang.entity;

import com.huluxia.mojang.util.Vector3f;
import java.io.Serializable;

public class Entity implements Serializable {
    private short air = (short) 300;
    private float fallDistance;
    private short fire;
    private Vector3f location = new Vector3f(0.0f, 0.0f, 0.0f);
    private Vector3f motion = new Vector3f(0.0f, 0.0f, 0.0f);
    private boolean onGround = false;
    private float pitch;
    private Entity riding = null;
    private int typeId = 0;
    private float yaw;

    public short getAirTicks() {
        return this.air;
    }

    public EntityType getEntityType() {
        EntityType localEntityType = EntityType.getById(this.typeId);
        if (localEntityType == null) {
            return EntityType.UNKNOWN;
        }
        return localEntityType;
    }

    public int getEntityTypeId() {
        return this.typeId;
    }

    public float getFallDistance() {
        return this.fallDistance;
    }

    public short getFireTicks() {
        return this.fire;
    }

    public Vector3f getLocation() {
        return this.location;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Entity getRiding() {
        return this.riding;
    }

    public Vector3f getVelocity() {
        return this.motion;
    }

    public float getYaw() {
        return this.yaw;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setAirTicks(short paramShort) {
        this.air = paramShort;
    }

    public void setEntityTypeId(int paramInt) {
        this.typeId = paramInt;
    }

    public void setFallDistance(float paramFloat) {
        this.fallDistance = paramFloat;
    }

    public void setFireTicks(short paramShort) {
        this.fire = paramShort;
    }

    public void setLocation(Vector3f paramVector3f) {
        this.location = paramVector3f;
    }

    public void setOnGround(boolean paramBoolean) {
        this.onGround = paramBoolean;
    }

    public void setPitch(float paramFloat) {
        this.pitch = paramFloat;
    }

    public void setRiding(Entity paramEntity) {
        this.riding = paramEntity;
    }

    public void setVelocity(Vector3f paramVector3f) {
        this.motion = paramVector3f;
    }

    public void setYaw(float paramFloat) {
        this.yaw = paramFloat;
    }

    public String toString() {
        return "Entity{air=" + this.air + ", fallDistance=" + this.fallDistance + ", fire=" + this.fire + ", location=" + this.location + ", motion=" + this.motion + ", onGround=" + this.onGround + ", pitch=" + this.pitch + ", riding=" + this.riding + ", typeId=" + this.typeId + ", yaw=" + this.yaw + '}';
    }
}
