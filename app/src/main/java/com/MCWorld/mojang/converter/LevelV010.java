package com.MCWorld.mojang.converter;

import com.MCWorld.mojang.entity.Entity;
import com.MCWorld.mojang.entity.Player;
import com.MCWorld.mojang.entity.PlayerV010;
import com.MCWorld.mojang.tileentity.TileEntity;
import java.io.Serializable;
import java.util.List;

public class LevelV010 implements Level, Serializable {
    private long dayCycleStopTime = -1;
    private int dimension = 0;
    private List<Entity> entities;
    private int gameType;
    private int generator = 0;
    private long lastPlayed;
    private String levelName;
    private int platform;
    private PlayerV010 player;
    private long randomSeed;
    private long sizeOnDisk;
    private boolean spawnMobs = true;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private int storageVersion;
    private List<TileEntity> tileEntities;
    private long time;

    public long getDayCycleStopTime() {
        return this.dayCycleStopTime;
    }

    public int getDimension() {
        return this.dimension;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public int getGameType() {
        return this.gameType;
    }

    public int getGenerator() {
        return this.generator;
    }

    public long getLastPlayed() {
        return this.lastPlayed;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public int getPlatform() {
        return this.platform;
    }

    public PlayerV010 getPlayer() {
        return this.player;
    }

    public long getRandomSeed() {
        return this.randomSeed;
    }

    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }

    public boolean getSpawnMobs() {
        return this.spawnMobs;
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

    public int getStorageVersion() {
        return this.storageVersion;
    }

    public List<TileEntity> getTileEntities() {
        return this.tileEntities;
    }

    public long getTime() {
        return this.time;
    }

    public void setDayCycleStopTime(long paramLong) {
        this.dayCycleStopTime = paramLong;
    }

    public void setDimension(int paramInt) {
        this.dimension = paramInt;
    }

    public void setEntities(List paramList) {
        this.entities = paramList;
    }

    public void setGameType(int paramInt) {
        this.gameType = paramInt;
    }

    public void setGenerator(int paramInt) {
        this.generator = paramInt;
    }

    public void setLastPlayed(long paramLong) {
        this.lastPlayed = paramLong;
    }

    public void setLevelName(String paramString) {
        this.levelName = paramString;
    }

    public void setPlatform(int paramInt) {
        this.platform = paramInt;
    }

    public void setPlayer(Player paramPlayer) {
        if (paramPlayer instanceof PlayerV010) {
            this.player = (PlayerV010) paramPlayer;
        }
    }

    public void setRandomSeed(long paramLong) {
        this.randomSeed = paramLong;
    }

    public void setSizeOnDisk(long paramLong) {
        this.sizeOnDisk = paramLong;
    }

    public void setSpawnMobs(boolean paramBoolean) {
        this.spawnMobs = paramBoolean;
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

    public void setStorageVersion(int paramInt) {
        this.storageVersion = paramInt;
    }

    public void setTileEntities(List paramList) {
        this.tileEntities = paramList;
    }

    public void setTime(long paramLong) {
        this.time = paramLong;
    }

    public String toString() {
        return "Level [gameType=" + this.gameType + ", lastPlayed=" + this.lastPlayed + ", levelName=" + this.levelName + ", platform=" + this.platform + ", player=" + this.player + ", randomSeed=" + this.randomSeed + ", sizeOnDisk=" + this.sizeOnDisk + ", spawnX=" + this.spawnX + ", spawnY=" + this.spawnY + ", spawnZ=" + this.spawnZ + ", storageVersion=" + this.storageVersion + ", time=" + this.time + ", dayCycleStopTime=" + this.dayCycleStopTime + ", spawnMobs=" + this.spawnMobs + ", dimension=" + this.dimension + ", generator=" + this.generator + ", entities=" + this.entities + ", tileEntities=" + this.tileEntities + ", player = " + this.player + "]";
    }
}
