package com.MCWorld.framework.base.async;

import com.MCWorld.framework.base.log.HLog;
import java.util.concurrent.Callable;

class AsyncTaskCenter$SafeCallable<T> implements Callable {
    private Callable<T> mCallable;

    public AsyncTaskCenter$SafeCallable(Callable<T> callable) {
        this.mCallable = callable;
    }

    public T call() {
        try {
            if (this.mCallable != null) {
                return this.mCallable.call();
            }
        } catch (Throwable e) {
            HLog.error("AsyncTaskCenter", "safe runnable run ex" + e, new Object[0]);
        }
        return null;
    }
}
