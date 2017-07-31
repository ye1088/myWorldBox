package com.huluxia.image.pipeline.memory;

/* compiled from: NoOpBitmapCounterTracker */
public class o implements c {
    private static c Ia = null;

    private o() {
    }

    public static synchronized c ob() {
        c cVar;
        synchronized (o.class) {
            if (Ia == null) {
                Ia = new o();
            }
            cVar = Ia;
        }
        return cVar;
    }

    public void a(a counter) {
    }

    public void b(a counter) {
    }
}
