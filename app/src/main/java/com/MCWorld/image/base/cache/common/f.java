package com.MCWorld.image.base.cache.common;

import javax.annotation.Nullable;

/* compiled from: NoOpCacheErrorLogger */
public class f implements CacheErrorLogger {
    private static f tA = null;

    private f() {
    }

    public static synchronized f gm() {
        f fVar;
        synchronized (f.class) {
            if (tA == null) {
                tA = new f();
            }
            fVar = tA;
        }
        return fVar;
    }

    public void a(CacheErrorCategory category, Class<?> cls, String message, @Nullable Throwable throwable) {
    }
}
