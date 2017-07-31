package com.huluxia.image.core.common.executors;

import com.huluxia.framework.base.log.HLog;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: ConstrainedExecutorService */
public class b extends AbstractExecutorService {
    private static final Class<?> tF = b.class;
    private final Executor mExecutor;
    private final String mName;
    private volatile int xS;
    private final BlockingQueue<Runnable> xT;
    private final a xU;
    private final AtomicInteger xV;
    private final AtomicInteger xW;

    /* compiled from: ConstrainedExecutorService */
    private class a implements Runnable {
        final /* synthetic */ b xX;

        private a(b bVar) {
            this.xX = bVar;
        }

        public void run() {
            int workers;
            try {
                Runnable runnable = (Runnable) this.xX.xT.poll();
                if (runnable != null) {
                    runnable.run();
                } else {
                    HLog.verbose(b.tF, "%s: Worker has nothing to run", new Object[]{this.xX.mName});
                }
                workers = this.xX.xV.decrementAndGet();
                if (this.xX.xT.isEmpty()) {
                    HLog.verbose(b.tF, "%s: worker finished; %d workers left", new Object[]{this.xX.mName, Integer.valueOf(workers)});
                    return;
                }
                this.xX.in();
            } catch (Throwable th) {
                workers = this.xX.xV.decrementAndGet();
                if (this.xX.xT.isEmpty()) {
                    HLog.verbose(b.tF, "%s: worker finished; %d workers left", new Object[]{this.xX.mName, Integer.valueOf(workers)});
                } else {
                    this.xX.in();
                }
            }
        }
    }

    public b(String name, int maxConcurrency, Executor executor, BlockingQueue<Runnable> workQueue) {
        if (maxConcurrency <= 0) {
            throw new IllegalArgumentException("max concurrency must be > 0");
        }
        this.mName = name;
        this.mExecutor = executor;
        this.xS = maxConcurrency;
        this.xT = workQueue;
        this.xU = new a();
        this.xV = new AtomicInteger(0);
        this.xW = new AtomicInteger(0);
    }

    public static b a(String name, int maxConcurrency, int queueSize, Executor executor) {
        return new b(name, maxConcurrency, executor, new LinkedBlockingQueue(queueSize));
    }

    public boolean il() {
        return this.xT.isEmpty() && this.xV.get() == 0;
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable parameter is null");
        } else if (this.xT.offer(runnable)) {
            int queueSize = this.xT.size();
            int maxSize = this.xW.get();
            if (queueSize > maxSize && this.xW.compareAndSet(maxSize, queueSize)) {
                HLog.verbose(tF, "%s: max pending work in queue = %d", new Object[]{this.mName, Integer.valueOf(queueSize)});
            }
            in();
        } else {
            throw new RejectedExecutionException(this.mName + " queue is full, size=" + this.xT.size());
        }
    }

    private void in() {
        int currentCount = this.xV.get();
        while (currentCount < this.xS) {
            if (this.xV.compareAndSet(currentCount, currentCount + 1)) {
                HLog.verbose(tF, "%s: starting worker %d of %d", new Object[]{this.mName, Integer.valueOf(currentCount + 1), Integer.valueOf(this.xS)});
                this.mExecutor.execute(this.xU);
                return;
            }
            HLog.verbose(tF, "%s: race in startWorkerIfNeeded; retrying", new Object[]{this.mName});
            currentCount = this.xV.get();
        }
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
}
