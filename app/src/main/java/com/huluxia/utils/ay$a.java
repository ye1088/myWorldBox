package com.huluxia.utils;

import java.util.concurrent.ThreadFactory;

/* compiled from: UtilsThreadPool */
class ay$a implements ThreadFactory {
    ay$a() {
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setPriority(10);
        return t;
    }
}
