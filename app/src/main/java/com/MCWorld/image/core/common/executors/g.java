package com.MCWorld.image.core.common.executors;

import android.os.Handler;
import android.os.Looper;

/* compiled from: UiThreadImmediateExecutorService */
public class g extends e {
    private static g yc = null;

    private g() {
        super(new Handler(Looper.getMainLooper()));
    }

    public static g ir() {
        if (yc == null) {
            yc = new g();
        }
        return yc;
    }

    public void execute(Runnable command) {
        if (ip()) {
            command.run();
        } else {
            super.execute(command);
        }
    }
}
