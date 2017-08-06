package io.netty.util.concurrent;

import io.netty.util.Signal;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {
    private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(ThrowableUtil.unknownStackTrace(new CancellationException(), DefaultPromise.class, "cancel(...)"));
    private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, SystemPropertyUtil.getInt("io.netty.defaultPromise.maxListenerStackDepth", 8));
    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER;
    private static final Signal SUCCESS = Signal.valueOf(DefaultPromise.class, "SUCCESS");
    private static final Signal UNCANCELLABLE = Signal.valueOf(DefaultPromise.class, "UNCANCELLABLE");
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
    private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
    private final EventExecutor executor;
    private Object listeners;
    private boolean notifyingListeners;
    private volatile Object result;
    private short waiters;

    private static final class CauseHolder {
        final Throwable cause;

        CauseHolder(Throwable cause) {
            this.cause = cause;
        }
    }

    private boolean await0(long r12, boolean r14) throws java.lang.InterruptedException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:80)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r11 = this;
        r6 = r11.isDone();
        if (r6 == 0) goto L_0x0008;
    L_0x0006:
        r6 = 1;
    L_0x0007:
        return r6;
    L_0x0008:
        r6 = 0;
        r6 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1));
        if (r6 > 0) goto L_0x0013;
    L_0x000e:
        r6 = r11.isDone();
        goto L_0x0007;
    L_0x0013:
        if (r14 == 0) goto L_0x0025;
    L_0x0015:
        r6 = java.lang.Thread.interrupted();
        if (r6 == 0) goto L_0x0025;
    L_0x001b:
        r6 = new java.lang.InterruptedException;
        r7 = r11.toString();
        r6.<init>(r7);
        throw r6;
    L_0x0025:
        r11.checkDeadLock();
        r2 = java.lang.System.nanoTime();
        r4 = r12;
        r1 = 0;
    L_0x002e:
        monitor-enter(r11);	 Catch:{ all -> 0x0073 }
        r6 = r11.isDone();	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        if (r6 == 0) goto L_0x0041;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
    L_0x0035:
        r6 = 1;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        monitor-exit(r11);	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        if (r1 == 0) goto L_0x0007;
    L_0x0039:
        r7 = java.lang.Thread.currentThread();
        r7.interrupt();
        goto L_0x0007;
    L_0x0041:
        r11.incWaiters();	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        r6 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r6 = r4 / r6;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        r8 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        r8 = r4 % r8;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        r8 = (int) r8;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        r11.wait(r6, r8);	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        r11.decWaiters();	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
    L_0x0055:
        monitor-exit(r11);	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        r6 = r11.isDone();	 Catch:{ all -> 0x0073 }
        if (r6 == 0) goto L_0x0083;
    L_0x005c:
        r6 = 1;
        if (r1 == 0) goto L_0x0007;
    L_0x005f:
        r7 = java.lang.Thread.currentThread();
        r7.interrupt();
        goto L_0x0007;
    L_0x0067:
        r0 = move-exception;
        if (r14 == 0) goto L_0x007e;
    L_0x006a:
        throw r0;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
    L_0x006b:
        r6 = move-exception;
        r11.decWaiters();	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        throw r6;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
    L_0x0070:
        r6 = move-exception;	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        monitor-exit(r11);	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        throw r6;	 Catch:{ all -> 0x0073 }
    L_0x0073:
        r6 = move-exception;
        if (r1 == 0) goto L_0x007d;
    L_0x0076:
        r7 = java.lang.Thread.currentThread();
        r7.interrupt();
    L_0x007d:
        throw r6;
    L_0x007e:
        r1 = 1;
        r11.decWaiters();	 Catch:{ InterruptedException -> 0x0067, all -> 0x006b }
        goto L_0x0055;
    L_0x0083:
        r6 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0073 }
        r6 = r6 - r2;	 Catch:{ all -> 0x0073 }
        r4 = r12 - r6;	 Catch:{ all -> 0x0073 }
        r6 = 0;	 Catch:{ all -> 0x0073 }
        r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));	 Catch:{ all -> 0x0073 }
        if (r6 > 0) goto L_0x002e;	 Catch:{ all -> 0x0073 }
    L_0x0090:
        r6 = r11.isDone();	 Catch:{ all -> 0x0073 }
        if (r1 == 0) goto L_0x0007;
    L_0x0096:
        r7 = java.lang.Thread.currentThread();
        r7.interrupt();
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.DefaultPromise.await0(long, boolean):boolean");
    }

    static {
        AtomicReferenceFieldUpdater<DefaultPromise, Object> updater = PlatformDependent.newAtomicReferenceFieldUpdater(DefaultPromise.class, "result");
        if (updater == null) {
            updater = AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");
        }
        RESULT_UPDATER = updater;
    }

    public DefaultPromise(EventExecutor executor) {
        this.executor = (EventExecutor) ObjectUtil.checkNotNull(executor, "executor");
    }

    protected DefaultPromise() {
        this.executor = null;
    }

    public Promise<V> setSuccess(V result) {
        if (setSuccess0(result)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    public boolean trySuccess(V result) {
        if (!setSuccess0(result)) {
            return false;
        }
        notifyListeners();
        return true;
    }

    public Promise<V> setFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this, cause);
    }

    public boolean tryFailure(Throwable cause) {
        if (!setFailure0(cause)) {
            return false;
        }
        notifyListeners();
        return true;
    }

    public boolean setUncancellable() {
        if (RESULT_UPDATER.compareAndSet(this, null, UNCANCELLABLE)) {
            return true;
        }
        Object result = this.result;
        if (isDone0(result) && isCancelled0(result)) {
            return false;
        }
        return true;
    }

    public boolean isSuccess() {
        Signal result = this.result;
        return (result == null || result == UNCANCELLABLE || (result instanceof CauseHolder)) ? false : true;
    }

    public boolean isCancellable() {
        return this.result == null;
    }

    public Throwable cause() {
        Object result = this.result;
        return result instanceof CauseHolder ? ((CauseHolder) result).cause : null;
    }

    public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
        ObjectUtil.checkNotNull(listener, "listener");
        synchronized (this) {
            addListener0(listener);
        }
        if (isDone()) {
            notifyListeners();
        }
        return this;
    }

    public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
        ObjectUtil.checkNotNull(listeners, "listeners");
        synchronized (this) {
            for (GenericFutureListener<? extends Future<? super V>> listener : listeners) {
                if (listener == null) {
                    break;
                }
                addListener0(listener);
            }
        }
        if (isDone()) {
            notifyListeners();
        }
        return this;
    }

    public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
        ObjectUtil.checkNotNull(listener, "listener");
        synchronized (this) {
            removeListener0(listener);
        }
        return this;
    }

    public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
        ObjectUtil.checkNotNull(listeners, "listeners");
        synchronized (this) {
            for (GenericFutureListener<? extends Future<? super V>> listener : listeners) {
                if (listener == null) {
                    break;
                }
                removeListener0(listener);
            }
        }
        return this;
    }

    public Promise<V> await() throws InterruptedException {
        if (!isDone()) {
            if (Thread.interrupted()) {
                throw new InterruptedException(toString());
            }
            checkDeadLock();
            synchronized (this) {
                while (!isDone()) {
                    incWaiters();
                    try {
                        wait();
                        decWaiters();
                    } catch (Throwable th) {
                        decWaiters();
                    }
                }
            }
        }
        return this;
    }

    public Promise<V> awaitUninterruptibly() {
        if (!isDone()) {
            checkDeadLock();
            boolean interrupted = false;
            synchronized (this) {
                while (!isDone()) {
                    incWaiters();
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        interrupted = true;
                    } finally {
                        decWaiters();
                    }
                }
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
        return this;
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return await0(unit.toNanos(timeout), true);
    }

    public boolean await(long timeoutMillis) throws InterruptedException {
        return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
    }

    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        try {
            return await0(unit.toNanos(timeout), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    public boolean awaitUninterruptibly(long timeoutMillis) {
        try {
            return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    public V getNow() {
        Signal result = this.result;
        if ((result instanceof CauseHolder) || result == SUCCESS) {
            return null;
        }
        return result;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!RESULT_UPDATER.compareAndSet(this, null, CANCELLATION_CAUSE_HOLDER)) {
            return false;
        }
        checkNotifyWaiters();
        notifyListeners();
        return true;
    }

    public boolean isCancelled() {
        return isCancelled0(this.result);
    }

    public boolean isDone() {
        return isDone0(this.result);
    }

    public Promise<V> sync() throws InterruptedException {
        await();
        rethrowIfFailed();
        return this;
    }

    public Promise<V> syncUninterruptibly() {
        awaitUninterruptibly();
        rethrowIfFailed();
        return this;
    }

    public String toString() {
        return toStringBuilder().toString();
    }

    protected StringBuilder toStringBuilder() {
        StringBuilder buf = new StringBuilder(64).append(StringUtil.simpleClassName((Object) this)).append('@').append(Integer.toHexString(hashCode()));
        Signal result = this.result;
        if (result == SUCCESS) {
            buf.append("(success)");
        } else if (result == UNCANCELLABLE) {
            buf.append("(uncancellable)");
        } else if (result instanceof CauseHolder) {
            buf.append("(failure: ").append(((CauseHolder) result).cause).append(')');
        } else if (result != null) {
            buf.append("(success: ").append(result).append(')');
        } else {
            buf.append("(incomplete)");
        }
        return buf;
    }

    protected EventExecutor executor() {
        return this.executor;
    }

    protected void checkDeadLock() {
        EventExecutor e = executor();
        if (e != null && e.inEventLoop()) {
            throw new BlockingOperationException(toString());
        }
    }

    protected static void notifyListener(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> listener) {
        ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
        ObjectUtil.checkNotNull(future, "future");
        ObjectUtil.checkNotNull(listener, "listener");
        notifyListenerWithStackOverFlowProtection(eventExecutor, future, listener);
    }

    private void notifyListeners() {
        EventExecutor executor = executor();
        if (executor.inEventLoop()) {
            InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
            int stackDepth = threadLocals.futureListenerStackDepth();
            if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
                threadLocals.setFutureListenerStackDepth(stackDepth + 1);
                try {
                    notifyListenersNow();
                    return;
                } finally {
                    threadLocals.setFutureListenerStackDepth(stackDepth);
                }
            }
        }
        safeExecute(executor, new Runnable() {
            public void run() {
                DefaultPromise.this.notifyListenersNow();
            }
        });
    }

    private static void notifyListenerWithStackOverFlowProtection(EventExecutor executor, final Future<?> future, final GenericFutureListener<?> listener) {
        if (executor.inEventLoop()) {
            InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
            int stackDepth = threadLocals.futureListenerStackDepth();
            if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
                threadLocals.setFutureListenerStackDepth(stackDepth + 1);
                try {
                    notifyListener0(future, listener);
                    return;
                } finally {
                    threadLocals.setFutureListenerStackDepth(stackDepth);
                }
            }
        }
        safeExecute(executor, new Runnable() {
            public void run() {
                DefaultPromise.notifyListener0(future, listener);
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyListenersNow() {
        /*
        r2 = this;
        monitor-enter(r2);
        r1 = r2.notifyingListeners;	 Catch:{ all -> 0x002b }
        if (r1 != 0) goto L_0x0009;
    L_0x0005:
        r1 = r2.listeners;	 Catch:{ all -> 0x002b }
        if (r1 != 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r2);	 Catch:{ all -> 0x002b }
    L_0x000a:
        return;
    L_0x000b:
        r1 = 1;
        r2.notifyingListeners = r1;	 Catch:{ all -> 0x002b }
        r0 = r2.listeners;	 Catch:{ all -> 0x002b }
        r1 = 0;
        r2.listeners = r1;	 Catch:{ all -> 0x002b }
        monitor-exit(r2);	 Catch:{ all -> 0x002b }
    L_0x0014:
        r1 = r0 instanceof io.netty.util.concurrent.DefaultFutureListeners;
        if (r1 == 0) goto L_0x002e;
    L_0x0018:
        r1 = r0;
        r1 = (io.netty.util.concurrent.DefaultFutureListeners) r1;
        r2.notifyListeners0(r1);
    L_0x001e:
        monitor-enter(r2);
        r1 = r2.listeners;	 Catch:{ all -> 0x0028 }
        if (r1 != 0) goto L_0x0035;
    L_0x0023:
        r1 = 0;
        r2.notifyingListeners = r1;	 Catch:{ all -> 0x0028 }
        monitor-exit(r2);	 Catch:{ all -> 0x0028 }
        goto L_0x000a;
    L_0x0028:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0028 }
        throw r1;
    L_0x002b:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x002b }
        throw r1;
    L_0x002e:
        r1 = r0;
        r1 = (io.netty.util.concurrent.GenericFutureListener) r1;
        notifyListener0(r2, r1);
        goto L_0x001e;
    L_0x0035:
        r0 = r2.listeners;	 Catch:{ all -> 0x0028 }
        r1 = 0;
        r2.listeners = r1;	 Catch:{ all -> 0x0028 }
        monitor-exit(r2);	 Catch:{ all -> 0x0028 }
        goto L_0x0014;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.DefaultPromise.notifyListenersNow():void");
    }

    private void notifyListeners0(DefaultFutureListeners listeners) {
        GenericFutureListener<?>[] a = listeners.listeners();
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            notifyListener0(this, a[i]);
        }
    }

    private static void notifyListener0(Future future, GenericFutureListener l) {
        try {
            l.operationComplete(future);
        } catch (Throwable t) {
            logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", t);
        }
    }

    private void addListener0(GenericFutureListener<? extends Future<? super V>> listener) {
        if (this.listeners == null) {
            this.listeners = listener;
        } else if (this.listeners instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners) this.listeners).add(listener);
        } else {
            this.listeners = new DefaultFutureListeners((GenericFutureListener) this.listeners, listener);
        }
    }

    private void removeListener0(GenericFutureListener<? extends Future<? super V>> listener) {
        if (this.listeners instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners) this.listeners).remove(listener);
        } else if (this.listeners == listener) {
            this.listeners = null;
        }
    }

    private boolean setSuccess0(V result) {
        if (result == null) {
            result = SUCCESS;
        }
        return setValue0(result);
    }

    private boolean setFailure0(Throwable cause) {
        return setValue0(new CauseHolder((Throwable) ObjectUtil.checkNotNull(cause, "cause")));
    }

    private boolean setValue0(Object objResult) {
        if (!RESULT_UPDATER.compareAndSet(this, null, objResult) && !RESULT_UPDATER.compareAndSet(this, UNCANCELLABLE, objResult)) {
            return false;
        }
        checkNotifyWaiters();
        return true;
    }

    private synchronized void checkNotifyWaiters() {
        if (this.waiters > (short) 0) {
            notifyAll();
        }
    }

    private void incWaiters() {
        if (this.waiters == Short.MAX_VALUE) {
            throw new IllegalStateException("too many waiters: " + this);
        }
        this.waiters = (short) (this.waiters + 1);
    }

    private void decWaiters() {
        this.waiters = (short) (this.waiters - 1);
    }

    private void rethrowIfFailed() {
        Throwable cause = cause();
        if (cause != null) {
            PlatformDependent.throwException(cause);
        }
    }

    void notifyProgressiveListeners(long progress, long total) {
        GenericProgressiveFutureListener<ProgressiveFuture<V>> listeners = progressiveListeners();
        if (listeners != null) {
            ProgressiveFuture<V> self = (ProgressiveFuture) this;
            EventExecutor executor = executor();
            if (executor.inEventLoop()) {
                if (listeners instanceof GenericProgressiveFutureListener[]) {
                    notifyProgressiveListeners0(self, (GenericProgressiveFutureListener[]) listeners, progress, total);
                } else {
                    notifyProgressiveListener0(self, listeners, progress, total);
                }
            } else if (listeners instanceof GenericProgressiveFutureListener[]) {
                final GenericProgressiveFutureListener[] array = (GenericProgressiveFutureListener[]) ((GenericProgressiveFutureListener[]) listeners);
                final ProgressiveFuture<V> progressiveFuture = self;
                final long j = progress;
                final long j2 = total;
                safeExecute(executor, new Runnable() {
                    public void run() {
                        DefaultPromise.notifyProgressiveListeners0(progressiveFuture, array, j, j2);
                    }
                });
            } else {
                final GenericProgressiveFutureListener<ProgressiveFuture<V>> l = listeners;
                final ProgressiveFuture<V> progressiveFuture2 = self;
                final long j3 = progress;
                final long j4 = total;
                safeExecute(executor, new Runnable() {
                    public void run() {
                        DefaultPromise.notifyProgressiveListener0(progressiveFuture2, l, j3, j4);
                    }
                });
            }
        }
    }

    private synchronized Object progressiveListeners() {
        Object obj;
        Object listeners = this.listeners;
        if (listeners == null) {
            obj = null;
        } else if (listeners instanceof DefaultFutureListeners) {
            DefaultFutureListeners dfl = (DefaultFutureListeners) listeners;
            int progressiveSize = dfl.progressiveSize();
            switch (progressiveSize) {
                case 0:
                    obj = null;
                    break;
                case 1:
                    for (Object obj2 : dfl.listeners()) {
                        if (obj2 instanceof GenericProgressiveFutureListener) {
                            break;
                        }
                    }
                    obj2 = null;
                    break;
                default:
                    GenericFutureListener<?>[] array = dfl.listeners();
                    Object copy = new GenericProgressiveFutureListener[progressiveSize];
                    int i = 0;
                    int j = 0;
                    while (j < progressiveSize) {
                        int j2;
                        GenericFutureListener<?> l = array[i];
                        if (l instanceof GenericProgressiveFutureListener) {
                            j2 = j + 1;
                            copy[j] = (GenericProgressiveFutureListener) l;
                        } else {
                            j2 = j;
                        }
                        i++;
                        j = j2;
                    }
                    obj2 = copy;
                    break;
            }
        } else {
            obj2 = listeners instanceof GenericProgressiveFutureListener ? listeners : null;
        }
        return obj2;
    }

    private static void notifyProgressiveListeners0(ProgressiveFuture<?> future, GenericProgressiveFutureListener<?>[] listeners, long progress, long total) {
        GenericProgressiveFutureListener<?>[] arr$ = listeners;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            GenericProgressiveFutureListener<?> l = arr$[i$];
            if (l != null) {
                notifyProgressiveListener0(future, l, progress, total);
                i$++;
            } else {
                return;
            }
        }
    }

    private static void notifyProgressiveListener0(ProgressiveFuture future, GenericProgressiveFutureListener l, long progress, long total) {
        try {
            l.operationProgressed(future, progress, total);
        } catch (Throwable t) {
            logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationProgressed()", t);
        }
    }

    private static boolean isCancelled0(Object result) {
        return (result instanceof CauseHolder) && (((CauseHolder) result).cause instanceof CancellationException);
    }

    private static boolean isDone0(Object result) {
        return (result == null || result == UNCANCELLABLE) ? false : true;
    }

    private static void safeExecute(EventExecutor executor, Runnable task) {
        try {
            executor.execute(task);
        } catch (Throwable t) {
            rejectedExecutionLogger.error("Failed to submit a_isRightVersion listener notification task. Event loop shut down?", t);
        }
    }
}
