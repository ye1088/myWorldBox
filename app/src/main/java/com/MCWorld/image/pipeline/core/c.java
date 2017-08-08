package com.MCWorld.image.pipeline.core;

import com.MCWorld.image.base.cache.disk.b;
import com.MCWorld.image.base.cache.disk.e;

/* compiled from: DynamicDefaultDiskStorageFactory */
public class c implements b {
    public com.MCWorld.image.base.cache.disk.c b(b diskCacheConfig) {
        return new e(diskCacheConfig.getVersion(), diskCacheConfig.gB(), diskCacheConfig.gA(), diskCacheConfig.gG());
    }
}
