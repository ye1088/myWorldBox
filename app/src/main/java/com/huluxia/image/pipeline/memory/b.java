package com.huluxia.image.pipeline.memory;

import javax.annotation.Nullable;

/* compiled from: BitmapCounterProvider */
public class b {
    private static final long HA = 1024;
    private static final long HB = 1048576;
    public static final int HC = nR();
    public static final int HD = 384;
    private static a HE;

    private static int nR() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        if (((long) maxMemory) > 16777216) {
            return (maxMemory / 4) * 3;
        }
        return maxMemory / 2;
    }

    public static a a(@Nullable c tracker) {
        if (HE == null) {
            HE = new a(HD, HC, tracker);
        }
        return HE;
    }
}
