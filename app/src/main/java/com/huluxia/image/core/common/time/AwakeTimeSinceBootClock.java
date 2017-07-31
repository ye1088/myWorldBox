package com.huluxia.image.core.common.time;

import android.os.SystemClock;
import com.huluxia.framework.base.utils.DoNotStrip;

@DoNotStrip
public class AwakeTimeSinceBootClock implements c {
    @DoNotStrip
    private static final AwakeTimeSinceBootClock INSTANCE = new AwakeTimeSinceBootClock();

    private AwakeTimeSinceBootClock() {
    }

    @DoNotStrip
    public static AwakeTimeSinceBootClock get() {
        return INSTANCE;
    }

    @DoNotStrip
    public long now() {
        return SystemClock.uptimeMillis();
    }
}
