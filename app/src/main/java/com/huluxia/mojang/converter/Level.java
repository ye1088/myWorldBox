package com.huluxia.mojang.converter;

import com.huluxia.mojang.entity.Entity;
import com.huluxia.mojang.entity.Player;
import com.huluxia.mojang.tileentity.TileEntity;
import java.util.List;

public interface Level<T extends Player> {
    List<Entity> getEntities();

    int getGameType();

    long getLastPlayed();

    String getLevelName();

    T getPlayer();

    long getRandomSeed();

    int getSpawnX();

    int getSpawnY();

    int getSpawnZ();

    List<TileEntity> getTileEntities();

    long getTime();

    void setEntities(List list);

    void setGameType(int i);

    void setLastPlayed(long j);

    void setLevelName(String str);

    void setPlayer(T t);

    void setRandomSeed(long j);

    void setSpawnX(int i);

    void setSpawnY(int i);

    void setSpawnZ(int i);

    void setTileEntities(List list);

    void setTime(long j);
}
