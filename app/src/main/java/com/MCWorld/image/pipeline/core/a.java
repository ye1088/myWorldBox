package com.MCWorld.image.pipeline.core;

import com.MCWorld.image.base.cache.disk.c;
import com.MCWorld.image.base.cache.disk.d;
import com.MCWorld.image.base.cache.disk.h;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: DiskStorageCacheFactory */
public class a implements d {
    private b Fe;

    public a(b diskStorageFactory) {
        this.Fe = diskStorageFactory;
    }

    public static d a(b diskCacheConfig, c diskStorage) {
        return a(diskCacheConfig, diskStorage, Executors.newSingleThreadExecutor());
    }

    public static d a(b diskCacheConfig, c diskStorage, Executor executorForBackgroundInit) {
        return new d(diskStorage, diskCacheConfig.gF(), new d.b(diskCacheConfig.gE(), diskCacheConfig.gD(), diskCacheConfig.gC()), diskCacheConfig.gH(), diskCacheConfig.gG(), diskCacheConfig.gI(), diskCacheConfig.getContext(), executorForBackgroundInit, diskCacheConfig.gJ());
    }

    public h a(b diskCacheConfig) {
        return a(diskCacheConfig, this.Fe.b(diskCacheConfig));
    }
}
