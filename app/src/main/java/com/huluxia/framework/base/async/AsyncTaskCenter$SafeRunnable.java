package com.huluxia.framework.base.async;

import com.huluxia.framework.base.log.HLog;

class AsyncTaskCenter$SafeRunnable implements Runnable {
    private Runnable mRunnable;

    public AsyncTaskCenter$SafeRunnable(Runnable runnable) {
        this.mRunnable = runnable;
    }

    public void run() {
        try {
            if (this.mRunnable != null) {
                this.mRunnable.run();
            }
        } catch (Throwable e) {
            HLog.error("AsyncTaskCenter", "safe runnable run ex" + e, new Object[0]);
        }
    }
}
