package io.netty.util.concurrent;

import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public abstract class SingleThreadEventExecutor extends AbstractScheduledEventExecutor implements OrderedEventExecutor {
    static final /* synthetic */ boolean $assertionsDisabled = (!SingleThreadEventExecutor.class.desiredAssertionStatus());
    static final int DEFAULT_MAX_PENDING_EXECUTOR_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventexecutor.maxPendingTasks", Integer.MAX_VALUE));
    private static final Runnable NOOP_TASK = new 2();
    private static final AtomicReferenceFieldUpdater<SingleThreadEventExecutor, ThreadProperties> PROPERTIES_UPDATER;
    private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1);
    private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER;
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_SHUTDOWN = 4;
    private static final int ST_SHUTTING_DOWN = 3;
    private static final int ST_STARTED = 2;
    private static final int ST_TERMINATED = 5;
    private static final Runnable WAKEUP_TASK = new 1();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
    private final boolean addTaskWakesUp;
    private final Executor executor;
    private volatile long gracefulShutdownQuietPeriod;
    private long gracefulShutdownStartTime;
    private volatile long gracefulShutdownTimeout;
    private volatile boolean interrupted;
    private long lastExecutionTime;
    private final int maxPendingTasks;
    private final RejectedExecutionHandler rejectedExecutionHandler;
    private final Set<Runnable> shutdownHooks;
    private volatile int state;
    private final Queue<Runnable> taskQueue;
    private final Promise<?> terminationFuture;
    private volatile Thread thread;
    private final Semaphore threadLock;
    private volatile ThreadProperties threadProperties;

    protected abstract void run();

    static {
        AtomicIntegerFieldUpdater<SingleThreadEventExecutor> updater = PlatformDependent.newAtomicIntegerFieldUpdater(SingleThreadEventExecutor.class, DownloadRecord.COLUMN_STATE);
        if (updater == null) {
            updater = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, DownloadRecord.COLUMN_STATE);
        }
        STATE_UPDATER = updater;
        AtomicReferenceFieldUpdater<SingleThreadEventExecutor, ThreadProperties> propertiesUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(SingleThreadEventExecutor.class, "threadProperties");
        if (propertiesUpdater == null) {
            propertiesUpdater = AtomicReferenceFieldUpdater.newUpdater(SingleThreadEventExecutor.class, ThreadProperties.class, "threadProperties");
        }
        PROPERTIES_UPDATER = propertiesUpdater;
    }

    protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
        this(parent, new ThreadPerTaskExecutor(threadFactory), addTaskWakesUp);
    }

    protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
        this(parent, new ThreadPerTaskExecutor(threadFactory), addTaskWakesUp, maxPendingTasks, rejectedHandler);
    }

    protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp) {
        this(parent, executor, addTaskWakesUp, DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
        super(parent);
        this.threadLock = new Semaphore(0);
        this.shutdownHooks = new LinkedHashSet();
        this.state = 1;
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        this.addTaskWakesUp = addTaskWakesUp;
        this.maxPendingTasks = Math.max(16, maxPendingTasks);
        this.executor = (Executor) ObjectUtil.checkNotNull(executor, "executor");
        this.taskQueue = newTaskQueue(this.maxPendingTasks);
        this.rejectedExecutionHandler = (RejectedExecutionHandler) ObjectUtil.checkNotNull(rejectedHandler, "rejectedHandler");
    }

    @Deprecated
    protected Queue<Runnable> newTaskQueue() {
        return newTaskQueue(this.maxPendingTasks);
    }

    protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
        return new LinkedBlockingQueue(maxPendingTasks);
    }

    protected void interruptThread() {
        Thread currentThread = this.thread;
        if (currentThread == null) {
            this.interrupted = true;
        } else {
            currentThread.interrupt();
        }
    }

    protected Runnable pollTask() {
        if ($assertionsDisabled || inEventLoop()) {
            return pollTaskFrom(this.taskQueue);
        }
        throw new AssertionError();
    }

    protected final Runnable pollTaskFrom(Queue<Runnable> taskQueue) {
        Runnable task;
        do {
            task = (Runnable) taskQueue.poll();
        } while (task == WAKEUP_TASK);
        return task;
    }

    protected Runnable takeTask() {
        if (!$assertionsDisabled && !inEventLoop()) {
            throw new AssertionError();
        } else if (this.taskQueue instanceof BlockingQueue) {
            Runnable task;
            BlockingQueue<Runnable> taskQueue = this.taskQueue;
            do {
                ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
                if (scheduledTask == null) {
                    task = null;
                    try {
                        task = (Runnable) taskQueue.take();
                        if (task == WAKEUP_TASK) {
                            return null;
                        }
                        return task;
                    } catch (InterruptedException e) {
                        return task;
                    }
                }
                long delayNanos = scheduledTask.delayNanos();
                task = null;
                if (delayNanos > 0) {
                    try {
                        task = (Runnable) taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException e2) {
                        return null;
                    }
                }
                if (task == null) {
                    fetchFromScheduledTaskQueue();
                    task = (Runnable) taskQueue.poll();
                    continue;
                }
            } while (task == null);
            return task;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean fetchFromScheduledTaskQueue() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        Runnable scheduledTask = pollScheduledTask(nanoTime);
        while (scheduledTask != null) {
            if (this.taskQueue.offer(scheduledTask)) {
                scheduledTask = pollScheduledTask(nanoTime);
            } else {
                scheduledTaskQueue().add((ScheduledFutureTask) scheduledTask);
                return false;
            }
        }
        return true;
    }

    protected Runnable peekTask() {
        if ($assertionsDisabled || inEventLoop()) {
            return (Runnable) this.taskQueue.peek();
        }
        throw new AssertionError();
    }

    protected boolean hasTasks() {
        if ($assertionsDisabled || inEventLoop()) {
            return !this.taskQueue.isEmpty();
        } else {
            throw new AssertionError();
        }
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    protected void addTask(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        } else if (!offerTask(task)) {
            reject(task);
        }
    }

    final boolean offerTask(Runnable task) {
        if (isShutdown()) {
            reject();
        }
        return this.taskQueue.offer(task);
    }

    protected boolean removeTask(Runnable task) {
        if (task != null) {
            return this.taskQueue.remove(task);
        }
        throw new NullPointerException("task");
    }

    protected boolean runAllTasks() {
        if ($assertionsDisabled || inEventLoop()) {
            boolean ranAtLeastOne = false;
            boolean fetchedAll;
            do {
                fetchedAll = fetchFromScheduledTaskQueue();
                if (runAllTasksFrom(this.taskQueue)) {
                    ranAtLeastOne = true;
                    continue;
                }
            } while (!fetchedAll);
            if (ranAtLeastOne) {
                this.lastExecutionTime = ScheduledFutureTask.nanoTime();
            }
            afterRunningAllTasks();
            return ranAtLeastOne;
        }
        throw new AssertionError();
    }

    protected final boolean runAllTasksFrom(Queue<Runnable> taskQueue) {
        Runnable task = pollTaskFrom(taskQueue);
        if (task == null) {
            return false;
        }
        do {
            safeExecute(task);
            task = pollTaskFrom(taskQueue);
        } while (task != null);
        return true;
    }

    protected boolean runAllTasks(long timeoutNanos) {
        fetchFromScheduledTaskQueue();
        Runnable task = pollTask();
        if (task == null) {
            afterRunningAllTasks();
            return false;
        }
        long lastExecutionTime;
        long deadline = ScheduledFutureTask.nanoTime() + timeoutNanos;
        long runTasks = 0;
        do {
            safeExecute(task);
            runTasks++;
            if ((63 & runTasks) == 0) {
                lastExecutionTime = ScheduledFutureTask.nanoTime();
                if (lastExecutionTime >= deadline) {
                    break;
                }
            }
            task = pollTask();
        } while (task != null);
        lastExecutionTime = ScheduledFutureTask.nanoTime();
        afterRunningAllTasks();
        this.lastExecutionTime = lastExecutionTime;
        return true;
    }

    protected void afterRunningAllTasks() {
    }

    protected long delayNanos(long currentTimeNanos) {
        ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
        if (scheduledTask == null) {
            return SCHEDULE_PURGE_INTERVAL;
        }
        return scheduledTask.delayNanos(currentTimeNanos);
    }

    protected void updateLastExecutionTime() {
        this.lastExecutionTime = ScheduledFutureTask.nanoTime();
    }

    protected void cleanup() {
    }

    protected void wakeup(boolean inEventLoop) {
        if (!inEventLoop || STATE_UPDATER.get(this) == 3) {
            this.taskQueue.offer(WAKEUP_TASK);
        }
    }

    public boolean inEventLoop(Thread thread) {
        return thread == this.thread;
    }

    public void addShutdownHook(Runnable task) {
        if (inEventLoop()) {
            this.shutdownHooks.add(task);
        } else {
            execute(new 3(this, task));
        }
    }

    public void removeShutdownHook(Runnable task) {
        if (inEventLoop()) {
            this.shutdownHooks.remove(task);
        } else {
            execute(new 4(this, task));
        }
    }

    private boolean runShutdownHooks() {
        boolean ran = false;
        while (!this.shutdownHooks.isEmpty()) {
            List<Runnable> copy = new ArrayList(this.shutdownHooks);
            this.shutdownHooks.clear();
            for (Runnable task : copy) {
                try {
                    task.run();
                } catch (Throwable t) {
                    logger.warn("Shutdown hook raised an exception.", t);
                } finally {
                    ran = true;
                }
            }
        }
        if (ran) {
            this.lastExecutionTime = ScheduledFutureTask.nanoTime();
        }
        return ran;
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        if (quietPeriod < 0) {
            throw new IllegalArgumentException("quietPeriod: " + quietPeriod + " (expected >= 0)");
        } else if (timeout < quietPeriod) {
            throw new IllegalArgumentException("timeout: " + timeout + " (expected >= quietPeriod (" + quietPeriod + "))");
        } else if (unit == null) {
            throw new NullPointerException("unit");
        } else if (isShuttingDown()) {
            return terminationFuture();
        } else {
            boolean inEventLoop = inEventLoop();
            while (!isShuttingDown()) {
                int newState;
                boolean wakeup = true;
                int oldState = STATE_UPDATER.get(this);
                if (!inEventLoop) {
                    switch (oldState) {
                        case 1:
                        case 2:
                            newState = 3;
                            break;
                        default:
                            newState = oldState;
                            wakeup = false;
                            break;
                    }
                }
                newState = 3;
                if (STATE_UPDATER.compareAndSet(this, oldState, newState)) {
                    this.gracefulShutdownQuietPeriod = unit.toNanos(quietPeriod);
                    this.gracefulShutdownTimeout = unit.toNanos(timeout);
                    if (oldState == 1) {
                        doStartThread();
                    }
                    if (wakeup) {
                        wakeup(inEventLoop);
                    }
                    return terminationFuture();
                }
            }
            return terminationFuture();
        }
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        if (!isShutdown()) {
            boolean inEventLoop = inEventLoop();
            while (!isShuttingDown()) {
                int newState;
                boolean wakeup = true;
                int oldState = STATE_UPDATER.get(this);
                if (!inEventLoop) {
                    switch (oldState) {
                        case 1:
                        case 2:
                        case 3:
                            newState = 4;
                            break;
                        default:
                            newState = oldState;
                            wakeup = false;
                            break;
                    }
                }
                newState = 4;
                if (STATE_UPDATER.compareAndSet(this, oldState, newState)) {
                    if (oldState == 1) {
                        doStartThread();
                    }
                    if (wakeup) {
                        wakeup(inEventLoop);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public boolean isShuttingDown() {
        return STATE_UPDATER.get(this) >= 3;
    }

    public boolean isShutdown() {
        return STATE_UPDATER.get(this) >= 4;
    }

    public boolean isTerminated() {
        return STATE_UPDATER.get(this) == 5;
    }

    protected boolean confirmShutdown() {
        if (!isShuttingDown()) {
            return false;
        }
        if (inEventLoop()) {
            cancelScheduledTasks();
            if (this.gracefulShutdownStartTime == 0) {
                this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
            }
            if (!runAllTasks() && !runShutdownHooks()) {
                long nanoTime = ScheduledFutureTask.nanoTime();
                if (isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout) {
                    return true;
                }
                if (nanoTime - this.lastExecutionTime > this.gracefulShutdownQuietPeriod) {
                    return true;
                }
                wakeup(true);
                try {
                    Thread.sleep(100);
                    return false;
                } catch (InterruptedException e) {
                    return false;
                }
            } else if (isShutdown()) {
                return true;
            } else {
                if (this.gracefulShutdownQuietPeriod == 0) {
                    return true;
                }
                wakeup(true);
                return false;
            }
        }
        throw new IllegalStateException("must be invoked from an event loop");
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        if (unit == null) {
            throw new NullPointerException("unit");
        } else if (inEventLoop()) {
            throw new IllegalStateException("cannot await termination of the current thread");
        } else {
            if (this.threadLock.tryAcquire(timeout, unit)) {
                this.threadLock.release();
            }
            return isTerminated();
        }
    }

    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        boolean inEventLoop = inEventLoop();
        if (inEventLoop) {
            addTask(task);
        } else {
            startThread();
            addTask(task);
            if (isShutdown() && removeTask(task)) {
                reject();
            }
        }
        if (!this.addTaskWakesUp && wakesUpForTask(task)) {
            wakeup(inEventLoop);
        }
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        throwIfInEventLoop("invokeAny");
        return super.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throwIfInEventLoop("invokeAny");
        return super.invokeAny(tasks, timeout, unit);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        throwIfInEventLoop("invokeAll");
        return super.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        throwIfInEventLoop("invokeAll");
        return super.invokeAll(tasks, timeout, unit);
    }

    private void throwIfInEventLoop(String method) {
        if (inEventLoop()) {
            throw new RejectedExecutionException("Calling " + method + " from within the EventLoop is not allowed");
        }
    }

    public final ThreadProperties threadProperties() {
        ThreadProperties threadProperties = this.threadProperties;
        if (threadProperties != null) {
            return threadProperties;
        }
        Thread thread = this.thread;
        if (thread == null) {
            if ($assertionsDisabled || !inEventLoop()) {
                submit(NOOP_TASK).syncUninterruptibly();
                thread = this.thread;
                if (!$assertionsDisabled && thread == null) {
                    throw new AssertionError();
                }
            }
            throw new AssertionError();
        }
        threadProperties = new DefaultThreadProperties(thread);
        if (PROPERTIES_UPDATER.compareAndSet(this, null, threadProperties)) {
            return threadProperties;
        }
        return this.threadProperties;
    }

    protected boolean wakesUpForTask(Runnable task) {
        return true;
    }

    protected static void reject() {
        throw new RejectedExecutionException("event executor terminated");
    }

    protected final void reject(Runnable task) {
        this.rejectedExecutionHandler.rejected(task, this);
    }

    private void startThread() {
        if (STATE_UPDATER.get(this) == 1 && STATE_UPDATER.compareAndSet(this, 1, 2)) {
            doStartThread();
        }
    }

    private void doStartThread() {
        if ($assertionsDisabled || this.thread == null) {
            this.executor.execute(new 5(this));
            return;
        }
        throw new AssertionError();
    }
}
