package com.huluxia.image.base.imagepipeline.core;

import android.os.Process;
import java.util.concurrent.ThreadFactory;

/* compiled from: PriorityThreadFactory */
public class c implements ThreadFactory {
    private final int mThreadPriority;

    public c(int threadPriority) {
        this.mThreadPriority = threadPriority;
    }

    public Thread newThread(final Runnable runnable) {
        return new Thread(new Runnable(this) {
            final /* synthetic */ c wK;

            public void run() {
                try {
                    Process.setThreadPriority(this.wK.mThreadPriority);
                } catch (Throwable th) {
                }
                runnable.run();
            }
        });
    }
}
