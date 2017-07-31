package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractScheduledEventExecutor extends AbstractEventExecutor {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractScheduledEventExecutor.class.desiredAssertionStatus());
    Queue<ScheduledFutureTask<?>> scheduledTaskQueue;

    protected AbstractScheduledEventExecutor() {
    }

    protected AbstractScheduledEventExecutor(EventExecutorGroup parent) {
        super(parent);
    }

    protected static long nanoTime() {
        return ScheduledFutureTask.nanoTime();
    }

    Queue<ScheduledFutureTask<?>> scheduledTaskQueue() {
        if (this.scheduledTaskQueue == null) {
            this.scheduledTaskQueue = new PriorityQueue();
        }
        return this.scheduledTaskQueue;
    }

    private static boolean isNullOrEmpty(Queue<ScheduledFutureTask<?>> queue) {
        return queue == null || queue.isEmpty();
    }

    protected void cancelScheduledTasks() {
        if ($assertionsDisabled || inEventLoop()) {
            Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
            if (!isNullOrEmpty(scheduledTaskQueue)) {
                for (ScheduledFutureTask<?> task : (ScheduledFutureTask[]) scheduledTaskQueue.toArray(new ScheduledFutureTask[scheduledTaskQueue.size()])) {
                    task.cancelWithoutRemove(false);
                }
                scheduledTaskQueue.clear();
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    protected final Runnable pollScheduledTask() {
        return pollScheduledTask(nanoTime());
    }

    protected final Runnable pollScheduledTask(long nanoTime) {
        if ($assertionsDisabled || inEventLoop()) {
            Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
            ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask) scheduledTaskQueue.peek();
            if (scheduledTask == null) {
                return null;
            }
            if (scheduledTask.deadlineNanos() > nanoTime) {
                return null;
            }
            scheduledTaskQueue.remove();
            return scheduledTask;
        }
        throw new AssertionError();
    }

    protected final long nextScheduledTaskNano() {
        Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
        ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask) scheduledTaskQueue.peek();
        if (scheduledTask == null) {
            return -1;
        }
        return Math.max(0, scheduledTask.deadlineNanos() - nanoTime());
    }

    final ScheduledFutureTask<?> peekScheduledTask() {
        Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
        if (scheduledTaskQueue == null) {
            return null;
        }
        return (ScheduledFutureTask) scheduledTaskQueue.peek();
    }

    protected final boolean hasScheduledTasks() {
        Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
        ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask) scheduledTaskQueue.peek();
        return scheduledTask != null && scheduledTask.deadlineNanos() <= nanoTime();
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        ObjectUtil.checkNotNull(command, "command");
        ObjectUtil.checkNotNull(unit, "unit");
        if (delay < 0) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[]{Long.valueOf(delay)}));
        }
        return schedule(new ScheduledFutureTask(this, command, null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        ObjectUtil.checkNotNull(callable, "callable");
        ObjectUtil.checkNotNull(unit, "unit");
        if (delay >= 0) {
            return schedule(new ScheduledFutureTask(this, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
        }
        throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[]{Long.valueOf(delay)}));
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        ObjectUtil.checkNotNull(command, "command");
        ObjectUtil.checkNotNull(unit, "unit");
        if (initialDelay < 0) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[]{Long.valueOf(initialDelay)}));
        } else if (period > 0) {
            return schedule(new ScheduledFutureTask(this, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period)));
        } else {
            throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[]{Long.valueOf(period)}));
        }
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        ObjectUtil.checkNotNull(command, "command");
        ObjectUtil.checkNotNull(unit, "unit");
        if (initialDelay < 0) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[]{Long.valueOf(initialDelay)}));
        } else if (delay > 0) {
            return schedule(new ScheduledFutureTask(this, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay)));
        } else {
            throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[]{Long.valueOf(delay)}));
        }
    }

    <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
        if (inEventLoop()) {
            scheduledTaskQueue().add(task);
        } else {
            execute(new Runnable() {
                public void run() {
                    AbstractScheduledEventExecutor.this.scheduledTaskQueue().add(task);
                }
            });
        }
        return task;
    }

    final void removeScheduled(final ScheduledFutureTask<?> task) {
        if (inEventLoop()) {
            scheduledTaskQueue().remove(task);
        } else {
            execute(new Runnable() {
                public void run() {
                    AbstractScheduledEventExecutor.this.removeScheduled(task);
                }
            });
        }
    }
}
