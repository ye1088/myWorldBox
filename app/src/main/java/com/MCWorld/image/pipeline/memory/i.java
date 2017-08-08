package com.MCWorld.image.pipeline.memory;

import android.util.SparseIntArray;
import org.bytedeco.javacpp.swscale;

/* compiled from: DefaultNativeMemoryChunkPoolParams */
public class i {
    private static final int HR = 5;
    private static final int HS = 2;

    public static t nW() {
        SparseIntArray DEFAULT_BUCKETS = new SparseIntArray();
        DEFAULT_BUCKETS.put(1024, 5);
        DEFAULT_BUCKETS.put(2048, 5);
        DEFAULT_BUCKETS.put(4096, 5);
        DEFAULT_BUCKETS.put(8192, 5);
        DEFAULT_BUCKETS.put(16384, 5);
        DEFAULT_BUCKETS.put(32768, 5);
        DEFAULT_BUCKETS.put(65536, 5);
        DEFAULT_BUCKETS.put(131072, 5);
        DEFAULT_BUCKETS.put(262144, 2);
        DEFAULT_BUCKETS.put(524288, 2);
        DEFAULT_BUCKETS.put(1048576, 2);
        return new t(nX(), nR(), DEFAULT_BUCKETS);
    }

    private static int nX() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        if (maxMemory < 16777216) {
            return 3145728;
        }
        if (maxMemory < swscale.SWS_CPU_CAPS_SSE2) {
            return 6291456;
        }
        return 12582912;
    }

    private static int nR() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        if (maxMemory < 16777216) {
            return maxMemory / 2;
        }
        return (maxMemory / 4) * 3;
    }
}
