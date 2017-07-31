package com.huluxia.image.core.common.executors;

import android.os.Handler;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/* compiled from: HandlerExecutorServiceImpl */
public class e extends AbstractExecutorService implements d {
    private final Handler mHandler;

    protected /* synthetic */ RunnableFuture newTaskFor(Runnable runnable, Object obj) {
        return a(runnable, obj);
    }

    protected /* synthetic */ RunnableFuture newTaskFor(Callable callable) {
        return c(callable);
    }

    public /* synthetic */ Future submit(Runnable runnable) {
        return d(runnable);
    }

    public /* synthetic */ Future submit(Runnable runnable, @Nullable Object obj) {
        return b(runnable, obj);
    }

    public /* synthetic */ Future submit(Callable callable) {
        return d(callable);
    }

    public e(Handler handler) {
        this.mHandler = handler;
    }

    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public void execute(Runnable command) {
        this.mHandler.post(command);
    }

    protected <T> ScheduledFutureImpl<T> a(Runnable runnable, T value) {
        return new ScheduledFutureImpl(this.mHandler, runnable, value);
    }

    protected <T> ScheduledFutureImpl<T> c(Callable<T> callable) {
        return new ScheduledFutureImpl(this.mHandler, callable);
    }

    public ScheduledFuture<?> d(Runnable task) {
        return b(task, (Void) null);
    }

    public <T> ScheduledFuture<T> b(Runnable task, @Nullable T result) {
        if (task == null) {
            throw new NullPointerException();
        }
        ScheduledFutureImpl<T> future = a(task, result);
        execute(future);
        return future;
    }

    public <T> ScheduledFuture<T> d(Callable<T> task) {
        if (task == null) {
            throw new NullPointerException();
        }
        ScheduledFutureImpl<T> future = c(task);
        execute(future);
        return future;
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        ScheduledFutureImpl<?> future = a(command, null);
        this.mHandler.postDelayed(future, unit.toMillis(delay));
        return future;
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        ScheduledFutureImpl<V> future = c(callable);
        this.mHandler.postDelayed(future, unit.toMillis(delay));
        return future;
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    public void quit() {
        this.mHandler.getLooper().quit();
    }

    public boolean ip() {
        return Thread.currentThread() == this.mHandler.getLooper().getThread();
    }
}
