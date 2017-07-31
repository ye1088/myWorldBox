package com.huluxia.mojang.converter;

import android.util.Log;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.mojang.entity.Chicken;
import com.huluxia.mojang.entity.Cow;
import com.huluxia.mojang.entity.Entity;
import com.huluxia.mojang.entity.EntityType;
import com.huluxia.mojang.entity.Item;
import com.huluxia.mojang.entity.Pig;
import com.huluxia.mojang.entity.PlayerAbilities;
import com.huluxia.mojang.entity.PlayerV010;
import com.huluxia.mojang.entity.PlayerV0110;
import com.huluxia.mojang.entity.Sheep;
import com.huluxia.mojang.entity.Zombie;
import com.huluxia.mojang.entitystore.EntityStore;
import com.huluxia.mojang.entitystore.EntityStoreLookupService;
import com.huluxia.mojang.tileentity.TileEntity;
import com.huluxia.mojang.tileentitystore.TileEntityStore;
import com.huluxia.mojang.tileentitystore.TileEntityStoreLookupService;
import com.huluxia.mojang.util.Vector2f;
import com.huluxia.mojang.util.Vector3f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.FloatTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.ListTag;
import org.spout.nbt.LongTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

public class NBTConverter {
    private static final String TAG = "NBTConverter";

