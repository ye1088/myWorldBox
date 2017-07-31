package com.huluxia.image.core.common.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: DefaultSerialExecutorService */
public class c extends b implements f {
    public c(Executor executor) {
        super("SerialExecutor", 1, executor, new LinkedBlockingQueue());
    }

    public synchronized void execute(Runnable runnable) {
        super.execute(runnable);
    }
}
