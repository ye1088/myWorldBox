package com.MCWorld.image.pipeline.memory;

import android.util.SparseIntArray;

/* compiled from: DefaultBitmapPoolParams */
public class f {
    private static final int HJ = 0;
    private static final SparseIntArray HK = new SparseIntArray(0);

    private f() {
    }

    private static int nR() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        if (maxMemory > 16777216) {
            return (maxMemory / 4) * 3;
        }
        return maxMemory / 2;
    }

    public static t nW() {
        return new t(0, nR(), HK);
    }
}
