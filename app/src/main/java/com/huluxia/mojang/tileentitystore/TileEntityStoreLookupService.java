package com.huluxia.mojang.tileentitystore;

import com.huluxia.mojang.tileentity.ChestTileEntity;
import com.huluxia.mojang.tileentity.FurnaceTileEntity;
import com.huluxia.mojang.tileentity.NetherReactorTileEntity;
import com.huluxia.mojang.tileentity.SignTileEntity;
import com.huluxia.mojang.tileentity.TileEntity;
import java.util.HashMap;
import java.util.Map;

public class TileEntityStoreLookupService {
    public static Map<String, Class> classMap = new HashMap();
    public static TileEntityStore<TileEntity> defaultStore = new TileEntityStore();
    public static Map<String, TileEntityStore> idMap = new HashMap();

    static {
        addStore("Furnace", new FurnaceTileEntityStore(), FurnaceTileEntity.class);
        addStore("Chest", new ChestTileEntityStore(), ChestTileEntity.class);
        addStore("NetherReactor", new NetherReactorTileEntityStore(), NetherReactorTileEntity.class);
        addStore("Sign", new SignTileEntityStore(), SignTileEntity.class);
    }

    public static void addStore(String paramString, TileEntityStore paramTileEntityStore, Class paramClass) {
        String str = paramString.toUpperCase();
        idMap.put(str, paramTileEntityStore);
        classMap.put(str, paramClass);
    }

    public static TileEntity createTileEntityById(String paramString) {
        Class localClass = (Class) classMap.get(paramString.toUpperCase());
        if (localClass == null) {
            return new TileEntity();
        }
        try {
            return (TileEntity) localClass.newInstance();
        } catch (Exception localException) {
            localException.printStackTrace();
            return new TileEntity();
        }
    }

    public static TileEntityStore getStoreById(String paramString) {
        return (TileEntityStore) idMap.get(paramString.toUpperCase());
    }
}
