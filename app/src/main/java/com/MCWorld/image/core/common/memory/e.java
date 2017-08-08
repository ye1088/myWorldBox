package com.MCWorld.image.core.common.memory;

/* compiled from: NoOpMemoryTrimmableRegistry */
public class e implements b {
    private static e yf = null;

    public static synchronized e it() {
        e eVar;
        synchronized (e.class) {
            if (yf == null) {
                yf = new e();
            }
            eVar = yf;
        }
        return eVar;
    }

    public void a(a trimmable) {
    }

    public void b(a trimmable) {
    }
}
