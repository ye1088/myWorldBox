package com.MCWorld.image.base.imagepipeline.cache;

import android.os.Build.VERSION;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.image.core.common.memory.MemoryTrimType;

/* compiled from: BitmapMemoryCacheTrimStrategy */
public class b implements d$a {
    private static final String TAG = "BitmapMemoryCacheTrimStrategy";

    public double a(MemoryTrimType trimType) {
        switch (trimType) {
            case OnCloseToDalvikHeapLimit:
                if (VERSION.SDK_INT >= 21) {
                    return MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio();
                }
                return 0.0d;
            case OnAppBackgrounded:
            case OnSystemLowMemoryWhileAppInForeground:
            case OnSystemLowMemoryWhileAppInBackground:
                return 1.0d;
            default:
                HLog.warn(TAG, "unknown trim type: %s", new Object[]{trimType});
                return 0.0d;
        }
    }
}
