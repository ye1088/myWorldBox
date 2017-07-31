package com.huluxia.framework.base.async;

import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.ThreadFactory;

public class AsyncTaskCenter$PriorityThreadFactory implements ThreadFactory {
    private final AsyncTaskCenter$ThreadNameGenerator mNameGenerator;
    private final int mThreadPriority;
    final /* synthetic */ AsyncTaskCenter this$0;

    public AsyncTaskCenter$PriorityThreadFactory(AsyncTaskCenter this$0, int threadPriority) {
        this(this$0, threadPriority, null);
    }

    public AsyncTaskCenter$PriorityThreadFactory(AsyncTaskCenter this$0, int threadPriority, AsyncTaskCenter$ThreadNameGenerator generator) {
        this.this$0 = this$0;
        this.mThreadPriority = threadPriority;
        this.mNameGenerator = generator;
    }

    public Thread newThread(final Runnable runnable) {
        String generateName;
        Runnable wrapperRunnable = new Runnable() {
            public void run() {
                try {
                    Process.setThreadPriority(AsyncTaskCenter$PriorityThreadFactory.this.mThreadPriority);
                } catch (Throwable th) {
                }
                runnable.run();
            }
        };
        if (this.mNameGenerator != null) {
            generateName = this.mNameGenerator.generateName();
        } else {
            generateName = String.format("asyn-task-%d", new Object[]{Long.valueOf(SystemClock.elapsedRealtime())});
        }
        return new Thread(wrapperRunnable, generateName);
    }
}
