package com.huluxia.framework.base.http.datasource.cache;

import com.huluxia.framework.base.http.datasource.cache.Cache.Entry;

public class NoCache implements Cache {
    public void clear() {
    }

    public Entry get(String key) {
        return null;
    }

    public void put(String key, Entry entry) {
    }

    public void invalidate(String key, boolean fullExpire) {
    }

    public void remove(String key) {
    }

    public void initialize() {
    }
}
