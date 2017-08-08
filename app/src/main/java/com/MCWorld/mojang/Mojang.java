package com.MCWorld.mojang;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mojang.converter.EntityDataConverter;
import com.MCWorld.mojang.converter.EntityDataConverter.EntityData;
import com.MCWorld.mojang.converter.InventorySlot;
import com.MCWorld.mojang.converter.Level;
import com.MCWorld.mojang.converter.LevelDataConverter;
import com.MCWorld.mojang.converter.LevelV010;
import com.MCWorld.mojang.converter.LevelV0110;
import com.MCWorld.mojang.entity.Animal;
import com.MCWorld.mojang.entity.DataConstants.ColorDataItem;
import com.MCWorld.mojang.entity.Entity;
import com.MCWorld.mojang.entity.EntityType;
import com.MCWorld.mojang.entity.LivingEntity;
import com.MCWorld.mojang.entity.Player;
import com.MCWorld.mojang.entity.PlayerV0110;
import com.MCWorld.mojang.entity.Sheep;
import com.MCWorld.mojang.options.Options;
import com.MCWorld.mojang.options.OptionsUtils;
import com.MCWorld.mojang.options.OptionsV010;
import com.MCWorld.mojang.util.Vector3f;
import com.MCWorld.utils.UtilsFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class Mojang {
    private static Object LOCK = new Object();
    private static final String TAG = "Mojang_Tag";
    private static Mojang instance;
    private Level level;
    private Handler mHandler;
    private Options options;
    private ExecutorService service = new ThreadPoolExecutor(3, 8, 15, TimeUnit.SECONDS, new ArrayBlockingQueue(16), new TFactory(this, null), new DiscardPolicy());
    private HandlerThread thread = new HandlerThread("Mojang");
    public WorldItem worldItem;

    public static synchronized Mojang instance() {
        Mojang mojang;
        synchronized (Mojang.class) {
            if (instance == null) {
                instance = new Mojang();
            }
            mojang = instance;
        }
        return mojang;
    }

    private Mojang() {
        this.thread.start();
        this.mHandler = new Handler(this.thread.getLooper());
    }

    public void init(String fileName, int reserved_param, Object inputObject) throws Exception {
        String path = Environment.getExternalStorageDirectory() + "/games/com.mojang/minecraftWorlds/" + fileName;
        compatibleOldVersionLevelDat(path);
        setWorldItem(fileName, path);
        if (this.worldItem == null) {
            throw new IllegalArgumentException("no world item exsists");
        }
        readOptions();
        postEvent(262, new Object[0]);
        asynReloadLevelData(fileName, inputObject);
    }

    public void init(String fileName, String path) throws Exception {
        compatibleOldVersionLevelDat(path);
        setWorldItem(fileName, path);
        if (this.worldItem == null) {
            throw new IllegalArgumentException("no world item exsists");
        }
        postEvent(262, new Object[0]);
        asynReloadLevelData(fileName, null);
    }

    private void compatibleOldVersionLevelDat(String mapDirPath) {
        if (!UtilsFile.isExist(mapDirPath + File.separator + "level.dat") && UtilsFile.isExist(mapDirPath + File.separator + "level.dat_old")) {
            UtilsFile.copyFile(mapDirPath + File.separator + "level.dat_old", mapDirPath + File.separator + "level.dat");
        }
    }

    public void uninit() {
        this.worldItem = null;
        this.level = null;
    }

    public void asynReadOptions() {
        this.service.submit(new 1(this));
    }

    public Options readOptions() {
        this.options = OptionsUtils.getOptions();
        return this.options;
    }

    public Options getOptions() {
        return this.options;
    }

    public void setGameThirdPersionAngle(boolean yes) {
        int i = 1;
        if (this.options == null) {
            readOptions();
        }
        HLog.verbose(TAG, "setGameThirdPersionAngle yes = " + yes, new Object[0]);
        if (this.options != null) {
            int i2;
            int intValue = this.options.getGame_thirdperson().intValue();
            if (yes) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (intValue != i2) {
                Options options = this.options;
                if (!yes) {
                    i = 0;
                }
                options.setGame_thirdperson(Integer.valueOf(i));
                OptionsUtils.writeOption(this.options);
            }
        }
    }

    public boolean isGameThirdPersionAngle() {
        if (this.options == null) {
            readOptions();
        }
        if (this.options == null) {
            return false;
        }
        if (this.options.getGame_thirdperson().intValue() == 1) {
            return true;
        }
        return false;
    }

    public void asynReloadLevelData(String filename, Object object) {
        this.service.submit(new 2(this, filename, object));
    }

    public void reloadLevelData() throws IOException {
        if (this.worldItem == null) {
            return;
        }
        if (this.options instanceof OptionsV010) {
            this.level = LevelDataConverter.readV010(this.worldItem.levelDat);
        } else {
            this.level = LevelDataConverter.readV0110(this.worldItem.levelDat);
        }
    }

    private void setWorldItem(String name, String path) {
        if (name != null && name.length() > 0) {
            WorldItem item = new WorldItem(new File(path));
            item.setFileName(name);
            setWorldItem(item);
        }
    }

    private void setWorldItem(WorldItem item) {
        this.worldItem = item;
    }

    public WorldItem getWorldItem() {
        return this.worldItem;
    }

    public Player getPlayer() {
        if (this.level != null) {
            return this.level.getPlayer();
        }
        HLog.error(TAG, "palyer is NULL", new Object[0]);
        return new PlayerV0110();
    }

    public List<Entity> getCacheEntity() {
        if (this.level != null) {
            return this.level.getEntities();
        }
        return null;
    }

    public Level getLevel() {
        return this.level;
    }

    public boolean mayFly() {
        boolean z = false;
        try {
            z = this.level.getPlayer().getAbilities().isMayFly();
        } catch (Exception e) {
            HLog.error(TAG, "mayFly level = " + this.level, e, new Object[z]);
        }
        return z;
    }

    public boolean invulnerable() {
        boolean z = false;
        try {
            z = this.level.getPlayer().getAbilities().isInvulnerable();
        } catch (Exception e) {
            HLog.error(TAG, "invulnerable level = " + this.level, e, new Object[z]);
        }
        return z;
    }

    public void setMayFlying(boolean mayFlying) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "setMayFlying error[world item is NULL]", new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setMayFlying level is NULL, level = " + this.level, new Object[0]);
        } else {
            HLog.verbose(TAG, "setMayFlying yes = " + mayFlying, new Object[0]);
            if (mayFly() != mayFlying) {
                this.level.getPlayer().getAbilities().setMayFly(mayFlying);
                writeLevelData();
                reloadLevelData();
            }
        }
    }

    private void writeLevelData() throws IOException {
        if (this.options == null) {
            return;
        }
        if (this.options instanceof OptionsV010) {
            LevelDataConverter.writeV010((LevelV010) this.level, this.worldItem.levelDat);
        } else {
            LevelDataConverter.writeV0110((LevelV0110) this.level, this.worldItem.levelDat);
        }
    }

    private void writeLevelData(boolean isNeedOption) throws IOException {
        if (!isNeedOption || this.options != null) {
            if (this.options instanceof OptionsV010) {
                LevelDataConverter.writeV010((LevelV010) this.level, this.worldItem.levelDat);
            } else {
                LevelDataConverter.writeV0110((LevelV0110) this.level, this.worldItem.levelDat);
            }
        }
    }

    public void setInvulnerable(boolean invulnerable) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "setInvulnerable error[world item is NULL]", new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setInvulnerable level is NULL, level = " + this.level, new Object[0]);
        } else if (invulnerable() != invulnerable) {
            this.level.getPlayer().getAbilities().setInvulnerable(invulnerable);
            writeLevelData();
            reloadLevelData();
        }
    }

    public EntityData readEntityData() throws IOException {
        if (this.worldItem == null || this.level == null) {
            HLog.error(TAG, "readEntityData error1[world item is NULL], level  =" + this.level, new Object[0]);
            return null;
        }
        EntityData entityData = EntityDataConverter.read(this.worldItem.db);
        if (entityData == null) {
            HLog.error(TAG, "readEntityData error2[entityData is NULL]", new Object[0]);
            return null;
        }
        this.level.setEntities(entityData.entities);
        this.level.setTileEntities(entityData.tileEntities);
        return entityData;
    }

    public void asynReadEntityData() {
        Log.d(TAG, "asynReadEntityData1");
        this.service.submit(new 3(this));
    }

    private void postEvent(int msg, Object... objects) {
        new Handler(Looper.getMainLooper()).post(new 4(this, msg, objects));
    }

    public void setEntity(EntityType entityType, int count) throws IOException, IllegalAccessException, InstantiationException {
        Map<EntityType, Integer> data = new HashMap();
        data.put(entityType, new Integer(count));
        batAddEntity(data);
    }

    public void asynSetEntity(EntityType entityType, int count) {
        Map<EntityType, Integer> data = new HashMap();
        data.put(entityType, new Integer(count));
        asynBatchAddEntity(data);
    }

    public void batchAddEntity(Map<EntityType, Integer> entityTypeIntegerMap, boolean asyn) throws IllegalAccessException, InstantiationException, IOException {
        if (entityTypeIntegerMap != null) {
            if (this.worldItem == null) {
                Log.e(TAG, "addEntity error[world item is NULL]");
            } else if (this.level == null || this.level.getEntities() == null) {
                HLog.error(TAG, "addEntity level is " + this.level, new Object[0]);
            } else {
                List<Entity> result = new ArrayList();
                for (EntityType entityType : entityTypeIntegerMap.keySet()) {
                    List<Entity> typeResult = new ArrayList();
                    int count = ((Integer) entityTypeIntegerMap.get(entityType)).intValue();
                    for (Entity entity : this.level.getEntities()) {
                        if (entity.getEntityType() == entityType) {
                            typeResult.add(entity);
                        } else if (!result.contains(entity)) {
                            result.add(entity);
                        }
                    }
                    if (typeResult.size() < count) {
                        List<Entity> localList = new ArrayList();
                        Vector3f vector3f = getPlayer().getPos();
                        for (int i = 0; i < count; i++) {
                            Entity localEntity = (Entity) entityType.getEntityClass().newInstance();
                            localEntity.setEntityTypeId(entityType.getId());
                            localEntity.setLocation(vector3f);
                            if (localEntity instanceof LivingEntity) {
                                ((LivingEntity) localEntity).setHealth((short) ((LivingEntity) localEntity).getMaxHealth());
                            }
                            localList.add(localEntity);
                        }
                        result.addAll(localList);
                    } else {
                        result.addAll(typeResult.subList(0, count));
                    }
                }
                this.level.setEntities(result);
                if (asyn) {
                    this.service.submit(new 5(this, result, entityTypeIntegerMap));
                    return;
                }
                EntityDataConverter.write(result, null, this.worldItem.db);
                readEntityData();
            }
        }
    }

    public void batAddEntity(Map<EntityType, Integer> entityTypeIntegerMap) throws IllegalAccessException, InstantiationException, IOException {
        batchAddEntity(entityTypeIntegerMap, false);
    }

    public void asynBatchAddEntity(Map<EntityType, Integer> entityTypeIntegerMap) {
        try {
            batchAddEntity(entityTypeIntegerMap, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public void deleteEntity(EntityType type, boolean asyn) throws IOException {
        if (this.worldItem == null || type == null) {
            Log.e(TAG, "deleteEntity error[world item or tyep is NULL], type = " + type);
        } else if (this.level != null) {
            List<Entity> entities = this.level.getEntities();
            List<Entity> result = new ArrayList();
            for (Entity entity : entities) {
                if (entity.getEntityType() != type) {
                    result.add(entity);
                }
            }
            this.level.setEntities(result);
            if (asyn) {
                this.service.submit(new 6(this, result));
                return;
            }
            EntityDataConverter.write(result, null, this.worldItem.db);
            readEntityData();
        } else {
            HLog.error(TAG, "deleteEntity error level is NULL, type = " + type, new Object[0]);
        }
    }

    public void deleteEntity(EntityType type) throws IOException {
        deleteEntity(type, false);
    }

    public void asynDeleteEntity(EntityType type) {
        try {
            Log.d(TAG, "delete entity1");
            deleteEntity(type, true);
        } catch (IOException e) {
        }
    }

    public void batchDeleteEntity(List<EntityType> types, boolean asyn) throws IOException {
        if (this.worldItem == null || types == null) {
            Log.e(TAG, "deleteEntity error[world item or tyep is NULL], type = " + types);
        } else if (this.level != null) {
            List<Entity> entities = this.level.getEntities();
            List<Entity> result = new ArrayList();
            for (Entity entity : entities) {
                if (!types.contains(entity.getEntityType())) {
                    result.add(entity);
                }
            }
            this.level.setEntities(result);
            if (asyn) {
                this.service.submit(new 7(this, result));
                return;
            }
            EntityDataConverter.write(result, null, this.worldItem.db);
            readEntityData();
        } else {
            HLog.error(TAG, "deleteEntity error level is NULL", new Object[0]);
        }
    }

    public void asynBatchDeleteEntity(List<EntityType> types) {
        try {
            batchDeleteEntity(types, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void batchDeleteEntity(List<EntityType> types) throws IOException {
        batchDeleteEntity(types, false);
    }

    public void addInventory(List<InventorySlot> inventorySlots, boolean asyn) throws IOException {
        if (this.worldItem == null || this.level == null) {
            HLog.error(TAG, "addInventory error[world item is NULL], level = " + this.level, new Object[0]);
        } else if (inventorySlots == null || inventorySlots.size() <= 0) {
            HLog.error(TAG, "addInventory error[paramList INVALID]", new Object[0]);
        } else {
            List<InventorySlot> original = this.level.getPlayer().getInventory();
            if (original == null) {
                original = new ArrayList();
            }
            List<InventorySlot> dataResult = new ArrayList();
            List<InventorySlot> everAdd = new ArrayList();
            for (InventorySlot org : original) {
                for (InventorySlot add : inventorySlots) {
                    int slot1 = new Byte(org.getSlot()).intValue();
                    int slot2 = new Byte(add.getSlot()).intValue();
                    if (!dataResult.contains(org)) {
                        dataResult.add(org);
                    }
                    if (slot1 == slot2) {
                        if (org.getContents().getTypeId() == add.getContents().getTypeId()) {
                            org.getContents().setAmount(org.getContents().getAmount() + add.getContents().getAmount());
                        }
                        everAdd.add(add);
                    }
                }
            }
            inventorySlots.removeAll(everAdd);
            dataResult.addAll(inventorySlots);
            this.level.getPlayer().setInventory(dataResult);
            if (asyn) {
                this.service.submit(new 8(this));
                return;
            }
            writeLevelData();
            reloadLevelData();
        }
    }

    public void addInventory(List<InventorySlot> inventorySlots) throws IOException {
        addInventory(inventorySlots, false);
    }

    public void asynAddInventory(List<InventorySlot> inventorySlots) {
        try {
            addInventory(inventorySlots, true);
        } catch (IOException e) {
        }
    }

    public List<InventorySlot> readInventory() throws IOException {
        if (this.level == null) {
            HLog.error(TAG, "readInventory level is NULL", new Object[0]);
            return null;
        }
        reloadLevelData();
        return this.level.getPlayer().getInventory();
    }

    public void asynReadInventory() {
        this.service.submit(new 9(this));
    }

    public void deleteInventory(byte slot, boolean asyn) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "deleteInventory error[world item is NULL]", new Object[0]);
        } else if (this.level != null) {
            List<InventorySlot> list = this.level.getPlayer().getInventory();
            if (list != null) {
                List<InventorySlot> result = new ArrayList();
                for (InventorySlot item : list) {
                    if (item.getSlot() != slot) {
                        result.add(item);
                    }
                }
                this.level.getPlayer().setInventory(result);
                if (asyn) {
                    this.service.submit(new 10(this));
                    return;
                }
                writeLevelData();
                reloadLevelData();
            }
        } else {
            HLog.error(TAG, "deleteInventory level is NULL, slot = " + slot, new Object[0]);
        }
    }

    public void deleteInventory(byte slot) throws IOException {
        deleteInventory(slot, false);
    }

    public void asynDeleteInventory(byte slot) {
        try {
            deleteInventory(slot, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNight() throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "setNight error[world item is NULL]", new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setNight level is NULL, level = " + this.level, new Object[0]);
        } else {
            this.level.setTime(11040 + ((this.level.getTime() / 19200) * 19200));
            writeLevelData();
            reloadLevelData();
        }
    }

    public void setDay() throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "setDay error[world item is NULL]", new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setDay level is NULL, level = " + this.level, new Object[0]);
        } else {
            this.level.setTime((this.level.getTime() / 19200) * 19200);
            writeLevelData();
            reloadLevelData();
        }
    }

    public boolean isDay() {
        if (this.level == null) {
            HLog.error(TAG, "isDay level is NULL, level = " + this.level, new Object[0]);
            return true;
        }
        long time = this.level.getTime();
        if (time >= 9600 + ((time / 19200) * 19200)) {
            return false;
        }
        return true;
    }

    public int getGameMode() {
        if (this.level != null) {
            return this.level.getGameType();
        }
        HLog.error(TAG, "getGameMode level is NULL, level = " + this.level, new Object[0]);
        return 0;
    }

    public void setGameMode(int gameType) throws IOException {
        if (this.level == null) {
            HLog.error(TAG, "setGameType level is NULL, level = " + this.level, new Object[0]);
        } else if (getGameMode() != gameType) {
            this.level.setGameType(gameType);
            this.level.getPlayer().getAbilities().initForGameType(gameType);
            writeLevelData();
            reloadLevelData();
        }
    }

    public void growup(EntityType type, boolean grow) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "growup error[world item is NULL]", new Object[0]);
        } else if (this.level != null && this.level.getEntities() != null) {
            HLog.warn(TAG, "growup level is " + this.level, new Object[0]);
            List<Entity> paramList = this.level.getEntities();
            for (Entity entity : paramList) {
                if ((entity instanceof Animal) && entity.getEntityType() == type) {
                    if (grow) {
                        ((Animal) entity).setAge(10);
                    } else {
                        ((Animal) entity).setAge(0);
                    }
                }
            }
            EntityDataConverter.write(paramList, null, this.worldItem.db);
            readEntityData();
        }
    }

    public void paintSheep(ColorDataItem item) throws IOException {
        if (this.worldItem == null || item == null) {
            HLog.error(TAG, "paintSheep error[world item is NULL], item = " + item, new Object[0]);
        } else if (this.level != null && this.level.getEntities() != null) {
            HLog.warn(TAG, "paintSheep level is " + this.level, new Object[0]);
            List<Entity> paramList = this.level.getEntities();
            for (Entity entity : paramList) {
                if (entity.getEntityType() == EntityType.SHEEP) {
                    ((Sheep) entity).setColor(Byte.valueOf(item.getColorId().toString()).byteValue());
                }
            }
            EntityDataConverter.write(paramList, null, this.worldItem.db);
            readEntityData();
        }
    }

    public void setSpeed(float speed) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "setSpeed error[world item is NULL], speed = " + speed, new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setSpeed level is NULL, level = " + this.level, new Object[0]);
        } else {
            this.level.getPlayer().getAbilities().setWalkSpeed(speed);
            writeLevelData();
            reloadLevelData();
        }
    }

    public void renameGame(String name) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "renameGame error[world item is NULL], name = " + name, new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "renameGame level is NULL, level = " + this.level, new Object[0]);
        } else if (name == null || name.length() < 0) {
            HLog.error(TAG, "renameGame with invalid param", new Object[0]);
        } else {
            this.level.setLevelName(name);
            writeLevelData();
            reloadLevelData();
        }
    }

    public void renameMap(String name) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "renameGame error[world item is NULL], name = " + name, new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "renameGame level is NULL, level = " + this.level, new Object[0]);
        } else if (name == null || name.length() < 0) {
            HLog.error(TAG, "renameGame with invalid param", new Object[0]);
        } else {
            this.level.setLevelName(name);
            writeLevelData(false);
            reloadLevelData();
        }
    }

    public void alterMapRandomSeed(long randomSeed) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "renameGame error[world item is NULL]", new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "renameGame level is NULL, level = " + this.level, new Object[0]);
        } else {
            this.level.setRandomSeed(randomSeed);
            writeLevelData(false);
            reloadLevelData();
        }
    }

    public void alterMapSpawn(int posX, int posY, int posZ) throws IOException {
        Vector3f location = new Vector3f((float) posX, (float) posY, (float) posZ);
        if (this.worldItem == null) {
            HLog.error(TAG, "setCurrentLocation error[world item is NULL], location = " + location, new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setCurrentLocation level is NULL, level = " + this.level, new Object[0]);
        } else {
            this.level.setSpawnX(posX);
            this.level.setSpawnY(posY);
            this.level.setSpawnZ(posZ);
            writeLevelData(false);
            reloadLevelData();
        }
    }

    public void setLastPlayed(long lastPlayed) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "setCurrentLocation error[world item is NULL], lastPlayed = " + lastPlayed, new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setCurrentLocation level is NULL, level = " + this.level, new Object[0]);
        } else {
            this.level.setLastPlayed(lastPlayed);
            writeLevelData(false);
            reloadLevelData();
        }
    }

    public long getLastPlayed() throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "DTPrint setCurrentLocation error[world item is NULL] ", new Object[0]);
            return 0;
        } else if (this.level == null) {
            HLog.error(TAG, "DTPrint setCurrentLocation level is NULL, level = " + this.level, new Object[0]);
            return 0;
        } else {
            long ret = this.level.getLastPlayed();
            HLog.verbose(TAG, "DTPrint getLastPlayed is " + ret, new Object[0]);
            return ret;
        }
    }

    public void setCurrentLocation(Vector3f location) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "setCurrentLocation error[world item is NULL], location = " + location, new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setCurrentLocation level is NULL, level = " + this.level, new Object[0]);
        } else if (location == null) {
            HLog.error(TAG, "setCurrentLocation with invalid param", new Object[0]);
        } else {
            this.level.getPlayer().setPos(location);
            writeLevelData();
            reloadLevelData();
        }
    }

    public void setToRebornLocation() throws IOException {
        if (this.level == null) {
            HLog.error(TAG, "setToRebornLocation level is NULL, level = " + this.level, new Object[0]);
        } else {
            setCurrentLocation(new Vector3f((float) this.level.getSpawnX(), (float) this.level.getSpawnY(), (float) this.level.getSpawnZ()));
        }
    }

    public void getSpawnPoint(Vector3f inputSpawnPoint) throws IOException {
        if (this.level == null) {
            HLog.error(TAG, "getSpawnPoint level is NULL, level = " + this.level, new Object[0]);
            return;
        }
        inputSpawnPoint.x = (float) this.level.getSpawnX();
        inputSpawnPoint.y = (float) this.level.getSpawnY();
        inputSpawnPoint.z = (float) this.level.getSpawnZ();
    }

    public void setSpawnPoint(int x, int y, int z) throws IOException {
        if (this.worldItem == null) {
            HLog.error(TAG, "setDay error[world item is NULL]", new Object[0]);
        } else if (this.level == null) {
            HLog.error(TAG, "setDay level is NULL, level = " + this.level, new Object[0]);
        } else {
            this.level.setSpawnX(x);
            this.level.setSpawnY(y);
            this.level.setSpawnZ(z);
            writeLevelData();
            reloadLevelData();
        }
    }
}
