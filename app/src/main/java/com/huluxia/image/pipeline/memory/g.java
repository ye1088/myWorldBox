package com.huluxia.image.pipeline.memory;

import android.util.SparseIntArray;

/* compiled from: DefaultByteArrayPoolParams */
public class g {
    private static final int HJ = 81920;
    private static final int HL = 16384;
    private static final int HM = 5;
    private static final int HN = 1048576;

    public static t nW() {
        SparseIntArray defaultBuckets = new SparseIntArray();
        defaultBuckets.put(16384, 5);
        return new t(81920, 1048576, defaultBuckets);
    }
}
