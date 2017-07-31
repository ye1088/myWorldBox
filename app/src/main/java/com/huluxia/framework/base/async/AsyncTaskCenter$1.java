package com.huluxia.framework.base.async;

import java.util.concurrent.Callable;

class AsyncTaskCenter$1 implements Callable<T> {
    final /* synthetic */ AsyncTaskCenter this$0;
    final /* synthetic */ AsyncTaskCenter$CallableCallback val$callback;
    final /* synthetic */ Callable val$runnable;

    AsyncTaskCenter$1(AsyncTaskCenter this$0, Callable callable, AsyncTaskCenter$CallableCallback asyncTaskCenter$CallableCallback) {
        this.this$0 = this$0;
        this.val$runnable = callable;
        this.val$callback = asyncTaskCenter$CallableCallback;
    }

    public T call() throws Exception {
        T result = this.val$runnable.call();
        this.val$callback.onCallback(result);
        return result;
    }
}
