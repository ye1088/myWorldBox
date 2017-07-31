package com.huluxia.mojang.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EntityType {
    CHICKEN("CHICKEN", 0, 10, Chicken.class),
    COW("COW", 1, 11, Cow.class),
    PIG("PIG", 2, 12, Pig.class),
    SHEEP("SHEEP", 3, 13, Sheep.class),
    ZOMBIE("ZOMBIE", 4, 32, Zombie.class),
    CREEPER("CREEPER", 5, 33, Creeper.class),
    SKELETON("SKELETON", 6, 34, Skeleton.class),
    SPIDER("SPIDER", 7, 35, Spider.class),
    PIG_ZOMBIE("PIG_ZOMBIE", 8, 36, PigZombie.class),
    ITEM("ITEM", 9, 64, Item.class),
    PRIMED_TNT("PRIMED_TNT", 10, 65, TNTPrimed.class),
    FALLING_BLOCK("FALLING_BLOCK", 11, 66, FallingBlock.class),
    ARROW("ARROW", 12, 80, Arrow.class),
    SNOWBALL("SNOWBALL", 13, 81, Snowball.class),
    EGG("EGG", 14, 82, Egg.class),
    PAINTING("PAINTING", 15, 83, Painting.class),
    MINECART("MINECART", 16, 84, Minecart.class),
    UNKNOWN("UNKNOWN", 17, -1, null),
    PLAYER("PLAYER", 18, 63, PlayerV010.class),
    WOLF("WOLF", 19, 14, Wolf.class),
    VILLAGER("VILLAGER", 20, 15, Villager.class),
    MUSHROOM_COW("MUSHROOM_COW", 21, 16, MushroomCow.class),
    SLIME("SLIME", 22, 37, Slime.class),
    ENDERMAN("ENDERMAN", 23, 38, Enderman.class),
    SILVERFISH("SILVERFISH", 24, 39, Silverfish.class);
    
    private static Map<Class<? extends Entity>, EntityType> classMap;
    private static Map<Integer, EntityType> idMap;
    private static List<EntityType> usedType;
    private Class<? extends Entity> entityClass;
    private int id;

    static {
        usedType = new ArrayList();
        EntityType[] arrayOfEntityType1 = new EntityType[]{CHICKEN, COW, PIG, SHEEP, ZOMBIE, CREEPER, SKELETON, SPIDER, PIG_ZOMBIE, ITEM, PRIMED_TNT, FALLING_BLOCK, ARROW, SNOWBALL, EGG, PAINTING, MINECART, UNKNOWN, PLAYER, WOLF, VILLAGER, MUSHROOM_COW, SLIME, ENDERMAN, SILVERFISH};
        idMap = new HashMap();
        classMap = new HashMap();
        for (EntityType localEntityType : values()) {
            idMap.put(Integer.valueOf(localEntityType.getId()), localEntityType);
            if (localEntityType.getEntityClass() != null) {
                classMap.put(localEntityType.getEntityClass(), localEntityType);
            }
        }
        usedType.add(CHICKEN);
        usedType.add(COW);
        usedType.add(PIG);
        usedType.add(SHEEP);
        usedType.add(ZOMBIE);
        usedType.add(CREEPER);
        usedType.add(SKELETON);
        usedType.add(SPIDER);
        usedType.add(PIG_ZOMBIE);
        usedType.add(WOLF);
        usedType.add(VILLAGER);
        usedType.add(MUSHROOM_COW);
        usedType.add(SLIME);
        usedType.add(ENDERMAN);
        usedType.add(SILVERFISH);
    }

    private EntityType(int paramInt, Class<? extends Entity> paramClass) {
        this.id = paramInt;
        this.entityClass = paramClass;
    }

    private EntityType(String desc, int paramInt1, int paramInt2, Class<? extends Entity> paramClass) {
        this.id = paramInt2;
        this.entityClass = paramClass;
    }

    public static EntityType getByClass(Class<? extends Entity> paramClass) {
        EntityType localEntityType = (EntityType) classMap.get(paramClass);
        if (localEntityType == null) {
            return UNKNOWN;
        }
        return localEntityType;
    }

    public static EntityType getById(int paramInt) {
        EntityType localEntityType = (EntityType) idMap.get(Integer.valueOf(paramInt));
        if (localEntityType == null) {
            return UNKNOWN;
        }
        return localEntityType;
    }

    public Class<? extends Entity> getEntityClass() {
        return this.entityClass;
    }

    public int getId() {
        return this.id;
    }

    public static boolean shouldUsed(EntityType type) {
        return usedType.contains(type);
    }
}
