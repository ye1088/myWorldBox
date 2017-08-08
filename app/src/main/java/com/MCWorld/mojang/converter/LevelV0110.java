package com.MCWorld.mojang.converter;

import com.MCWorld.mojang.entity.Entity;
import com.MCWorld.mojang.entity.Player;
import com.MCWorld.mojang.entity.PlayerV0110;
import com.MCWorld.mojang.tileentity.TileEntity;
import java.io.Serializable;
import java.util.List;

public class LevelV0110 implements Level, Serializable {
    private long creationTime;
    private long currentTick;
    private int dayCycleStopTime;
    private int dimension = 0;
    private List<Entity> entities;
    private int gameType;
    private int generator = 0;
    private long lastPlayed;
    private String levelName;
    private int limitedWorldOriginX;
    private int limitedWorldOriginY;
    private int limitedWorldOriginZ;
    private int platform;
    private PlayerV0110 player;
    private long randomSeed;
    private boolean spawnMobs = true;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private int storageVersion;
    private List<TileEntity> tileEntities;
    private long time;

    public boolean isSpawnMobs() {
        return this.spawnMobs;
    }

    public List<TileEntity> getTileEntities() {
        return this.tileEntities;
    }

    public void setTileEntities(List tileEntities) {
        this.tileEntities = tileEntities;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void setEntities(List entities) {
        this.entities = entities;
    }

    public int getLimitedWorldOriginX() {
        return this.limitedWorldOriginX;
    }

    public void setLimitedWorldOriginX(int limitedWorldOriginX) {
        this.limitedWorldOriginX = limitedWorldOriginX;
    }

    public int getLimitedWorldOriginY() {
        return this.limitedWorldOriginY;
    }

    public void setLimitedWorldOriginY(int limitedWorldOriginY) {
        this.limitedWorldOriginY = limitedWorldOriginY;
    }

    public int getLimitedWorldOriginZ() {
        return this.limitedWorldOriginZ;
    }

    public void setLimitedWorldOriginZ(int limitedWorldOriginZ) {
        this.limitedWorldOriginZ = limitedWorldOriginZ;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getCurrentTick() {
        return this.currentTick;
    }

    public void setCurrentTick(long currentTick) {
        this.currentTick = currentTick;
    }

    public long getDayCycleStopTime() {
        return (long) this.dayCycleStopTime;
    }

    public int getDimension() {
        return this.dimension;
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

    public long getRandomSeed() {
        return this.randomSeed;
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

    public long getTime() {
        return this.time;
    }

    public void setDayCycleStopTime(int value) {
        this.dayCycleStopTime = value;
    }

    public void setDimension(int paramInt) {
        this.dimension = paramInt;
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

    public void setRandomSeed(long paramLong) {
        this.randomSeed = paramLong;
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

    public void setTime(long paramLong) {
        this.time = paramLong;
    }

    public String toString() {
        return "Level [gameType=" + this.gameType + ", lastPlayed=" + this.lastPlayed + ", levelName=" + this.levelName + ", platform=" + this.platform + ", randomSeed=" + this.randomSeed + ", spawnX=" + this.spawnX + ", spawnY=" + this.spawnY + ", spawnZ=" + this.spawnZ + ", storageVersion=" + this.storageVersion + ", time=" + this.time + ", dayCycleStopTime=" + this.dayCycleStopTime + ", spawnMobs=" + this.spawnMobs + ", dimension=" + this.dimension + ", generator=" + this.generator + ", player = " + this.player + "]";
    }

    public void setPlayer(Player player) {
        if (player instanceof PlayerV0110) {
            this.player = (PlayerV0110) player;
        }
    }

    public PlayerV0110 getPlayer() {
        return this.player;
    }
}
