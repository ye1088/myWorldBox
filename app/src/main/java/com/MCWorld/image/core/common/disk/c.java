package com.MCWorld.image.core.common.disk;

/* compiled from: NoOpDiskTrimmableRegistry */
public class c implements b {
    private static c xQ = null;

    private c() {
    }

    public static synchronized c ij() {
        c cVar;
        synchronized (c.class) {
            if (xQ == null) {
                xQ = new c();
            }
            cVar = xQ;
        }
        return cVar;
    }

    public void a(a trimmable) {
    }

    public void b(a trimmable) {
    }
}
