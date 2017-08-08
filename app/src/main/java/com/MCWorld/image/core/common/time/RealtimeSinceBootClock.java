package com.MCWorld.image.core.common.time;

import android.os.SystemClock;
import com.MCWorld.framework.base.utils.DoNotStrip;

@DoNotStrip
public class RealtimeSinceBootClock implements c {
    private static final RealtimeSinceBootClock yI = new RealtimeSinceBootClock();

    private RealtimeSinceBootClock() {
    }

    @DoNotStrip
    public static RealtimeSinceBootClock get() {
        return yI;
    }

    public long now() {
        return SystemClock.elapsedRealtime();
    }
}
