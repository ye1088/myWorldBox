package com.MCWorld.image.pipeline.cache;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.image.base.imagepipeline.cache.d$a;
import com.MCWorld.image.core.common.memory.MemoryTrimType;

/* compiled from: NativeMemoryCacheTrimStrategy */
public class n implements d$a {
    private static final String TAG = "NativeMemoryCacheTrimStrategy";

    public double a(MemoryTrimType trimType) {
        switch (trimType) {
            case OnCloseToDalvikHeapLimit:
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
