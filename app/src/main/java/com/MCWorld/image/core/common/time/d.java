package com.MCWorld.image.core.common.time;

/* compiled from: SystemClock */
public class d implements a {
    private static final d yJ = new d();

    private d() {
    }

    public static d iK() {
        return yJ;
    }

    public long now() {
        return System.currentTimeMillis();
    }
}
