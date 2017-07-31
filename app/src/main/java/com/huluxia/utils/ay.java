package com.huluxia.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: UtilsThreadPool */
public class ay {
    private static ExecutorService bni = Executors.newCachedThreadPool();

    public static void execute(Runnable command) {
        bni.execute(command);
    }
}
