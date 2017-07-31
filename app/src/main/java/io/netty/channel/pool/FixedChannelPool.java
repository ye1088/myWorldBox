package io.netty.channel.pool;

import com.tencent.tauth.AuthActivity;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ThrowableUtil;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class FixedChannelPool extends SimpleChannelPool {
    static final /* synthetic */ boolean $assertionsDisabled = (!FixedChannelPool.class.desiredAssertionStatus());
    private static final IllegalStateException FULL_EXCEPTION = ((IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("Too many outstanding acquire operations"), FixedChannelPool.class, "acquire0(...)"));
    private static final TimeoutException TIMEOUT_EXCEPTION = ((TimeoutException) ThrowableUtil.unknownStackTrace(new TimeoutException("Acquire operation took longer then configured maximum time"), FixedChannelPool.class, "<init>(...)"));
    private final long acquireTimeoutNanos;
    private int acquiredChannelCount;
    private boolean closed;
    private final EventExecutor executor;
    private final int maxConnections;
    private final int maxPendingAcquires;
    private int pendingAcquireCount;
    private final Queue<AcquireTask> pendingAcquireQueue;
    private final Runnable timeoutTask;

    private abstract class TimeoutTask implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = (!FixedChannelPool.class.desiredAssertionStatus());

        public abstract void onTimeout(AcquireTask acquireTask);

        private TimeoutTask() {
        }

        public final void run() {
            if ($assertionsDisabled || FixedChannelPool.this.executor.inEventLoop()) {
                long nanoTime = System.nanoTime();
                while (true) {
                    AcquireTask task = (AcquireTask) FixedChannelPool.this.pendingAcquireQueue.peek();
                    if (task != null && nanoTime - task.expireNanoTime >= 0) {
                        FixedChannelPool.this.pendingAcquireQueue.remove();
                        FixedChannelPool.access$906(FixedChannelPool.this);
                        onTimeout(task);
                    } else {
                        return;
                    }
                }
            }
            throw new AssertionError();
        }
    }

    private class AcquireListener implements FutureListener<Channel> {
        static final /* synthetic */ boolean $assertionsDisabled = (!FixedChannelPool.class.desiredAssertionStatus());
        protected boolean acquired;
        private final Promise<Channel> originalPromise;

        AcquireListener(Promise<Channel> originalPromise) {
            this.originalPromise = originalPromise;
        }

        public void operationComplete(Future<Channel> future) throws Exception {
            if (!$assertionsDisabled && !FixedChannelPool.this.executor.inEventLoop()) {
                throw new AssertionError();
            } else if (FixedChannelPool.this.closed) {
                this.originalPromise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
            } else if (future.isSuccess()) {
                this.originalPromise.setSuccess(future.getNow());
            } else {
                if (this.acquired) {
                    FixedChannelPool.this.decrementAndRunTaskQueue();
                } else {
                    FixedChannelPool.this.runTaskQueue();
                }
                this.originalPromise.setFailure(future.cause());
            }
        }

        public void acquired() {
            if (!this.acquired) {
                FixedChannelPool.this.acquiredChannelCount = FixedChannelPool.this.acquiredChannelCount + 1;
                this.acquired = true;
            }
        }
    }

    private final class AcquireTask extends AcquireListener {
        final long expireNanoTime = (System.nanoTime() + FixedChannelPool.this.acquireTimeoutNanos);
        final Promise<Channel> promise;
        ScheduledFuture<?> timeoutFuture;

        public AcquireTask(Promise<Channel> promise) {
            super(promise);
            this.promise = FixedChannelPool.this.executor.newPromise().addListener(this);
        }
    }

    public enum AcquireTimeoutAction {
        NEW,
        FAIL
    }

    static /* synthetic */ int access$906(FixedChannelPool x0) {
        int i = x0.pendingAcquireCount - 1;
        x0.pendingAcquireCount = i;
        return i;
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections) {
        this(bootstrap, handler, maxConnections, Integer.MAX_VALUE);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections, int maxPendingAcquires) {
        this(bootstrap, handler, ChannelHealthChecker.ACTIVE, null, -1, maxConnections, maxPendingAcquires);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires) {
        this(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires, true);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires, boolean releaseHealthCheck) {
        super(bootstrap, handler, healthCheck, releaseHealthCheck);
        this.pendingAcquireQueue = new ArrayDeque();
        if (maxConnections < 1) {
            throw new IllegalArgumentException("maxConnections: " + maxConnections + " (expected: >= 1)");
        } else if (maxPendingAcquires < 1) {
            throw new IllegalArgumentException("maxPendingAcquires: " + maxPendingAcquires + " (expected: >= 1)");
        } else {
            if (action == null && acquireTimeoutMillis == -1) {
                this.timeoutTask = null;
                this.acquireTimeoutNanos = -1;
            } else if (action == null && acquireTimeoutMillis != -1) {
                throw new NullPointerException(AuthActivity.ACTION_KEY);
            } else if (action == null || acquireTimeoutMillis >= 0) {
                this.acquireTimeoutNanos = TimeUnit.MILLISECONDS.toNanos(acquireTimeoutMillis);
                switch (action) {
                    case FAIL:
                        this.timeoutTask = new TimeoutTask() {
                            public void onTimeout(AcquireTask task) {
                                task.promise.setFailure(FixedChannelPool.TIMEOUT_EXCEPTION);
                            }
                        };
                        break;
                    case NEW:
                        this.timeoutTask = new TimeoutTask() {
                            public void onTimeout(AcquireTask task) {
                                task.acquired();
                                super.acquire(task.promise);
                            }
                        };
                        break;
                    default:
                        throw new Error();
                }
            } else {
                throw new IllegalArgumentException("acquireTimeoutMillis: " + acquireTimeoutMillis + " (expected: >= 1)");
            }
            this.executor = bootstrap.config().group().next();
            this.maxConnections = maxConnections;
            this.maxPendingAcquires = maxPendingAcquires;
        }
    }

    public Future<Channel> acquire(final Promise<Channel> promise) {
        try {
            if (this.executor.inEventLoop()) {
                acquire0(promise);
            } else {
                this.executor.execute(new Runnable() {
                    public void run() {
                        FixedChannelPool.this.acquire0(promise);
                    }
                });
            }
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
        return promise;
    }

    private void acquire0(Promise<Channel> promise) {
        if (!$assertionsDisabled && !this.executor.inEventLoop()) {
            throw new AssertionError();
        } else if (this.closed) {
            promise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
        } else if (this.acquiredChannelCount >= this.maxConnections) {
            if (this.pendingAcquireCount >= this.maxPendingAcquires) {
                promise.setFailure(FULL_EXCEPTION);
            } else {
                AcquireTask task = new AcquireTask(promise);
                if (this.pendingAcquireQueue.offer(task)) {
                    this.pendingAcquireCount++;
                    if (this.timeoutTask != null) {
                        task.timeoutFuture = this.executor.schedule(this.timeoutTask, this.acquireTimeoutNanos, TimeUnit.NANOSECONDS);
                    }
                } else {
                    promise.setFailure(FULL_EXCEPTION);
                }
            }
            if (!$assertionsDisabled && this.pendingAcquireCount <= 0) {
                throw new AssertionError();
            }
        } else if ($assertionsDisabled || this.acquiredChannelCount >= 0) {
            Promise<Channel> p = this.executor.newPromise();
            AcquireListener l = new AcquireListener(promise);
            l.acquired();
            p.addListener(l);
            super.acquire(p);
        } else {
            throw new AssertionError();
        }
    }

    public Future<Void> release(Channel channel, final Promise<Void> promise) {
        Promise<Void> p = this.executor.newPromise();
        super.release(channel, p.addListener(new FutureListener<Void>() {
            static final /* synthetic */ boolean $assertionsDisabled = (!FixedChannelPool.class.desiredAssertionStatus());

            public void operationComplete(Future<Void> future) throws Exception {
                if (!$assertionsDisabled && !FixedChannelPool.this.executor.inEventLoop()) {
                    throw new AssertionError();
                } else if (FixedChannelPool.this.closed) {
                    promise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
                } else if (future.isSuccess()) {
                    FixedChannelPool.this.decrementAndRunTaskQueue();
                    promise.setSuccess(null);
                } else {
                    if (!(future.cause() instanceof IllegalArgumentException)) {
                        FixedChannelPool.this.decrementAndRunTaskQueue();
                    }
                    promise.setFailure(future.cause());
                }
            }
        }));
        return p;
    }

    private void decrementAndRunTaskQueue() {
        this.acquiredChannelCount--;
        if ($assertionsDisabled || this.acquiredChannelCount >= 0) {
            runTaskQueue();
            return;
        }
        throw new AssertionError();
    }

    private void runTaskQueue() {
        while (this.acquiredChannelCount < this.maxConnections) {
            AcquireTask task = (AcquireTask) this.pendingAcquireQueue.poll();
            if (task == null) {
                break;
            }
            ScheduledFuture<?> timeoutFuture = task.timeoutFuture;
            if (timeoutFuture != null) {
                timeoutFuture.cancel(false);
            }
            this.pendingAcquireCount--;
            task.acquired();
            super.acquire(task.promise);
        }
        if (!$assertionsDisabled && this.pendingAcquireCount < 0) {
            throw new AssertionError();
        } else if (!$assertionsDisabled && this.acquiredChannelCount < 0) {
            throw new AssertionError();
        }
    }

    public void close() {
        this.executor.execute(new Runnable() {
            public void run() {
                if (!FixedChannelPool.this.closed) {
                    FixedChannelPool.this.closed = true;
                    while (true) {
                        AcquireTask task = (AcquireTask) FixedChannelPool.this.pendingAcquireQueue.poll();
                        if (task == null) {
                            FixedChannelPool.this.acquiredChannelCount = 0;
                            FixedChannelPool.this.pendingAcquireCount = 0;
                            super.close();
                            return;
                        }
                        ScheduledFuture<?> f = task.timeoutFuture;
                        if (f != null) {
                            f.cancel(false);
                        }
                        task.promise.setFailure(new ClosedChannelException());
                    }
                }
            }
        });
    }
}
