package com.MCWorld.image.pipeline.cache;

import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.imagepipeline.cache.f;
import org.bytedeco.javacpp.swscale;

/* compiled from: DefaultEncodedMemoryCacheParamsSupplier */
public class g implements Supplier<f> {
    private static final int EM = Integer.MAX_VALUE;
    private static final int EO = Integer.MAX_VALUE;

    public /* synthetic */ Object get() {
        return cc();
    }

    public f cc() {
        int maxCacheSize = lw();
        return new f(maxCacheSize, Integer.MAX_VALUE, maxCacheSize, Integer.MAX_VALUE, maxCacheSize / 8);
    }

    private int lw() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        if (maxMemory < 16777216) {
            return 1048576;
        }
        if (maxMemory < swscale.SWS_CPU_CAPS_SSE2) {
            return 2097152;
        }
        return 4194304;
    }
}
