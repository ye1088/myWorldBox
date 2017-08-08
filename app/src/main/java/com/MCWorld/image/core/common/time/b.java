package com.MCWorld.image.core.common.time;

import android.os.SystemClock;

/* compiled from: CurrentThreadTimeClock */
public class b implements a {
    public long now() {
        return SystemClock.currentThreadTimeMillis();
    }
}
