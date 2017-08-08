package com.MCWorld.image.pipeline.cache;

import android.app.ActivityManager;
import android.os.Build.VERSION;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.imagepipeline.cache.f;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacpp.swscale;

/* compiled from: DefaultBitmapMemoryCacheParamsSupplier */
public class e implements Supplier<f> {
    private static final int EM = 256;
    private static final int EN = Integer.MAX_VALUE;
    private static final int EO = Integer.MAX_VALUE;
    private static final int EP = Integer.MAX_VALUE;
    private final ActivityManager ER;

    public /* synthetic */ Object get() {
        return cc();
    }

    public e(ActivityManager activityManager) {
        this.ER = activityManager;
    }

    public f cc() {
        return new f(lw(), 256, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private int lw() {
        int maxMemory = Math.min(this.ER.getMemoryClass() * 1048576, Integer.MAX_VALUE);
        if (maxMemory < swscale.SWS_CPU_CAPS_SSE2) {
            return 4194304;
        }
        if (maxMemory < avformat.AVFMT_SEEK_TO_PTS) {
            return 6291456;
        }
        if (VERSION.SDK_INT < 11) {
            return 8388608;
        }
        return maxMemory / 4;
    }
}
