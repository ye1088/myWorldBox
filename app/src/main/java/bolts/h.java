package bolts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Task */
public class h<TResult> {
    public static final ExecutorService df = b.aN();
    private static final Executor dg = b.aP();
    public static final Executor dh = a.aL();
    private static volatile b dj;
    private static h<?> dp = new h(null);
    private static h<Boolean> dq = new h(Boolean.valueOf(true));
    private static h<Boolean> dr = new h(Boolean.valueOf(false));
    private static h<?> dt = new h(true);
    private boolean cancelled;
    private boolean complete;
    private Exception dk;
    private boolean dl;
    private j dm;
    private List<g<TResult, Void>> do = new ArrayList();
    private final Object lock = new Object();
    private TResult result;

    /* compiled from: Task */
    public class a extends i<TResult> {
        final /* synthetic */ h dy;

        a(h hVar) {
            this.dy = hVar;
        }
    }

    /* compiled from: Task */
    public interface b {
        void a(h<?> hVar, UnobservedTaskException unobservedTaskException);
    }

    public static b aY() {
        return dj;
    }

    public static void a(b eh) {
        dj = eh;
    }

    h() {
    }

    private h(TResult result) {
        c((Object) result);
    }

    private h(boolean cancelled) {
        if (cancelled) {
            bh();
        } else {
            c(null);
        }
    }

    public static <TResult> a aZ() {
        h<TResult> task = new h();
        task.getClass();
        return new a(task);
    }

