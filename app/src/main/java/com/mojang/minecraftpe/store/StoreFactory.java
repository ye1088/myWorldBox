package com.mojang.minecraftpe.store;

public class StoreFactory {
    public static Store createAmazonAppStore(StoreListener paramStoreListener) {
        return new Store(paramStoreListener);
    }

    public static Store createGooglePlayStore(String paramString, StoreListener paramStoreListener) {
        return new Store(paramStoreListener);
    }

    public static Store createSamsungAppStore(StoreListener paramStoreListener) {
        return new Store(paramStoreListener);
    }
}
