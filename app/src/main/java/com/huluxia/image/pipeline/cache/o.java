package com.huluxia.image.pipeline.cache;

import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.imagepipeline.cache.d;

/* compiled from: NoOpImageCacheStatsTracker */
public class o implements k {
    private static o EV = null;

    private o() {
    }

    public static synchronized o lG() {
        o oVar;
        synchronized (o.class) {
            if (EV == null) {
                EV = new o();
            }
            oVar = EV;
        }
        return oVar;
    }

    public void ly() {
    }

    public void s(b cacheKey) {
    }

    public void lz() {
    }

    public void lA() {
    }

    public void t(b cacheKey) {
    }

    public void lB() {
    }

    public void u(b cacheKey) {
    }

    public void lC() {
    }

    public void lD() {
    }

    public void lE() {
    }

    public void lF() {
    }

    public void a(d<?, ?> dVar) {
    }

    public void b(d<?, ?> dVar) {
    }
}
