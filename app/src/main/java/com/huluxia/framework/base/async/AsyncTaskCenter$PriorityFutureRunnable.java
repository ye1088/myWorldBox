package com.huluxia.framework.base.async;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.huluxia.framework.base.async.AsyncTaskCenter.RunnableCallback;
import com.huluxia.framework.base.log.HLog;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

final class AsyncTaskCenter$PriorityFutureRunnable<T> extends FutureTask<T> implements Comparable<AsyncTaskCenter$PriorityFutureRunnable> {
    private Callable mCallable;
    private final AsyncTaskCenter$CallableCallback<T> mCallableCallback;
    private final RunnableCallback mCallback;
    private volatile boolean mCanceled = false;
    private final Handler mHandler;
    private final int mPriority;
    private Runnable mRunnable;

    public AsyncTaskCenter$PriorityFutureRunnable(int priority, Runnable task, RunnableCallback callback) {
        super(new AsyncTaskCenter$SafeRunnable(task), null);
        this.mRunnable = task;
        this.mPriority = priority;
        this.mCallback = callback;
        this.mCallableCallback = null;
        Thread currentThread = Thread.currentThread();
        if (currentThread == Looper.getMainLooper().getThread()) {
            this.mHandler = new Handler(Looper.getMainLooper());
        } else if (currentThread instanceof HandlerThread) {
            this.mHandler = new Handler(((HandlerThread) currentThread).getLooper());
        } else {
            this.mHandler = null;
        }
    }

    public AsyncTaskCenter$PriorityFutureRunnable(int priority, Callable<T> task, AsyncTaskCenter$CallableCallback<T> callback) {
        super(new AsyncTaskCenter$SafeCallable(task));
        this.mCallable = task;
        this.mPriority = priority;
        this.mCallableCallback = callback;
        this.mCallback = null;
        Thread currentThread = Thread.currentThread();
        if (currentThread == Looper.getMainLooper().getThread()) {
            this.mHandler = new Handler(Looper.getMainLooper());
        } else if (currentThread instanceof HandlerThread) {
            this.mHandler = new Handler(((HandlerThread) currentThread).getLooper());
        } else {
            this.mHandler = null;
        }
    }

    public void cancel() {
        this.mCanceled = true;
    }

    public int compareTo(AsyncTaskCenter$PriorityFutureRunnable other) {
        return this.mPriority - (other == null ? Integer.MIN_VALUE : other.mPriority);
    }

    protected void done() {
        super.done();
        if (this.mCallback != null) {
            if (this.mHandler != null) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        if (!AsyncTaskCenter$PriorityFutureRunnable.this.mCanceled) {
                            AsyncTaskCenter$PriorityFutureRunnable.this.mCallback.onCallback();
                        }
                        AsyncTaskCenter.getInstance().cancel(AsyncTaskCenter$PriorityFutureRunnable.this.mRunnable);
                        AsyncTaskCenter.getInstance().cancel(AsyncTaskCenter$PriorityFutureRunnable.this.mCallable);
                    }
                });
                return;
            }
            if (!this.mCanceled) {
                this.mCallback.onCallback();
            }
            AsyncTaskCenter.getInstance().cancel(this.mRunnable);
            AsyncTaskCenter.getInstance().cancel(this.mCallable);
        } else if (this.mCallableCallback != null) {
            T finalResult;
            T result = null;
            try {
                finalResult = get();
            } catch (InterruptedException e) {
                HLog.error(this, "priority callable get result interrupt error " + e, new Object[0]);
                finalResult = result;
            } catch (ExecutionException e2) {
                HLog.error(this, "priority callable get result execute error " + e2, new Object[0]);
                finalResult = result;
            }
            if (this.mCallableCallback == null) {
                return;
            }
            if (this.mHandler != null) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        if (!AsyncTaskCenter$PriorityFutureRunnable.this.mCanceled) {
                            AsyncTaskCenter$PriorityFutureRunnable.this.mCallableCallback.onCallback(finalResult);
                        }
                        AsyncTaskCenter.getInstance().cancel(AsyncTaskCenter$PriorityFutureRunnable.this.mRunnable);
                        AsyncTaskCenter.getInstance().cancel(AsyncTaskCenter$PriorityFutureRunnable.this.mCallable);
                    }
                });
                return;
            }
            if (!this.mCanceled) {
                this.mCallableCallback.onCallback(finalResult);
            }
            AsyncTaskCenter.getInstance().cancel(this.mRunnable);
            AsyncTaskCenter.getInstance().cancel(this.mCallable);
        }
    }
}
