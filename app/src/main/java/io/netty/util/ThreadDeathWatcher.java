package io.netty.util;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ThreadDeathWatcher {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadDeathWatcher.class);
    private static final Queue<Entry> pendingEntries = PlatformDependent.newMpscQueue();
    private static final AtomicBoolean started = new AtomicBoolean();
    static final ThreadFactory threadFactory;
    private static final Watcher watcher = new Watcher();
    private static volatile Thread watcherThread;

    private static final class Entry {
        final boolean isWatch;
        final Runnable task;
        final Thread thread;

        Entry(Thread thread, Runnable task, boolean isWatch) {
            this.thread = thread;
            this.task = task;
            this.isWatch = isWatch;
        }

        public int hashCode() {
            return this.thread.hashCode() ^ this.task.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry that = (Entry) obj;
            if (this.thread == that.thread && this.task == that.task) {
                return true;
            }
            return false;
        }
    }

    private static final class Watcher implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = (!ThreadDeathWatcher.class.desiredAssertionStatus());
        private final List<Entry> watchees;

        private Watcher() {
            this.watchees = new ArrayList();
        }

        public void run() {
            while (true) {
                fetchWatchees();
                notifyWatchees();
                fetchWatchees();
                notifyWatchees();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                if (this.watchees.isEmpty() && ThreadDeathWatcher.pendingEntries.isEmpty()) {
                    boolean stopped = ThreadDeathWatcher.started.compareAndSet(true, false);
                    if (!$assertionsDisabled && !stopped) {
                        throw new AssertionError();
                    } else if (!ThreadDeathWatcher.pendingEntries.isEmpty()) {
                        if (!ThreadDeathWatcher.started.compareAndSet(false, true)) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        private void fetchWatchees() {
            while (true) {
                Entry e = (Entry) ThreadDeathWatcher.pendingEntries.poll();
                if (e != null) {
                    if (e.isWatch) {
                        this.watchees.add(e);
                    } else {
                        this.watchees.remove(e);
                    }
                } else {
                    return;
                }
            }
        }

        private void notifyWatchees() {
            List<Entry> watchees = this.watchees;
            int i = 0;
            while (i < watchees.size()) {
                Entry e = (Entry) watchees.get(i);
                if (e.thread.isAlive()) {
                    i++;
                } else {
                    watchees.remove(i);
                    try {
                        e.task.run();
                    } catch (Throwable t) {
                        ThreadDeathWatcher.logger.warn("Thread death watcher task raised an exception:", t);
                    }
                }
            }
        }
    }

    static {
        String poolName = "threadDeathWatcher";
        String serviceThreadPrefix = SystemPropertyUtil.get("io.netty.serviceThreadPrefix");
        if (!StringUtil.isNullOrEmpty(serviceThreadPrefix)) {
            poolName = serviceThreadPrefix + poolName;
        }
        threadFactory = new DefaultThreadFactory(poolName, true, 1, null);
    }

    public static void watch(Thread thread, Runnable task) {
        if (thread == null) {
            throw new NullPointerException("thread");
        } else if (task == null) {
            throw new NullPointerException("task");
        } else if (thread.isAlive()) {
            schedule(thread, task, true);
        } else {
            throw new IllegalArgumentException("thread must be alive.");
        }
    }

    public static void unwatch(Thread thread, Runnable task) {
        if (thread == null) {
            throw new NullPointerException("thread");
        } else if (task == null) {
            throw new NullPointerException("task");
        } else {
            schedule(thread, task, false);
        }
    }

    private static void schedule(Thread thread, Runnable task, boolean isWatch) {
        pendingEntries.add(new Entry(thread, task, isWatch));
        if (started.compareAndSet(false, true)) {
            Thread watcherThread = threadFactory.newThread(watcher);
            watcherThread.start();
            watcherThread = watcherThread;
        }
    }

    public static boolean awaitInactivity(long timeout, TimeUnit unit) throws InterruptedException {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        Thread watcherThread = watcherThread;
        if (watcherThread == null) {
            return true;
        }
        watcherThread.join(unit.toMillis(timeout));
        if (watcherThread.isAlive()) {
            return false;
        }
        return true;
    }

    private ThreadDeathWatcher() {
    }
}
