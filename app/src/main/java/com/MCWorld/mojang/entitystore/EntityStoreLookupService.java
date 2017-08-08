package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.entity.Entity;
import java.util.HashMap;
import java.util.Map;

public class EntityStoreLookupService {
    public static EntityStore<Entity> defaultStore = new EntityStore();
    public static Map<Integer, EntityStore> idMap = new HashMap();

    static {
        addStore(10, new AnimalEntityStore());
        addStore(11, new AnimalEntityStore());
        addStore(12, new AnimalEntityStore());
        addStore(13, new SheepEntityStore());
        addStore(32, new LivingEntityStore());
        addStore(33, new LivingEntityStore());
        addStore(34, new LivingEntityStore());
        addStore(35, new LivingEntityStore());
        addStore(36, new PigZombieEntityStore());
        addStore(64, new ItemEntityStore());
        addStore(65, new TNTPrimedEntityStore());
        addStore(66, new FallingBlockEntityStore());
        addStore(80, new ArrowEntityStore());
        addStore(81, new ProjectileEntityStore());
        addStore(82, new ProjectileEntityStore());
        addStore(83, new PaintingEntityStore());
        addStore(84, new MinecartEntityStore());
    }

    public static void addStore(int paramInt, EntityStore paramEntityStore) {
        idMap.put(Integer.valueOf(paramInt), paramEntityStore);
    }
}
