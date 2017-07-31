package com.tencent.smtt.sdk;

import android.os.HandlerThread;

class z extends HandlerThread {
    private static z a;

    public z(String str) {
        super(str);
    }

    public static synchronized z a() {
        z zVar;
        synchronized (z.class) {
            if (a == null) {
                a = new z("TbsHandlerThread");
                a.start();
            }
            zVar = a;
        }
        return zVar;
    }
}
