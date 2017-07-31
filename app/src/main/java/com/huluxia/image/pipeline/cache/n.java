package com.huluxia.image.pipeline.cache;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.image.base.imagepipeline.cache.d$a;
import com.huluxia.image.core.common.memory.MemoryTrimType;

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
