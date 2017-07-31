package com.huluxia.image.pipeline.core;

import com.huluxia.image.base.cache.disk.b;
import com.huluxia.image.base.cache.disk.e;

/* compiled from: DynamicDefaultDiskStorageFactory */
public class c implements b {
    public com.huluxia.image.base.cache.disk.c b(b diskCacheConfig) {
        return new e(diskCacheConfig.getVersion(), diskCacheConfig.gB(), diskCacheConfig.gA(), diskCacheConfig.gG());
    }
}
