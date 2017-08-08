package com.MCWorld.image.pipeline.memory;

import android.util.SparseIntArray;

/* compiled from: DefaultFlexByteArrayPoolParams */
public class h {
    public static final int HO = 4194304;
    private static final int HP = 131072;
    public static final int HQ = Runtime.getRuntime().availableProcessors();

    private h() {
    }

    public static SparseIntArray d(int min, int max, int numThreads) {
        SparseIntArray buckets = new SparseIntArray();
        for (int i = min; i <= max; i *= 2) {
            buckets.put(i, numThreads);
        }
        return buckets;
    }

    public static t nW() {
        return new t(4194304, HQ * 4194304, d(131072, 4194304, HQ), 131072, 4194304, HQ);
    }
}
