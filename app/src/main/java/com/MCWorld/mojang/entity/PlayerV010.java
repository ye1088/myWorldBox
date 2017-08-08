package com.MCWorld.mojang.entity;

import com.MCWorld.mojang.converter.InventorySlot;
import com.MCWorld.mojang.converter.ItemStack;
import com.MCWorld.mojang.util.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class PlayerV010 extends LivingEntity implements Player {
    private PlayerAbilities abilities = new PlayerAbilities();
    private List<ItemStack> armorSlots = new ArrayList();
    private int bedPositionX = 0;
    private int bedPositionY = 0;
    private int bedPositionZ = 0;
    private int dimension;
    private List<InventorySlot> inventory = new ArrayList();
    private int score;
    private short sleepTimer = (short) 0;
    private boolean sleeping = false;
    private int spawnX = 0;
    private int spawnY = 64;
    private int spawnZ = 0;

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

    public int getDimension() {
        return this.dimension;
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

    public void setDimension(int paramInt) {
        this.dimension = paramInt;
    }

    public void setInventory(List paramList) {
        this.inventory = paramList;
    }

    public Vector3f getPos() {
        return getLocation();
    }

    public void setPos(Vector3f pos) {
        setLocation(pos);
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

    public void setSpawnY(int paramInt) {
        this.spawnY = paramInt;
    }

    public void setSpawnZ(int paramInt) {
        this.spawnZ = paramInt;
    }

    public String toString() {
        return "Player{abilities=" + this.abilities + ", armorSlots=" + this.armorSlots + ", bedPositionX=" + this.bedPositionX + ", bedPositionY=" + this.bedPositionY + ", bedPositionZ=" + this.bedPositionZ + ", dimension=" + this.dimension + ", inventory=" + this.inventory + ", score=" + this.score + ", sleepTimer=" + this.sleepTimer + ", sleeping=" + this.sleeping + ", spawnX=" + this.spawnX + ", spawnY=" + this.spawnY + ", spawnZ=" + this.spawnZ + ", location=" + getLocation() + '}';
    }
}