    public boolean isCompleted() {
        boolean z;
        synchronized (this.lock) {
            z = this.complete;
        }
        return z;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this.lock) {
            z = this.cancelled;
        }
        return z;
    }

    public boolean ba() {
        boolean z;
        synchronized (this.lock) {
            z = bb() != null;
        }
        return z;
    }

    public TResult getResult() {
        TResult tResult;
        synchronized (this.lock) {
            tResult = this.result;
        }
        return tResult;
    }

    public Exception bb() {
        Exception exception;
        synchronized (this.lock) {
            if (this.dk != null) {
                this.dl = true;
                if (this.dm != null) {
                    this.dm.bk();
                    this.dm = null;
                }
            }
            exception = this.dk;
        }
        return exception;
    }

    public void bc() throws InterruptedException {
        synchronized (this.lock) {
            if (!isCompleted()) {
                this.lock.wait();
            }
        }
    }

    public boolean b(long duration, TimeUnit timeUnit) throws InterruptedException {
        boolean isCompleted;
        synchronized (this.lock) {
            if (!isCompleted()) {
                this.lock.wait(timeUnit.toMillis(duration));
            }
            isCompleted = isCompleted();
        }
        return isCompleted;
    }

    public static <TResult> h<TResult> b(TResult value) {
        if (value == null) {
            return dp;
        }
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue() ? dq : dr;
        } else {
            i<TResult> tcs = new i();
            tcs.setResult(value);
            return tcs.bi();
        }
    }

    public static <TResult> h<TResult> f(Exception error) {
        i<TResult> tcs = new i();
        tcs.h(error);
        return tcs.bi();
    }

    public static <TResult> h<TResult> bd() {
        return dt;
    }

    public static h<Void> f(long delay) {
        return a(delay, b.aO(), null);
    }

    public static h<Void> a(long delay, c cancellationToken) {
        return a(delay, b.aO(), cancellationToken);
    }

    static h<Void> a(long delay, ScheduledExecutorService executor, c cancellationToken) {
        if (cancellationToken != null && cancellationToken.aS()) {
            return bd();
        }
        if (delay <= 0) {
            return b(null);
        }
        final i<Void> tcs = new i();
        final ScheduledFuture<?> scheduled = executor.schedule(new Runnable() {
            public void run() {
                tcs.c(null);
            }
        }, delay, TimeUnit.MILLISECONDS);
        if (cancellationToken != null) {
            cancellationToken.c(new Runnable() {
                public void run() {
                    scheduled.cancel(true);
                    tcs.bh();
                }
            });
        }
        return tcs.bi();
    }

    public <TOut> h<TOut> be() {
        return this;
    }

    public h<Void> bf() {
        return b(new g<TResult, h<Void>>(this) {
            final /* synthetic */ h dy;

            {
                this.dy = r1;
            }

            public /* synthetic */ Object a(h x0) throws Exception {
                return c(x0);
            }

            public h<Void> c(h<TResult> task) throws Exception {
                if (task.isCancelled()) {
                    return h.bd();
                }
                if (task.ba()) {
                    return h.f(task.bb());
                }
                return h.b(null);
            }
        });
    }

    public static <TResult> h<TResult> a(Callable<TResult> callable) {
        return a((Callable) callable, df, null);
    }

    public static <TResult> h<TResult> a(Callable<TResult> callable, c ct) {
        return a((Callable) callable, df, ct);
    }

    public static <TResult> h<TResult> a(Callable<TResult> callable, Executor executor) {
        return a((Callable) callable, executor, null);
    }

    public static <TResult> h<TResult> a(final Callable<TResult> callable, Executor executor, final c ct) {
        final i<TResult> tcs = new i();
        try {
            executor.execute(new Runnable() {
                public void run() {
                    if (ct == null || !ct.aS()) {
                        try {
                            tcs.setResult(callable.call());
                            return;
                        } catch (CancellationException e) {
                            tcs.bj();
                            return;
                        } catch (Exception e2) {
                            tcs.h(e2);
                            return;
                        }
                    }
                    tcs.bj();
                }
            });
        } catch (Exception e) {
            tcs.h(new ExecutorException(e));
        }
        return tcs.bi();
    }

    public static <TResult> h<TResult> b(Callable<TResult> callable) {
        return a((Callable) callable, dg, null);
    }

    public static <TResult> h<TResult> b(Callable<TResult> callable, c ct) {
        return a((Callable) callable, dg, ct);
    }

    public static <TResult> h<h<TResult>> b(Collection<? extends h<TResult>> tasks) {
        if (tasks.size() == 0) {
            return b(null);
        }
        final i<h<TResult>> firstCompleted = new i();
        final AtomicBoolean isAnyTaskComplete = new AtomicBoolean(false);
        for (h<TResult> task : tasks) {
            task.a(new g<TResult, Void>() {
                public /* synthetic */ Object a(h x0) throws Exception {
                    return b(x0);
                }

                public Void b(h<TResult> task) {
                    if (isAnyTaskComplete.compareAndSet(false, true)) {
                        firstCompleted.setResult(task);
                    } else {
                        task.bb();
                    }
                    return null;
                }
            });
        }
        return firstCompleted.bi();
    }

    public static h<h<?>> c(Collection<? extends h<?>> tasks) {
        if (tasks.size() == 0) {
            return b(null);
        }
        final i<h<?>> firstCompleted = new i();
        final AtomicBoolean isAnyTaskComplete = new AtomicBoolean(false);
        for (h<?> task : tasks) {
            task.a(new g<Object, Void>() {
                public /* synthetic */ Object a(h x0) throws Exception {
                    return b(x0);
                }

                public Void b(h<Object> task) {
                    if (isAnyTaskComplete.compareAndSet(false, true)) {
                        firstCompleted.setResult(task);
                    } else {
                        task.bb();
                    }
                    return null;
                }
            });
        }
        return firstCompleted.bi();
    }

    public static <TResult> h<List<TResult>> d(final Collection<? extends h<TResult>> tasks) {
        return e(tasks).c(new g<Void, List<TResult>>() {
            public /* synthetic */ Object a(h x0) throws Exception {
                return d(x0);
            }

            public List<TResult> d(h<Void> hVar) throws Exception {
                if (tasks.size() == 0) {
                    return Collections.emptyList();
                }
                List<TResult> results = new ArrayList();
                for (h<TResult> individualTask : tasks) {
                    results.add(individualTask.getResult());
                }
                return results;
            }
        });
    }

    public static h<Void> e(Collection<? extends h<?>> tasks) {
        if (tasks.size() == 0) {
            return b(null);
        }
        final i<Void> allFinished = new i();
        final ArrayList<Exception> causes = new ArrayList();
        final Object errorLock = new Object();
        final AtomicInteger count = new AtomicInteger(tasks.size());
        final AtomicBoolean isCancelled = new AtomicBoolean(false);
        for (h<?> task : tasks) {
            task.a(new g<Object, Void>() {
                public /* synthetic */ Object a(h x0) throws Exception {
                    return b(x0);
                }

                public Void b(h<Object> task) {
                    if (task.ba()) {
                        synchronized (errorLock) {
                            causes.add(task.bb());
                        }
                    }
                    if (task.isCancelled()) {
                        isCancelled.set(true);
                    }
                    if (count.decrementAndGet() == 0) {
                        if (causes.size() != 0) {
                            if (causes.size() == 1) {
                                allFinished.h((Exception) causes.get(0));
                            } else {
                                allFinished.h(new AggregateException(String.format("There were %d exceptions.", new Object[]{Integer.valueOf(causes.size())}), causes));
                            }
                        } else if (isCancelled.get()) {
                            allFinished.bj();
                        } else {
                            allFinished.setResult(null);
                        }
                    }
                    return null;
                }
            });
        }
        return allFinished.bi();
    }

    public h<Void> a(Callable<Boolean> predicate, g<Void, h<Void>> continuation) {
        return a(predicate, continuation, dg, null);
    }

    public h<Void> a(Callable<Boolean> predicate, g<Void, h<Void>> continuation, c ct) {
        return a(predicate, continuation, dg, ct);
    }

    public h<Void> a(Callable<Boolean> predicate, g<Void, h<Void>> continuation, Executor executor) {
        return a(predicate, continuation, executor, null);
    }

    public h<Void> a(Callable<Boolean> predicate, g<Void, h<Void>> continuation, Executor executor, c ct) {
        final f<g<Void, h<Void>>> predicateContinuation = new f();
        final c cVar = ct;
        final Callable<Boolean> callable = predicate;
        final g<Void, h<Void>> gVar = continuation;
        final Executor executor2 = executor;
        predicateContinuation.set(new g<Void, h<Void>>(this) {
            final /* synthetic */ h dy;

            public /* synthetic */ Object a(h x0) throws Exception {
                return c(x0);
            }

            public h<Void> c(h<Void> hVar) throws Exception {
                if (cVar != null && cVar.aS()) {
                    return h.bd();
                }
                if (((Boolean) callable.call()).booleanValue()) {
                    return h.b(null).d(gVar, executor2).d((g) predicateContinuation.get(), executor2);
                }
                return h.b(null);
            }
        });
        return bf().b((g) predicateContinuation.get(), executor);
    }

    public <TContinuationResult> h<TContinuationResult> a(g<TResult, TContinuationResult> continuation, Executor executor) {
        return a((g) continuation, executor, null);
    }

    public <TContinuationResult> h<TContinuationResult> a(g<TResult, TContinuationResult> continuation, Executor executor, c ct) {
        final i<TContinuationResult> tcs = new i();
        synchronized (this.lock) {
            boolean completed = isCompleted();
            if (!completed) {
                final g<TResult, TContinuationResult> gVar = continuation;
                final Executor executor2 = executor;
                final c cVar = ct;
                this.do.add(new g<TResult, Void>(this) {
                    final /* synthetic */ h dy;

                    public /* synthetic */ Object a(h x0) throws Exception {
                        return b(x0);
                    }

                    public Void b(h<TResult> task) {
                        h.a(tcs, gVar, task, executor2, cVar);
                        return null;
                    }
                });
            }
        }
        if (completed) {
            a(tcs, continuation, this, executor, ct);
        }
        return tcs.bi();
    }

    public <TContinuationResult> h<TContinuationResult> a(g<TResult, TContinuationResult> continuation) {
        return a((g) continuation, dg, null);
    }

    public <TContinuationResult> h<TContinuationResult> a(g<TResult, TContinuationResult> continuation, c ct) {
        return a((g) continuation, dg, ct);
    }

    public <TContinuationResult> h<TContinuationResult> b(g<TResult, h<TContinuationResult>> continuation, Executor executor) {
        return b(continuation, executor, null);
    }

    public <TContinuationResult> h<TContinuationResult> b(g<TResult, h<TContinuationResult>> continuation, Executor executor, c ct) {
        final i<TContinuationResult> tcs = new i();
        synchronized (this.lock) {
            boolean completed = isCompleted();
            if (!completed) {
                final g<TResult, h<TContinuationResult>> gVar = continuation;
                final Executor executor2 = executor;
                final c cVar = ct;
                this.do.add(new g<TResult, Void>(this) {
                    final /* synthetic */ h dy;

                    public /* synthetic */ Object a(h x0) throws Exception {
                        return b(x0);
                    }

                    public Void b(h<TResult> task) {
                        h.b(tcs, gVar, task, executor2, cVar);
                        return null;
                    }
                });
            }
        }
        if (completed) {
            b(tcs, continuation, this, executor, ct);
        }
        return tcs.bi();
    }

    public <TContinuationResult> h<TContinuationResult> b(g<TResult, h<TContinuationResult>> continuation) {
        return b(continuation, dg, null);
    }

    public <TContinuationResult> h<TContinuationResult> b(g<TResult, h<TContinuationResult>> continuation, c ct) {
        return b(continuation, dg, ct);
    }

    public <TContinuationResult> h<TContinuationResult> c(g<TResult, TContinuationResult> continuation, Executor executor) {
        return c(continuation, executor, null);
    }

    public <TContinuationResult> h<TContinuationResult> c(final g<TResult, TContinuationResult> continuation, Executor executor, final c ct) {
        return b(new g<TResult, h<TContinuationResult>>(this) {
            final /* synthetic */ h dy;

            public /* synthetic */ Object a(h x0) throws Exception {
                return c(x0);
            }

            public h<TContinuationResult> c(h<TResult> task) {
                if (ct != null && ct.aS()) {
                    return h.bd();
                }
                if (task.ba()) {
                    return h.f(task.bb());
                }
                if (task.isCancelled()) {
                    return h.bd();
                }
                return task.a(continuation);
            }
        }, executor);
    }

    public <TContinuationResult> h<TContinuationResult> c(g<TResult, TContinuationResult> continuation) {
        return c(continuation, dg, null);
    }

    public <TContinuationResult> h<TContinuationResult> c(g<TResult, TContinuationResult> continuation, c ct) {
        return c(continuation, dg, ct);
    }

    public <TContinuationResult> h<TContinuationResult> d(g<TResult, h<TContinuationResult>> continuation, Executor executor) {
        return d(continuation, executor, null);
    }

    public <TContinuationResult> h<TContinuationResult> d(final g<TResult, h<TContinuationResult>> continuation, Executor executor, final c ct) {
        return b(new g<TResult, h<TContinuationResult>>(this) {
            final /* synthetic */ h dy;

            public /* synthetic */ Object a(h x0) throws Exception {
                return c(x0);
            }

            public h<TContinuationResult> c(h<TResult> task) {
                if (ct != null && ct.aS()) {
                    return h.bd();
                }
                if (task.ba()) {
                    return h.f(task.bb());
                }
                if (task.isCancelled()) {
                    return h.bd();
                }
                return task.b(continuation);
            }
        }, executor);
    }

    public <TContinuationResult> h<TContinuationResult> d(g<TResult, h<TContinuationResult>> continuation) {
        return d((g) continuation, dg);
    }

    public <TContinuationResult> h<TContinuationResult> d(g<TResult, h<TContinuationResult>> continuation, c ct) {
        return d(continuation, dg, ct);
    }

    private static <TContinuationResult, TResult> void a(final i<TContinuationResult> tcs, final g<TResult, TContinuationResult> continuation, final h<TResult> task, Executor executor, final c ct) {
        try {
            executor.execute(new Runnable() {
                public void run() {
                    if (ct == null || !ct.aS()) {
                        try {
                            tcs.setResult(continuation.a(task));
                            return;
                        } catch (CancellationException e) {
                            tcs.bj();
                            return;
                        } catch (Exception e2) {
                            tcs.h(e2);
                            return;
                        }
                    }
                    tcs.bj();
                }
            });
        } catch (Exception e) {
            tcs.h(new ExecutorException(e));
        }
    }

    private static <TContinuationResult, TResult> void b(final i<TContinuationResult> tcs, final g<TResult, h<TContinuationResult>> continuation, final h<TResult> task, Executor executor, final c ct) {
        try {
            executor.execute(new Runnable() {
                public void run() {
                    if (ct == null || !ct.aS()) {
                        try {
                            h<TContinuationResult> result = (h) continuation.a(task);
                            if (result == null) {
                                tcs.setResult(null);
                                return;
                            } else {
                                result.a(new g<TContinuationResult, Void>(this) {
                                    final /* synthetic */ AnonymousClass7 dA;

                                    {
                                        this.dA = r1;
                                    }

                                    public /* synthetic */ Object a(h x0) throws Exception {
                                        return b(x0);
                                    }

                                    public Void b(h<TContinuationResult> task) {
                                        if (ct != null && ct.aS()) {
                                            tcs.bj();
                                        } else if (task.isCancelled()) {
                                            tcs.bj();
                                        } else if (task.ba()) {
                                            tcs.h(task.bb());
                                        } else {
                                            tcs.setResult(task.getResult());
                                        }
                                        return null;
                                    }
                                });
                                return;
                            }
                        } catch (CancellationException e) {
                            tcs.bj();
                            return;
                        } catch (Exception e2) {
                            tcs.h(e2);
                            return;
                        }
                    }
                    tcs.bj();
                }
            });
        } catch (Exception e) {
            tcs.h(new ExecutorException(e));
        }
    }

    private void bg() {
        synchronized (this.lock) {
            for (g<TResult, ?> continuation : this.do) {
                try {
                    continuation.a(this);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            this.do = null;
        }
    }

    boolean bh() {
        boolean z = true;
        synchronized (this.lock) {
            if (this.complete) {
                z = false;
            } else {
                this.complete = true;
                this.cancelled = true;
                this.lock.notifyAll();
                bg();
            }
        }
        return z;
    }

    boolean c(TResult result) {
        boolean z = true;
        synchronized (this.lock) {
            if (this.complete) {
                z = false;
            } else {
                this.complete = true;
                this.result = result;
                this.lock.notifyAll();
                bg();
            }
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean g(java.lang.Exception r5) {
        /*
        r4 = this;
        r1 = 1;
        r0 = 0;
        r2 = r4.lock;
        monitor-enter(r2);
        r3 = r4.complete;	 Catch:{ all -> 0x002f }
        if (r3 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r2);	 Catch:{ all -> 0x002f }
    L_0x000a:
        return r0;
    L_0x000b:
        r0 = 1;
        r4.complete = r0;	 Catch:{ all -> 0x002f }
        r4.dk = r5;	 Catch:{ all -> 0x002f }
        r0 = 0;
        r4.dl = r0;	 Catch:{ all -> 0x002f }
        r0 = r4.lock;	 Catch:{ all -> 0x002f }
        r0.notifyAll();	 Catch:{ all -> 0x002f }
        r4.bg();	 Catch:{ all -> 0x002f }
        r0 = r4.dl;	 Catch:{ all -> 0x002f }
        if (r0 != 0) goto L_0x002c;
    L_0x001f:
        r0 = aY();	 Catch:{ all -> 0x002f }
        if (r0 == 0) goto L_0x002c;
    L_0x0025:
        r0 = new bolts.j;	 Catch:{ all -> 0x002f }
        r0.<init>(r4);	 Catch:{ all -> 0x002f }
        r4.dm = r0;	 Catch:{ all -> 0x002f }
    L_0x002c:
        monitor-exit(r2);	 Catch:{ all -> 0x002f }
        r0 = r1;
        goto L_0x000a;
    L_0x002f:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x002f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.h.g(java.lang.Exception):boolean");
    }
}
