package io.netty.util.concurrent;

import io.netty.util.concurrent.EventExecutorChooserFactory.EventExecutorChooser;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup {
    private final EventExecutor[] children;
    private final EventExecutorChooser chooser;
    private final Set<EventExecutor> readonlyChildren;
    private final AtomicInteger terminatedChildren;
    private final Promise<?> terminationFuture;

    protected abstract EventExecutor newChild(Executor executor, Object... objArr) throws Exception;

    protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
        this(nThreads, threadFactory == null ? null : new ThreadPerTaskExecutor(threadFactory), args);
    }

    protected MultithreadEventExecutorGroup(int nThreads, Executor executor, Object... args) {
        this(nThreads, executor, DefaultEventExecutorChooserFactory.INSTANCE, args);
    }

    protected MultithreadEventExecutorGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, Object... args) {
        int j;
        this.terminatedChildren = new AtomicInteger();
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        if (nThreads <= 0) {
            throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", new Object[]{Integer.valueOf(nThreads)}));
        }
        if (executor == null) {
            Executor threadPerTaskExecutor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
        }
        this.children = new EventExecutor[nThreads];
        int i = 0;
        while (i < nThreads) {
            EventExecutor e;
            try {
                this.children[i] = newChild(executor, args);
                if (!true) {
                    for (j = 0; j < i; j++) {
                        this.children[j].shutdownGracefully();
                    }
                    for (j = 0; j < i; j++) {
                        e = this.children[j];
                        while (!e.isTerminated()) {
                            try {
                                e.awaitTermination(2147483647L, TimeUnit.SECONDS);
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
                i++;
            } catch (Exception e3) {
                throw new IllegalStateException("failed to create a child event loop", e3);
            } catch (Throwable th) {
                if (!false) {
                    for (j = 0; j < i; j++) {
                        this.children[j].shutdownGracefully();
                    }
                    for (j = 0; j < i; j++) {
                        e = this.children[j];
                        while (!e.isTerminated()) {
                            try {
                                e.awaitTermination(2147483647L, TimeUnit.SECONDS);
                            } catch (InterruptedException e4) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
            }
        }
        this.chooser = chooserFactory.newChooser(this.children);
        FutureListener<Object> terminationListener = new 1(this);
        for (EventExecutor e5 : this.children) {
            e5.terminationFuture().addListener(terminationListener);
        }
        Set<EventExecutor> childrenSet = new LinkedHashSet(this.children.length);
        Collections.addAll(childrenSet, this.children);
        this.readonlyChildren = Collections.unmodifiableSet(childrenSet);
    }

    protected ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(getClass());
    }

    public EventExecutor next() {
        return this.chooser.next();
    }

    public Iterator<EventExecutor> iterator() {
        return this.readonlyChildren.iterator();
    }

    public final int executorCount() {
        return this.children.length;
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        for (EventExecutor l : this.children) {
            l.shutdownGracefully(quietPeriod, timeout, unit);
        }
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        for (EventExecutor l : this.children) {
            l.shutdown();
        }
    }

    public boolean isShuttingDown() {
        for (EventExecutor l : this.children) {
            if (!l.isShuttingDown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isShutdown() {
        for (EventExecutor l : this.children) {
            if (!l.isShutdown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTerminated() {
        for (EventExecutor l : this.children) {
            if (!l.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        loop0:
        for (EventExecutor l : this.children) {
            long timeLeft;
            do {
                timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0) {
                    break loop0;
                }
            } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        return isTerminated();
    }
}