    public static LevelV010 readLevel(CompoundTag paramCompoundTag) {
        LevelV010 localLevel = new LevelV010();
        for (Tag localTag : paramCompoundTag.getValue()) {
            Log.d(TAG, "local tag key =  " + localTag.getName() + ", value = " + localTag.getValue());
            String str = localTag.getName();
            if (str.equals("GameType")) {
                localLevel.setGameType(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("LastPlayed")) {
                localLevel.setLastPlayed(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("LevelName")) {
                localLevel.setLevelName(((StringTag) localTag).getValue());
            } else if (str.equals("Platform")) {
                localLevel.setPlatform(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("Player")) {
                localLevel.setPlayer(readPlayer((CompoundTag) localTag));
            } else if (str.equals("RandomSeed")) {
                localLevel.setRandomSeed(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("SizeOnDisk")) {
                localLevel.setSizeOnDisk(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("SpawnX")) {
                localLevel.setSpawnX(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("SpawnY")) {
                localLevel.setSpawnY(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("SpawnZ")) {
                localLevel.setSpawnZ(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("StorageVersion")) {
                localLevel.setStorageVersion(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("Time")) {
                localLevel.setTime(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("dayCycleStopTime")) {
                localLevel.setDayCycleStopTime(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("spawnMobs")) {
                if (((ByteTag) localTag).getValue().byteValue() != (byte) 0) {
                    localLevel.setSpawnMobs(((ByteTag) localTag).getBooleanValue());
                }
            } else if (str.equals("Dimension")) {
                localLevel.setDimension(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("Generator")) {
                localLevel.setGenerator(((IntTag) localTag).getValue().intValue());
            } else {
                Log.d(TAG, "Unhandled level tag: " + str + ":" + localTag);
            }
        }
        return localLevel;
    }

    public static LevelV0110 readLevelV0110(CompoundTag paramCompoundTag) {
        LevelV0110 localLevel = new LevelV0110();
        for (Tag localTag : paramCompoundTag.getValue()) {
            Log.d(TAG, "local tag key =  " + localTag.getName() + ", value = " + localTag.getValue());
            String str = localTag.getName();
            if (str.equals("GameType")) {
                localLevel.setGameType(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("LastPlayed")) {
                localLevel.setLastPlayed(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("LevelName")) {
                localLevel.setLevelName(((StringTag) localTag).getValue());
            } else if (str.equals("Platform")) {
                localLevel.setPlatform(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("RandomSeed")) {
                localLevel.setRandomSeed(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("SpawnX")) {
                localLevel.setSpawnX(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("SpawnY")) {
                localLevel.setSpawnY(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("SpawnZ")) {
                localLevel.setSpawnZ(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("StorageVersion")) {
                localLevel.setStorageVersion(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("Time")) {
                localLevel.setTime(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("DayCycleStopTime")) {
                localLevel.setDayCycleStopTime(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("spawnMobs")) {
                if (((ByteTag) localTag).getValue().byteValue() != (byte) 0) {
                    localLevel.setSpawnMobs(((ByteTag) localTag).getBooleanValue());
                }
            } else if (str.equals("Dimension")) {
                localLevel.setDimension(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("Generator")) {
                localLevel.setGenerator(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("LimitedWorldOriginX")) {
                localLevel.setLimitedWorldOriginX(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("LimitedWorldOriginY")) {
                localLevel.setLimitedWorldOriginY(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("LimitedWorldOriginZ")) {
                localLevel.setLimitedWorldOriginZ(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("creationTime")) {
                localLevel.setCreationTime(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("currentTick")) {
                localLevel.setCurrentTick(((LongTag) localTag).getValue().longValue());
            } else {
                Log.d(TAG, "Unhandled level tag: " + str + ":" + localTag);
            }
        }
        return localLevel;
    }

    public static PlayerV010 readPlayer(CompoundTag paramCompoundTag) {
        List<Tag> localList1 = paramCompoundTag.getValue();
        PlayerV010 localPlayer = new PlayerV010();
        for (Tag localTag : localList1) {
            String str = localTag.getName();
            Log.v(TAG, "readPlayer tag= " + localTag);
            if (localTag.getName().equals("Pos")) {
                localPlayer.setLocation(readVector((ListTag) localTag));
            } else if (localTag.getName().equals("Motion")) {
                localPlayer.setVelocity(readVector((ListTag) localTag));
            } else if (localTag.getName().equals("Air")) {
                localPlayer.setAirTicks(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("Fire")) {
                localPlayer.setFireTicks(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("FallDistance")) {
                localPlayer.setFallDistance(((FloatTag) localTag).getValue().floatValue());
            } else if (localTag.getName().equals("Rotation")) {
                List localList2 = ((ListTag) localTag).getValue();
                localPlayer.setYaw(((FloatTag) localList2.get(0)).getValue().floatValue());
                localPlayer.setPitch(((FloatTag) localList2.get(1)).getValue().floatValue());
            } else if (localTag.getName().equals("OnGround")) {
                if (((ByteTag) localTag).getValue().byteValue() > (byte) 0) {
                    localPlayer.setOnGround(((ByteTag) localTag).getBooleanValue());
                }
            } else if (localTag.getName().equals("AttackTime")) {
                localPlayer.setAttackTime(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("DeathTime")) {
                localPlayer.setDeathTime(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("Health")) {
                localPlayer.setHealth(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("HurtTime")) {
                localPlayer.setHurtTime(((ShortTag) localTag).getValue().shortValue());
            } else if (str.equals("Armor")) {
                localPlayer.setArmor(readArmor((ListTag) localTag));
            } else if (str.equals("BedPositionX")) {
                localPlayer.setBedPositionX(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("BedPositionY")) {
                localPlayer.setBedPositionY(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("BedPositionZ")) {
                localPlayer.setBedPositionZ(((IntTag) localTag).getValue().intValue());
            } else if (localTag.getName().equals("Dimension")) {
                localPlayer.setDimension(((IntTag) localTag).getValue().intValue());
            } else if (localTag.getName().equals("Inventory")) {
                localPlayer.setInventory(readInventory((ListTag) localTag));
            } else if (localTag.getName().equals("Score")) {
                localPlayer.setScore(((IntTag) localTag).getValue().intValue());
            } else if (localTag.getName().equals("Sleeping")) {
                if (((ByteTag) localTag).getValue().byteValue() != (byte) 0) {
                    localPlayer.setSleeping(((ByteTag) localTag).getBooleanValue());
                }
            } else if (str.equals("SleepTimer")) {
                localPlayer.setSleepTimer(((ShortTag) localTag).getValue().shortValue());
            } else if (str.equals("SpawnX")) {
                localPlayer.setSpawnX(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("SpawnY")) {
                localPlayer.setSpawnY(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("SpawnZ")) {
                localPlayer.setSpawnZ(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("abilities")) {
                readAbilities((CompoundTag) localTag, localPlayer.getAbilities());
            } else if (str.equals("Riding")) {
                localPlayer.setRiding(readSingleEntity((CompoundTag) localTag));
            } else {
                Log.d(TAG, "Unhandled player tag: " + str);
            }
        }
        return localPlayer;
    }

    public static PlayerV0110 readPlayerV0110(CompoundTag paramCompoundTag) {
        List<Tag> localList1 = paramCompoundTag.getValue();
        PlayerV0110 localPlayer = new PlayerV0110();
        for (Tag localTag : localList1) {
            String str = localTag.getName();
            Log.v(TAG, "readPlayer tag= " + localTag);
            if (localTag.getName().equals("Pos")) {
                localPlayer.setPos(readVector((ListTag) localTag));
            } else if (localTag.getName().equals("Motion")) {
                localPlayer.setMotion(readVector((ListTag) localTag));
            } else if (localTag.getName().equals("Air")) {
                localPlayer.setAir(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("Fire")) {
                localPlayer.setFire(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("FallDistance")) {
                localPlayer.setFallDistance(((FloatTag) localTag).getValue().floatValue());
            } else if (localTag.getName().equals("Rotation")) {
                List localList2 = ((ListTag) localTag).getValue();
                Vector2f rotation = new Vector2f();
                rotation.setYaw(((FloatTag) localList2.get(0)).getValue().floatValue());
                rotation.setPitch(((FloatTag) localList2.get(1)).getValue().floatValue());
                localPlayer.setRotation(rotation);
            } else if (localTag.getName().equals("OnGround")) {
                if (((ByteTag) localTag).getValue().byteValue() > (byte) 0) {
                    localPlayer.setOnGround(((ByteTag) localTag).getBooleanValue());
                }
            } else if (localTag.getName().equals("AttackTime")) {
                localPlayer.setAttackTime(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("DeathTime")) {
                localPlayer.setDeathTime(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("Health")) {
                localPlayer.setHealth(((ShortTag) localTag).getValue().shortValue());
            } else if (localTag.getName().equals("HurtTime")) {
                localPlayer.setHurtTime(((ShortTag) localTag).getValue().shortValue());
            } else if (str.equals("Armor")) {
                localPlayer.setArmor(readArmor((ListTag) localTag));
            } else if (str.equals("BedPositionX")) {
                localPlayer.setBedPositionX(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("BedPositionY")) {
                localPlayer.setBedPositionY(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("BedPositionZ")) {
                localPlayer.setBedPositionZ(((IntTag) localTag).getValue().intValue());
            } else if (localTag.getName().equals("Inventory")) {
                if (localTag instanceof ListTag) {
                    localPlayer.setInventory(readInventory((ListTag) localTag));
                } else {
                    HLog.error(TAG, "read inventory local tag is NOT list tag", new Object[0]);
                }
            } else if (localTag.getName().equals("Score")) {
                localPlayer.setScore(((IntTag) localTag).getValue().intValue());
            } else if (localTag.getName().equals("Sleeping")) {
                if (((ByteTag) localTag).getValue().byteValue() != (byte) 0) {
                    localPlayer.setSleeping(((ByteTag) localTag).getBooleanValue());
                }
            } else if (str.equals("SleepTimer")) {
                localPlayer.setSleepTimer(((ShortTag) localTag).getValue().shortValue());
            } else if (str.equals("SpawnX")) {
                localPlayer.setSpawnX(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("SpawnY")) {
                localPlayer.setSpawnY(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("SpawnZ")) {
                localPlayer.setSpawnZ(((IntTag) localTag).getValue().intValue());
            } else if (str.equals("abilities")) {
                readAbilities((CompoundTag) localTag, localPlayer.getAbilities());
            } else if (str.equals("TargetID")) {
                localPlayer.setTargetId(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("UniqueID")) {
                localPlayer.setUniqueId(((LongTag) localTag).getValue().longValue());
            } else if (str.equals("id")) {
                localPlayer.setId(((IntTag) localTag).getValue().intValue());
            } else if (!localTag.getName().equals("Persistent")) {
                Log.d(TAG, "Unhandled player tag: " + str);
            } else if (((ByteTag) localTag).getValue().byteValue() > (byte) 0) {
                localPlayer.setPersistent(((ByteTag) localTag).getBooleanValue());
            }
        }
        return localPlayer;
    }

    public static Entity readSingleEntity(CompoundTag paramCompoundTag) {
        Entity localEntity = null;
        for (Tag localTag : paramCompoundTag.getValue()) {
            if (localTag.getName().equals("id")) {
                Log.d(TAG, "locatag name is ID, tag = " + localTag);
                localEntity = readEntity(((IntTag) localTag).getValue().intValue(), paramCompoundTag);
                if (localEntity != null) {
                    break;
                }
            }
        }
        return localEntity;
    }

    public static Entity readEntity(int paramInt, CompoundTag paramCompoundTag) {
        Entity localEntity = createEntityById(paramInt);
        localEntity.setEntityTypeId(paramInt);
        EntityStore localEntityStore = (EntityStore) EntityStoreLookupService.idMap.get(Integer.valueOf(paramInt));
        if (localEntityStore == null) {
            Log.e(TAG, "Warning: unknown entity id " + paramInt + "; using default entity store.");
            localEntityStore = EntityStoreLookupService.defaultStore;
        }
        localEntityStore.load(localEntity, paramCompoundTag);
        return localEntity;
    }

    public static Entity createEntityById(int paramInt) {
        Entity localObject = new Entity();
        switch (paramInt) {
            case 10:
                return new Chicken();
            case 11:
                return new Cow();
            case 12:
                return new Pig();
            case 13:
                return new Sheep();
            case 32:
                return new Zombie();
            case 64:
                return new Item();
            default:
                EntityType entityType = EntityType.getById(paramInt);
                if (entityType == null || entityType.getEntityClass() == null) {
                    return localObject;
                }
                try {
                    return (Entity) entityType.getEntityClass().newInstance();
                } catch (InstantiationException e) {
                    Log.e(TAG, "createEntityById error1", e);
                    return localObject;
                } catch (IllegalAccessException e2) {
                    Log.e(TAG, "createEntityById error2", e2);
                    return localObject;
                }
        }
    }

    public static void readAbilities(CompoundTag paramCompoundTag, PlayerAbilities paramPlayerAbilities) {
        for (Tag localTag : paramCompoundTag.getValue()) {
            String str = localTag.getName();
            if (localTag instanceof ByteTag) {
                boolean value = ((ByteTag) localTag).getValue().byteValue() != (byte) 0;
                Log.v(TAG, "readAbilities key = " + str + ", value = " + value);
                if (str.equals("flying")) {
                    paramPlayerAbilities.flying = value;
                } else if (str.equals("instabuild")) {
                    paramPlayerAbilities.instabuild = value;
                } else if (str.equals("invulnerable")) {
                    paramPlayerAbilities.invulnerable = value;
                } else if (str.equals("mayfly")) {
                    paramPlayerAbilities.mayFly = value;
                }
            } else {
                Log.d(TAG, "Unsupported tag in readAbilities: " + str + " = " + localTag.getValue());
                if ((localTag instanceof FloatTag) && str.equals("walkspeed")) {
                    paramPlayerAbilities.walkSpeed = ((FloatTag) localTag).getValue().floatValue();
                }
            }
        }
    }

    public static List<InventorySlot> readInventory(ListTag<CompoundTag> paramListTag) {
        ArrayList localArrayList = new ArrayList();
        for (CompoundTag readInventorySlot : paramListTag.getValue()) {
            InventorySlot slot = readInventorySlot(readInventorySlot);
            ItemStack stack = slot.getContents();
            localArrayList.add(slot);
        }
        return localArrayList;
    }

    public static Vector3f readVector(ListTag<FloatTag> paramListTag) {
        List localList = paramListTag.getValue();
        return new Vector3f(((FloatTag) localList.get(0)).getValue().floatValue(), ((FloatTag) localList.get(1)).getValue().floatValue(), ((FloatTag) localList.get(2)).getValue().floatValue());
    }

    public static List<ItemStack> readArmor(ListTag<CompoundTag> paramListTag) {
        ArrayList localArrayList = new ArrayList();
        for (CompoundTag readItemStack : paramListTag.getValue()) {
            ItemStack stack = readItemStack(readItemStack);
            if (stack.getAmount() > 0) {
                localArrayList.add(stack);
            }
        }
        return localArrayList;
    }

    public static InventorySlot readInventorySlot(CompoundTag paramCompoundTag) {
        byte b = (byte) 0;
        short s1 = (short) 0;
        short s2 = (short) 0;
        int count = 0;
        for (Tag localTag : paramCompoundTag.getValue()) {
            if (localTag.getName().equals("Slot")) {
                b = ((ByteTag) localTag).getValue().byteValue();
            } else if (localTag.getName().equals("id")) {
                s1 = ((ShortTag) localTag).getValue().shortValue();
            } else if (localTag.getName().equals("Damage")) {
                s2 = ((ShortTag) localTag).getValue().shortValue();
            } else if (localTag.getName().equals("Count")) {
                count = new Byte(((ByteTag) localTag).getValue().byteValue()).intValue();
                if (count < 0) {
                    count += 256;
                }
            }
        }
        return new InventorySlot(b, new ItemStack(s1, s2, count));
    }

    public static ItemStack readItemStack(CompoundTag paramCompoundTag) {
        short s1 = (short) 0;
        short s2 = (short) 0;
        int count = 0;
        for (Tag localTag : paramCompoundTag.getValue()) {
            if (localTag.getName().equals("id")) {
                s1 = ((ShortTag) localTag).getValue().shortValue();
            } else if (localTag.getName().equals("Damage")) {
                s2 = ((ShortTag) localTag).getValue().shortValue();
            } else if (localTag.getName().equals("Count")) {
                count = new Byte(((ByteTag) localTag).getValue().byteValue()).intValue();
                if (count < 0) {
                    count += 256;
                }
            }
        }
        return new ItemStack(s1, s2, count);
    }

    public static ListTag<FloatTag> writeVector(Vector3f paramVector3f, String paramString) {
        ArrayList localArrayList = new ArrayList(3);
        localArrayList.add(new FloatTag("", paramVector3f.getX()));
        localArrayList.add(new FloatTag("", paramVector3f.getY()));
        localArrayList.add(new FloatTag("", paramVector3f.getZ()));
        return new ListTag(paramString, FloatTag.class, localArrayList);
    }

    public static CompoundTag writeEntity(Entity paramEntity) {
        return writeEntity(paramEntity, "");
    }

    public static CompoundTag writeEntity(Entity paramEntity, String paramString) {
        int i = paramEntity.getEntityTypeId();
        EntityStore localEntityStore = (EntityStore) EntityStoreLookupService.idMap.get(Integer.valueOf(i));
        if (localEntityStore == null) {
            Log.e(TAG, "Warning: unknown entity id " + i + "; using default entity store.");
            localEntityStore = EntityStoreLookupService.defaultStore;
        }
        List<Tag> localList = localEntityStore.save(paramEntity);
        Collections.sort(localList, new Comparator<Tag>() {
            public int compare(Tag lhs, Tag rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }

            public boolean equals(Object o) {
                if (o == null || !(o instanceof Tag)) {
                    return false;
                }
                return super.equals(o);
            }
        });
        return new CompoundTag(paramString, localList);
    }

    public static CompoundTag writeItemStack(ItemStack paramItemStack, String paramString) {
        return null;
    }

    public static ListTag<CompoundTag> writeInventory(List<InventorySlot> paramList, String paramString) {
        ArrayList localArrayList = new ArrayList(paramList.size());
        for (InventorySlot writeInventorySlot : paramList) {
            localArrayList.add(writeInventorySlot(writeInventorySlot));
        }
        return new ListTag(paramString, CompoundTag.class, localArrayList);
    }

    public static CompoundTag writeInventorySlot(InventorySlot paramInventorySlot) {
        ArrayList localArrayList = new ArrayList(4);
        ItemStack localItemStack = paramInventorySlot.getContents();
        localArrayList.add(new ByteTag("Count", (byte) localItemStack.getAmount()));
        localArrayList.add(new ShortTag("Damage", localItemStack.getDurability()));
        localArrayList.add(new ByteTag("Slot", paramInventorySlot.getSlot()));
        localArrayList.add(new ShortTag("id", localItemStack.getTypeId()));
        return new CompoundTag("", localArrayList);
    }

    public static CompoundTag writeLevel(LevelV010 paramLevel) {
        ArrayList localArrayList = new ArrayList();
        if (!Integer.valueOf(0).equals(Integer.valueOf(paramLevel.getGenerator()))) {
            localArrayList.add(new IntTag("GameType", paramLevel.getGameType()));
            localArrayList.add(new IntTag("Generator", paramLevel.getGenerator()));
        }
        localArrayList.add(new LongTag("LastPlayed", paramLevel.getLastPlayed()));
        localArrayList.add(new StringTag("LevelName", paramLevel.getLevelName()));
        localArrayList.add(new IntTag("Platform", paramLevel.getPlatform()));
        if (paramLevel.getPlayer() != null) {
            localArrayList.add(writePlayer(paramLevel.getPlayer(), "Player"));
        }
        localArrayList.add(new LongTag("RandomSeed", paramLevel.getRandomSeed()));
        localArrayList.add(new LongTag("SizeOnDisk", paramLevel.getSizeOnDisk()));
        localArrayList.add(new IntTag("SpawnX", paramLevel.getSpawnX()));
        localArrayList.add(new IntTag("SpawnY", paramLevel.getSpawnY()));
        localArrayList.add(new IntTag("SpawnZ", paramLevel.getSpawnZ()));
        localArrayList.add(new IntTag("StorageVersion", paramLevel.getStorageVersion()));
        localArrayList.add(new LongTag("Time", paramLevel.getTime()));
        localArrayList.add(new LongTag("dayCycleStopTime", paramLevel.getDayCycleStopTime()));
        localArrayList.add(new ByteTag("spawnMobs", paramLevel.getSpawnMobs()));
        return new CompoundTag("", localArrayList);
    }

    public static CompoundTag writeLevel(LevelV010 paramLevel, boolean isNewversion) {
        ArrayList localArrayList = new ArrayList();
        if (isNewversion) {
            IntTag generator = new IntTag("Generator", paramLevel.getGenerator());
            localArrayList.add(new IntTag("GameType", paramLevel.getGameType()));
            localArrayList.add(generator);
        } else {
            localArrayList.add(new IntTag("GameType", paramLevel.getGameType()));
            localArrayList.add(new IntTag("Generator", 1));
        }
        localArrayList.add(new LongTag("LastPlayed", paramLevel.getLastPlayed()));
        localArrayList.add(new StringTag("LevelName", paramLevel.getLevelName()));
        localArrayList.add(new IntTag("Platform", paramLevel.getPlatform()));
        if (paramLevel.getPlayer() != null) {
            localArrayList.add(writePlayer(paramLevel.getPlayer(), "Player"));
        }
        localArrayList.add(new LongTag("RandomSeed", paramLevel.getRandomSeed()));
        localArrayList.add(new LongTag("SizeOnDisk", paramLevel.getSizeOnDisk()));
        localArrayList.add(new IntTag("SpawnX", paramLevel.getSpawnX()));
        localArrayList.add(new IntTag("SpawnY", paramLevel.getSpawnY()));
        localArrayList.add(new IntTag("SpawnZ", paramLevel.getSpawnZ()));
        localArrayList.add(new IntTag("StorageVersion", paramLevel.getStorageVersion()));
        localArrayList.add(new LongTag("Time", paramLevel.getTime()));
        localArrayList.add(new LongTag("dayCycleStopTime", paramLevel.getDayCycleStopTime()));
        localArrayList.add(new IntTag("Generator", paramLevel.getGenerator()));
        localArrayList.add(new IntTag("GameType", paramLevel.getGameType()));
        localArrayList.add(new ByteTag("spawnMobs", paramLevel.getSpawnMobs()));
        return new CompoundTag("", localArrayList);
    }

    public static CompoundTag writeLevelV0110(LevelV0110 paramLevel) {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new LongTag("LastPlayed", paramLevel.getLastPlayed()));
        localArrayList.add(new StringTag("LevelName", paramLevel.getLevelName()));
        localArrayList.add(new IntTag("Platform", paramLevel.getPlatform()));
        localArrayList.add(new LongTag("RandomSeed", paramLevel.getRandomSeed()));
        localArrayList.add(new IntTag("SpawnX", paramLevel.getSpawnX()));
        localArrayList.add(new IntTag("SpawnY", paramLevel.getSpawnY()));
        localArrayList.add(new IntTag("SpawnZ", paramLevel.getSpawnZ()));
        localArrayList.add(new IntTag("StorageVersion", paramLevel.getStorageVersion()));
        localArrayList.add(new LongTag("Time", paramLevel.getTime()));
        localArrayList.add(new LongTag("dayCycleStopTime", paramLevel.getDayCycleStopTime()));
        localArrayList.add(new IntTag("Generator", paramLevel.getGenerator()));
        localArrayList.add(new IntTag("GameType", paramLevel.getGameType()));
        localArrayList.add(new ByteTag("spawnMobs", paramLevel.getSpawnMobs()));
        localArrayList.add(new IntTag("limitedWorldOriginX", paramLevel.getLimitedWorldOriginX()));
        localArrayList.add(new IntTag("limitedWorldOriginY", paramLevel.getLimitedWorldOriginY()));
        localArrayList.add(new IntTag("limitedWorldOriginZ", paramLevel.getLimitedWorldOriginZ()));
        localArrayList.add(new LongTag("creationTime", paramLevel.getCreationTime()));
        localArrayList.add(new LongTag("time", paramLevel.getTime()));
        localArrayList.add(new LongTag("dimension", (long) paramLevel.getDimension()));
        return new CompoundTag("", localArrayList);
    }

    public static CompoundTag writePlayer(PlayerV010 paramPlayer, String paramString) {
        return writePlayer(paramPlayer, paramString, false);
    }

    public static CompoundTag writePlayer(PlayerV010 paramPlayer, String paramString, boolean paramBoolean) {
        ArrayList localArrayList1 = new ArrayList();
        localArrayList1.add(new ShortTag("Air", paramPlayer.getAirTicks()));
        localArrayList1.add(new FloatTag("FallDistance", paramPlayer.getFallDistance()));
        localArrayList1.add(new ShortTag("Fire", paramPlayer.getFireTicks()));
        localArrayList1.add(writeVector(paramPlayer.getVelocity(), "Motion"));
        localArrayList1.add(writeVector(paramPlayer.getLocation(), "Pos"));
        ArrayList localArrayList2 = new ArrayList(2);
        localArrayList2.add(new FloatTag("", paramPlayer.getYaw()));
        localArrayList2.add(new FloatTag("", paramPlayer.getPitch()));
        localArrayList1.add(new ListTag("Rotation", FloatTag.class, localArrayList2));
        localArrayList1.add(new ByteTag("OnGround", paramPlayer.isOnGround()));
        localArrayList1.add(new ShortTag("AttackTime", paramPlayer.getAttackTime()));
        localArrayList1.add(new ShortTag("DeathTime", paramPlayer.getDeathTime()));
        localArrayList1.add(new ShortTag("Health", paramPlayer.getHealth()));
        localArrayList1.add(new ShortTag("HurtTime", paramPlayer.getHurtTime()));
        if (paramPlayer.getArmor() != null) {
            localArrayList1.add(writeArmor(paramPlayer.getArmor(), "Armor"));
        }
        localArrayList1.add(new IntTag("BedPositionX", paramPlayer.getBedPositionX()));
        localArrayList1.add(new IntTag("BedPositionY", paramPlayer.getBedPositionY()));
        localArrayList1.add(new IntTag("BedPositionZ", paramPlayer.getBedPositionZ()));
        localArrayList1.add(new IntTag("Dimension", paramPlayer.getDimension()));
        localArrayList1.add(writeInventory(paramPlayer.getInventory(), "Inventory"));
        localArrayList1.add(new IntTag("Score", paramPlayer.getScore()));
        localArrayList1.add(new ByteTag("Sleeping", paramPlayer.isSleeping()));
        localArrayList1.add(new ShortTag("SleepTimer", paramPlayer.getSleepTimer()));
        localArrayList1.add(new IntTag("SpawnX", paramPlayer.getSpawnX()));
        localArrayList1.add(new IntTag("SpawnY", paramPlayer.getSpawnY()));
        localArrayList1.add(new IntTag("SpawnZ", paramPlayer.getSpawnZ()));
        localArrayList1.add(writeAbilities(paramPlayer.getAbilities(), "abilities"));
        if (paramPlayer.getRiding() != null) {
            localArrayList1.add(writeEntity(paramPlayer.getRiding(), "Riding"));
        }
        localArrayList1.add(new IntTag("id", EntityType.PLAYER.getId()));
        Collections.sort(localArrayList1, new Comparator<Tag>() {
            public int compare(Tag lhs, Tag rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }

            public boolean equals(Object o) {
                if (o == null || !(o instanceof Tag)) {
                    return false;
                }
                return super.equals(o);
            }
        });
        return new CompoundTag(paramString, localArrayList1);
    }

    public static CompoundTag writePlayerV0110(PlayerV0110 paramPlayer, String paramString, boolean paramBoolean) {
        ArrayList localArrayList1 = new ArrayList();
        localArrayList1.add(new ShortTag("Air", paramPlayer.getAir()));
        localArrayList1.add(new FloatTag("FallDistance", paramPlayer.getFallDistance()));
        localArrayList1.add(new ShortTag("Fire", paramPlayer.getFire()));
        localArrayList1.add(writeVector(paramPlayer.getPos(), "Motion"));
        localArrayList1.add(writeVector(paramPlayer.getPos(), "Pos"));
        ArrayList localArrayList2 = new ArrayList(2);
        localArrayList2.add(new FloatTag("", (float) paramPlayer.getRotation().getYaw()));
        localArrayList2.add(new FloatTag("", (float) paramPlayer.getRotation().getPitch()));
        localArrayList1.add(new ListTag("Rotation", FloatTag.class, localArrayList2));
        localArrayList1.add(new ByteTag("OnGround", paramPlayer.isOnGround()));
        localArrayList1.add(new ByteTag("Persistent", paramPlayer.isPersistent()));
        localArrayList1.add(new ShortTag("AttackTime", paramPlayer.getAttackTime()));
        localArrayList1.add(new ShortTag("DeathTime", paramPlayer.getDeathTime()));
        localArrayList1.add(new ShortTag("Health", paramPlayer.getHealth()));
        localArrayList1.add(new ShortTag("HurtTime", paramPlayer.getHurtTime()));
        if (paramPlayer.getArmor() != null) {
            localArrayList1.add(writeArmor(paramPlayer.getArmor(), "Armor"));
        }
        localArrayList1.add(new IntTag("BedPositionX", paramPlayer.getBedPositionX()));
        localArrayList1.add(new IntTag("BedPositionY", paramPlayer.getBedPositionY()));
        localArrayList1.add(new IntTag("BedPositionZ", paramPlayer.getBedPositionZ()));
        if (paramPlayer.getInventory() != null) {
            localArrayList1.add(writeInventory(paramPlayer.getInventory(), "Inventory"));
        }
        localArrayList1.add(new IntTag("Score", paramPlayer.getScore()));
        localArrayList1.add(new ByteTag("Sleeping", paramPlayer.isSleeping()));
        localArrayList1.add(new ShortTag("SleepTimer", paramPlayer.getSleepTimer()));
        localArrayList1.add(new IntTag("SpawnX", paramPlayer.getSpawnX()));
        localArrayList1.add(new IntTag("SpawnY", paramPlayer.getSpawnY()));
        localArrayList1.add(new IntTag("SpawnZ", paramPlayer.getSpawnZ()));
        if (paramPlayer.getAbilities() != null) {
            localArrayList1.add(writeAbilities(paramPlayer.getAbilities(), "abilities"));
        }
        localArrayList1.add(new LongTag("TargetID", paramPlayer.getTargetId()));
        localArrayList1.add(new LongTag("UniqueID", paramPlayer.getUniqueId()));
        localArrayList1.add(new IntTag("id", paramPlayer.getId()));
        Collections.sort(localArrayList1, new Comparator<Tag>() {
            public int compare(Tag lhs, Tag rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }

            public boolean equals(Object o) {
                if (o == null || !(o instanceof Tag)) {
                    return false;
                }
                return super.equals(o);
            }
        });
        return new CompoundTag(paramString, localArrayList1);
    }

    public static CompoundTag writeAbilities(PlayerAbilities paramPlayerAbilities, String paramString) {
        ArrayList localArrayList = new ArrayList(5);
        localArrayList.add(new ByteTag("flying", paramPlayerAbilities.flying));
        localArrayList.add(new ByteTag("instabuild", paramPlayerAbilities.instabuild));
        localArrayList.add(new ByteTag("invulnerable", paramPlayerAbilities.invulnerable));
        localArrayList.add(new ByteTag("mayfly", paramPlayerAbilities.mayFly));
        return new CompoundTag(paramString, localArrayList);
    }

    public static ListTag<CompoundTag> writeArmor(List<ItemStack> paramList, String paramString) {
        ArrayList localArrayList = new ArrayList(paramList.size());
        for (ItemStack writeItemStack : paramList) {
            localArrayList.add(writeItemStack(writeItemStack, ""));
        }
        return new ListTag(paramString, CompoundTag.class, localArrayList);
    }

    public static TileEntity readSingleTileEntity(CompoundTag paramCompoundTag) {
        TileEntity localTileEntity = null;
        for (Tag localTag : paramCompoundTag.getValue()) {
            if (localTag.getName().equals("id")) {
                if (localTag instanceof StringTag) {
                    localTileEntity = readTileEntity(((StringTag) localTag).getValue(), paramCompoundTag);
                    if (localTileEntity != null) {
                        break;
                    }
                } else {
                    HLog.warn(TAG, "read tile entity tag = " + localTag, new Object[0]);
                }
            }
        }
        return localTileEntity;
    }

    public static List<TileEntity> readTileEntitiesPart(List<CompoundTag> paramList) {
        ArrayList localArrayList = new ArrayList(paramList.size());
        for (CompoundTag localCompoundTag : paramList) {
            for (Tag localTag : localCompoundTag.getValue()) {
                if (localTag.getName().equals("id") && (localTag instanceof StringTag)) {
                    localArrayList.add(readTileEntity(((StringTag) localTag).getValue(), localCompoundTag));
                }
            }
        }
        return localArrayList;
    }

    public static TileEntity readTileEntity(String paramString, CompoundTag paramCompoundTag) {
        TileEntity localTileEntity = createTileEntityById(paramString);
        localTileEntity.setId(paramString);
        TileEntityStore localTileEntityStore = TileEntityStoreLookupService.getStoreById(paramString);
        if (localTileEntityStore == null) {
            Log.e(TAG, "Warning: unknown tile entity id " + paramString + "; using default tileentity store.");
            localTileEntityStore = TileEntityStoreLookupService.defaultStore;
        }
        localTileEntityStore.load(localTileEntity, paramCompoundTag);
        return localTileEntity;
    }

    public static TileEntity createTileEntityById(String paramString) {
        return TileEntityStoreLookupService.createTileEntityById(paramString);
    }
}
