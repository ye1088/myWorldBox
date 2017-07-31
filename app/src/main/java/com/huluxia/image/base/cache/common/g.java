package com.huluxia.image.base.cache.common;

/* compiled from: NoOpCacheEventListener */
public class g implements CacheEventListener {
    private static g tB = null;

    private g() {
    }

    public static synchronized g gn() {
        g gVar;
        synchronized (g.class) {
            if (tB == null) {
                tB = new g();
            }
            gVar = tB;
        }
        return gVar;
    }

    public void a(a cacheEvent) {
    }

    public void b(a cacheEvent) {
    }

    public void c(a cacheEvent) {
    }

    public void d(a cacheEvent) {
    }

    public void e(a cacheEvent) {
    }

    public void f(a cacheEvent) {
    }

    public void g(a cacheEvent) {
    }

    public void gj() {
    }
}
