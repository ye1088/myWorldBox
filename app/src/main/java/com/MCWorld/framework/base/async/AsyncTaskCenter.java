package com.MCWorld.framework.base.async;

import com.MCWorld.framework.base.log.HLog;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class AsyncTaskCenter {
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    private static final String TAG = "AsyncTaskCenter";
    private static final int THREAD_POOL_CORE_COUNT = 3;
    private static final int THREAD_POOL_MAX_COUNT = 6;
    private Map<String, PriorityFutureRunnable> mRunnableCallbackMap;
    private ThreadPoolExecutor mThreadPoolExecutor;

    public interface RunnableCallback {
        void onCallback();
    }

    private AsyncTaskCenter() {
        this.mRunnableCallbackMap = new ConcurrentHashMap();
        this.mThreadPoolExecutor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new PriorityBlockingQueue(), new PriorityThreadFactory(this, 0), new DiscardPolicy());
    }

    public static AsyncTaskCenter getInstance() {
        return Singleton.center;
    }

    public void cancel(Runnable runnable) {
        if (runnable != null) {
            PriorityFutureRunnable priorityFutureRunnable = (PriorityFutureRunnable) this.mRunnableCallbackMap.remove(runnable.toString());
            HLog.debug(TAG, "cancel task " + priorityFutureRunnable, new Object[0]);
            if (priorityFutureRunnable != null) {
                priorityFutureRunnable.cancel();
            }
        }
    }

    public void cancel(Callable runnable) {
        if (runnable != null) {
            PriorityFutureRunnable priorityFutureRunnable = (PriorityFutureRunnable) this.mRunnableCallbackMap.remove(runnable.toString());
            if (priorityFutureRunnable != null) {
                priorityFutureRunnable.cancel();
            }
        }
    }

    public void execute(Runnable runnable) {
        execute(runnable, null);
    }

    public void execute(Runnable runnable, RunnableCallback callback) {
        execute(0, runnable, callback);
    }

    public void execute(int priority, Runnable runnable, RunnableCallback callback) {
        PriorityFutureRunnable futureRunnable = new PriorityFutureRunnable(priority, runnable, callback);
        this.mRunnableCallbackMap.put(runnable.toString(), futureRunnable);
        this.mThreadPoolExecutor.execute(futureRunnable);
    }

    public <T> void execute(Callable<T> runnable) {
        execute((Callable) runnable, null);
    }

    public <T> void execute(Callable<T> runnable, CallableCallback<T> callback) {
        execute(0, (Callable) runnable, (CallableCallback) callback);
    }

    public <T> void execute(int priority, Callable<T> runnable, CallableCallback<T> callback) {
        PriorityFutureRunnable<T> futureCallable = new PriorityFutureRunnable(priority, runnable, callback);
        this.mRunnableCallbackMap.put(runnable.toString(), futureCallable);
        this.mThreadPoolExecutor.execute(futureCallable);
    }

    public ExecutorService executeSingleThread(Runnable runnable) {
        return executeSingleThread(runnable, 0);
    }

    public ExecutorService executeSingleThread(Runnable runnable, int threadPriority) {
        if (threadPriority >= 19) {
            threadPriority = Math.min(threadPriority, 19);
        }
        if (threadPriority <= -19) {
            threadPriority = Math.max(threadPriority, -19);
        }
        ExecutorService executor = Executors.newSingleThreadExecutor(new PriorityThreadFactory(this, threadPriority));
        executor.execute(runnable);
        return executor;
    }

    public <T> ExecutorService executeSingleThread(Callable<T> runnable, CallableCallback<T> callback) {
        return executeSingleThread(runnable, callback, 0);
    }

    public <T> ExecutorService executeSingleThread(Callable<T> runnable, CallableCallback<T> callback, int threadPriority) {
        if (threadPriority >= 19) {
            threadPriority = Math.min(threadPriority, 19);
        }
        if (threadPriority <= -19) {
            threadPriority = Math.max(threadPriority, -19);
        }
        ExecutorService executor = Executors.newSingleThreadExecutor(new PriorityThreadFactory(this, threadPriority));
        executor.submit(new 1(this, runnable, callback));
        return executor;
    }
}
