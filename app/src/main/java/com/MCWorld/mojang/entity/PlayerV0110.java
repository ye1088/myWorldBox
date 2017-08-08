package com.MCWorld.mojang.entity;

import com.MCWorld.mojang.converter.InventorySlot;
import com.MCWorld.mojang.converter.ItemStack;
import com.MCWorld.mojang.util.Vector2f;
import com.MCWorld.mojang.util.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class PlayerV0110 implements Player {
    private PlayerAbilities abilities = new PlayerAbilities();
    private short air;
    private List<ItemStack> armorSlots = new ArrayList();
    private short attackTime;
    private int bedPositionX = 0;
    private int bedPositionY = 0;
    private int bedPositionZ = 0;
    private short deathTime;
    private float fallDistance;
    private short fire;
    private short health;
    private short hurtTime;
    private int id;
    private List<InventorySlot> inventory = new ArrayList();
    private Vector3f motion;
    private boolean onGround = true;
    private boolean persistent = true;
    private Vector3f pos;
    private Vector2f rotation;
    private int score;
    private short sleepTimer = (short) 0;
    private boolean sleeping = false;
    private int spawnX = 0;
    private int spawnY = 64;
    private int spawnZ = 0;
    private long targetId;
    private long uniqueId;

    public short getAir() {
        return this.air;
    }

    public void setAir(short air) {
        this.air = air;
    }

    public List<ItemStack> getArmorSlots() {
        return this.armorSlots;
    }

    public void setArmorSlots(List<ItemStack> armorSlots) {
        this.armorSlots = armorSlots;
    }

    public short getAttackTime() {
        return this.attackTime;
    }

    public void setAttackTime(short attackTime) {
        this.attackTime = attackTime;
    }

    public short getDeathTime() {
        return this.deathTime;
    }

    public void setDeathTime(short deathTime) {
        this.deathTime = deathTime;
    }

    public float getFallDistance() {
        return this.fallDistance;
    }

    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    public short getFire() {
        return this.fire;
    }

    public void setFire(short fire) {
        this.fire = fire;
    }

    public short getHealth() {
        return this.health;
    }

    public void setHealth(short health) {
        this.health = health;
    }

    public short getHurtTime() {
        return this.hurtTime;
    }

    public void setHurtTime(short hurtTime) {
        this.hurtTime = hurtTime;
    }

    public Vector3f getMotion() {
        return this.motion;
    }

    public void setMotion(Vector3f motion) {
        this.motion = motion;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public Vector3f getPos() {
        return this.pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector2f getRotation() {
        return this.rotation;
    }

    public void setRotation(Vector2f rotation) {
        this.rotation = rotation;
    }

    public long getTargetId() {
        return this.targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public long getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayerAbilities getAbilities() {
        return this.abilities;
    }

    public List<ItemStack> getArmor() {
        return this.armorSlots;
    }

    public int getBedPositionX() {
        return this.bedPositionX;
    }

    public int getBedPositionY() {
        return this.bedPositionY;
    }

    public int getBedPositionZ() {
        return this.bedPositionZ;
    }

    public EntityType getEntityType() {
        return EntityType.PLAYER;
    }

    public List<InventorySlot> getInventory() {
        return this.inventory;
    }

    public int getScore() {
        return this.score;
    }

    public short getSleepTimer() {
        return this.sleepTimer;
    }

    public int getSpawnX() {
        return this.spawnX;
    }

    public int getSpawnY() {
        return this.spawnY;
    }

    public int getSpawnZ() {
        return this.spawnZ;
    }

    public boolean isSleeping() {
        return this.sleeping;
    }

    public void setAbilities(PlayerAbilities paramPlayerAbilities) {
        this.abilities = paramPlayerAbilities;
    }

    public void setArmor(List<ItemStack> paramList) {
        this.armorSlots = paramList;
    }

    public void setBedPositionX(int paramInt) {
        this.bedPositionX = paramInt;
    }

    public void setBedPositionY(int paramInt) {
        this.bedPositionY = paramInt;
    }

    public void setBedPositionZ(int paramInt) {
        this.bedPositionZ = paramInt;
    }

    public void setInventory(List paramList) {
        this.inventory = paramList;
    }

    public void setScore(int paramInt) {
        this.score = paramInt;
    }

    public void setSleepTimer(short paramShort) {
        this.sleepTimer = paramShort;
    }

    public void setSleeping(boolean paramBoolean) {
        this.sleeping = paramBoolean;
    }

    public void setSpawnX(int paramInt) {
        this.spawnX = paramInt;
    }

    public String toString() {
        return "PlayerV0110{air=" + this.air + ", armorSlots=" + this.armorSlots + ", attackTime=" + this.attackTime + ", bedPositionX=" + this.bedPositionX + ", bedPositionY=" + this.bedPositionY + ", bedPositionZ=" + this.bedPositionZ + ", deathTime=" + this.deathTime + ", fallDistance=" + this.fallDistance + ", fire=" + this.fire + ", health=" + this.health + ", hurtTime=" + this.hurtTime + ", inventory=" + this.inventory + ", motion=" + this.motion + ", onGround=" + this.onGround + ", persistent=" + this.persistent + ", pos=" + this.pos + ", rotation=" + this.rotation + ", score=" + this.score + ", sleepTimer=" + this.sleepTimer + ", sleeping=" + this.sleeping + ", spawnX=" + this.spawnX + ", spawnY=" + this.spawnY + ", spawnZ=" + this.spawnZ + ", targetId=" + this.targetId + ", uniqueId=" + this.uniqueId + ", abilities=" + this.abilities + ", id=" + this.id + '}';
    }

    public void setSpawnY(int paramInt) {
        this.spawnY = paramInt;
    }

    public void setSpawnZ(int paramInt) {
        this.spawnZ = paramInt;
    }
}
