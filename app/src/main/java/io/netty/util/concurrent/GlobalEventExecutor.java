package io.netty.util.concurrent;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class GlobalEventExecutor extends AbstractScheduledEventExecutor {
    public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
    private static final long SCHEDULE_QUIET_PERIOD_INTERVAL = TimeUnit.SECONDS.toNanos(1);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
    final ScheduledFutureTask<Void> quietPeriodTask = new ScheduledFutureTask(this, Executors.callable(new 1(this), null), ScheduledFutureTask.deadlineNanos(SCHEDULE_QUIET_PERIOD_INTERVAL), -SCHEDULE_QUIET_PERIOD_INTERVAL);
    private final AtomicBoolean started = new AtomicBoolean();
    final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue();
    private final TaskRunner taskRunner = new TaskRunner(this);
    private final Future<?> terminationFuture = new FailedFuture(this, new UnsupportedOperationException());
    volatile Thread thread;
    final ThreadFactory threadFactory = new DefaultThreadFactory(DefaultThreadFactory.toPoolName(getClass()), false, 5, null);

    private GlobalEventExecutor() {
        scheduledTaskQueue().add(this.quietPeriodTask);
    }

    Runnable takeTask() {
        Runnable task;
        BlockingQueue<Runnable> taskQueue = this.taskQueue;
        do {
            ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
            if (scheduledTask == null) {
                task = null;
                try {
                    return (Runnable) taskQueue.take();
                } catch (InterruptedException e) {
                    return task;
                }
            }
            long delayNanos = scheduledTask.delayNanos();
            if (delayNanos > 0) {
                try {
                    task = (Runnable) taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e2) {
                    return null;
                }
            }
            task = (Runnable) taskQueue.poll();
            if (task == null) {
                fetchFromScheduledTaskQueue();
                task = (Runnable) taskQueue.poll();
                continue;
            }
        } while (task == null);
        return task;
    }

    private void fetchFromScheduledTaskQueue() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        Runnable scheduledTask = pollScheduledTask(nanoTime);
        while (scheduledTask != null) {
            this.taskQueue.add(scheduledTask);
            scheduledTask = pollScheduledTask(nanoTime);
        }
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    private void addTask(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        this.taskQueue.add(task);
    }

    public boolean inEventLoop(Thread thread) {
        return thread == this.thread;
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public boolean isShuttingDown() {
        return false;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return false;
    }

    public boolean awaitInactivity(long timeout, TimeUnit unit) throws InterruptedException {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        Thread thread = this.thread;
        if (thread == null) {
            throw new IllegalStateException("thread was not started");
        }
        thread.join(unit.toMillis(timeout));
        return !thread.isAlive();
    }

    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        addTask(task);
        if (!inEventLoop()) {
            startThread();
        }
    }

    private void startThread() {
        if (this.started.compareAndSet(false, true)) {
            Thread t = this.threadFactory.newThread(this.taskRunner);
            this.thread = t;
            t.start();
        }
    }
}
