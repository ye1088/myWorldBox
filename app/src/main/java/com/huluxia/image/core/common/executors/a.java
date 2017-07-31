package com.huluxia.image.core.common.executors;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/* compiled from: CallerThreadExecutor */
public class a extends AbstractExecutorService {
    private static final a xR = new a();

    public static a ik() {
        return xR;
    }

    private a() {
    }

    public void execute(Runnable command) {
        command.run();
    }

    public boolean isShutdown() {
        return false;
    }

    public void shutdown() {
    }

    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return true;
    }
}
