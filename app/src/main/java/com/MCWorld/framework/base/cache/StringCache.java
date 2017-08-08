package com.MCWorld.framework.base.cache;

import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsMD5;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;

public class StringCache {
    private Map<String, StringLruCache> mStringDiskLruCacheMap = new Hashtable();

    private static class Singleton {
        static final StringCache instance = new StringCache();

        private Singleton() {
        }
    }

    public static StringCache getInstance() {
        return Singleton.instance;
    }

    public StringLruCache getDiskCache(String dirName) {
        if (UtilsFunction.empty(dirName)) {
            throw new IllegalArgumentException("dir name should not be null");
        }
        String key = UtilsMD5.getMD5String(dirName);
        StringLruCache diskLruCache = (StringLruCache) this.mStringDiskLruCacheMap.get(key);
        if (diskLruCache != null) {
            return diskLruCache;
        }
        StringLruCache cache = new StringLruCache(0.05f, new File(AppConfig.getInstance().getRootDir(), "caches" + File.separator + dirName).getAbsolutePath());
        this.mStringDiskLruCacheMap.put(key, cache);
        return cache;
    }
}
