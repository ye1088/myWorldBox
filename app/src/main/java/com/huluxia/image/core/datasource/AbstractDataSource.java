package com.huluxia.image.core.datasource;

import android.util.Pair;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public abstract class AbstractDataSource<T> implements c<T> {
    @GuardedBy("this")
    @Nullable
    private T mResult = null;
    @GuardedBy("this")
    private boolean ym = false;
    @GuardedBy("this")
    private DataSourceStatus zm = DataSourceStatus.IN_PROGRESS;
    @GuardedBy("this")
    private Throwable zn = null;
    @GuardedBy("this")
    private float zo = 0.0f;
    private final ConcurrentLinkedQueue<Pair<e<T>, Executor>> zp = new ConcurrentLinkedQueue();

    protected AbstractDataSource() {
    }

    public synchronized boolean isClosed() {
        return this.ym;
    }

    public synchronized boolean isFinished() {
        return this.zm != DataSourceStatus.IN_PROGRESS;
    }

    public synchronized boolean iN() {
        return this.mResult != null;
    }

    @Nullable
    public synchronized T getResult() {
        return this.mResult;
    }

    public synchronized boolean iO() {
        return this.zm == DataSourceStatus.FAILURE;
    }

    @Nullable
    public synchronized Throwable iP() {
        return this.zn;
    }

    public synchronized float getProgress() {
        return this.zo;
    }

    public boolean close() {
        boolean z = true;
        synchronized (this) {
            if (this.ym) {
                z = false;
            } else {
                this.ym = true;
                T resultToClose = this.mResult;
                this.mResult = null;
                if (resultToClose != null) {
                    s(resultToClose);
                }
                if (!isFinished()) {
                    iQ();
                }
                synchronized (this) {
                    this.zp.clear();
                }
            }
        }
        return z;
    }

    protected void s(@Nullable T t) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.huluxia.image.core.datasource.e<T> r4, java.util.concurrent.Executor r5) {
        /*
        r3 = this;
        com.huluxia.framework.base.utils.Preconditions.checkNotNull(r4);
        com.huluxia.framework.base.utils.Preconditions.checkNotNull(r5);
        monitor-enter(r3);
        r1 = r3.ym;	 Catch:{ all -> 0x0040 }
        if (r1 == 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r3);	 Catch:{ all -> 0x0040 }
    L_0x000c:
        return;
    L_0x000d:
        r1 = r3.zm;	 Catch:{ all -> 0x0040 }
        r2 = com.huluxia.image.core.datasource.AbstractDataSource.DataSourceStatus.IN_PROGRESS;	 Catch:{ all -> 0x0040 }
        if (r1 != r2) goto L_0x001c;
    L_0x0013:
        r1 = r3.zp;	 Catch:{ all -> 0x0040 }
        r2 = android.util.Pair.create(r4, r5);	 Catch:{ all -> 0x0040 }
        r1.add(r2);	 Catch:{ all -> 0x0040 }
    L_0x001c:
        r1 = r3.iN();	 Catch:{ all -> 0x0040 }
        if (r1 != 0) goto L_0x002e;
    L_0x0022:
        r1 = r3.isFinished();	 Catch:{ all -> 0x0040 }
        if (r1 != 0) goto L_0x002e;
    L_0x0028:
        r1 = r3.iR();	 Catch:{ all -> 0x0040 }
        if (r1 == 0) goto L_0x003e;
    L_0x002e:
        r0 = 1;
    L_0x002f:
        monitor-exit(r3);	 Catch:{ all -> 0x0040 }
        if (r0 == 0) goto L_0x000c;
    L_0x0032:
        r1 = r3.iO();
        r2 = r3.iR();
        r3.a(r4, r5, r1, r2);
        goto L_0x000c;
    L_0x003e:
        r0 = 0;
        goto L_0x002f;
    L_0x0040:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0040 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.core.datasource.AbstractDataSource.a(com.huluxia.image.core.datasource.e, java.util.concurrent.Executor):void");
    }

    private void iQ() {
        boolean isFailure = iO();
        boolean isCancellation = iR();
        Iterator it = this.zp.iterator();
        while (it.hasNext()) {
            Pair<e<T>, Executor> pair = (Pair) it.next();
            a((e) pair.first, (Executor) pair.second, isFailure, isCancellation);
        }
    }

    private void a(e<T> dataSubscriber, Executor executor, boolean isFailure, boolean isCancellation) {
        executor.execute(new 1(this, isFailure, dataSubscriber, isCancellation));
    }

    private synchronized boolean iR() {
        boolean z;
        z = isClosed() && !isFinished();
        return z;
    }

    protected boolean b(@Nullable T value, boolean isLast) {
        boolean result = c(value, isLast);
        if (result) {
            iQ();
        }
        return result;
    }

    protected boolean b(Throwable throwable) {
        boolean result = c(throwable);
        if (result) {
            iQ();
        }
        return result;
    }

    protected boolean d(float progress) {
        boolean result = e(progress);
        if (result) {
            iS();
        }
        return result;
    }

    private boolean c(@Nullable T value, boolean isLast) {
        Object obj = null;
        try {
            boolean z;
            synchronized (this) {
                if (this.ym || this.zm != DataSourceStatus.IN_PROGRESS) {
                    obj = value;
                    z = false;
                } else {
                    if (isLast) {
                        this.zm = DataSourceStatus.SUCCESS;
                        this.zo = 1.0f;
                    }
                    if (this.mResult != value) {
                        obj = this.mResult;
                        this.mResult = value;
                    }
                    z = true;
                    if (obj != null) {
                        s(obj);
                    }
                }
            }
            return z;
        } finally {
            if (obj != null) {
                s(obj);
            }
        }
    }

    private synchronized boolean c(Throwable throwable) {
        boolean z;
        if (this.ym || this.zm != DataSourceStatus.IN_PROGRESS) {
            z = false;
        } else {
            this.zm = DataSourceStatus.FAILURE;
            this.zn = throwable;
            z = true;
        }
        return z;
    }

    private synchronized boolean e(float progress) {
        boolean z = false;
        synchronized (this) {
            if (!this.ym && this.zm == DataSourceStatus.IN_PROGRESS) {
                if (progress >= this.zo) {
                    this.zo = progress;
                    z = true;
                }
            }
        }
        return z;
    }

    protected void iS() {
        Iterator it = this.zp.iterator();
        while (it.hasNext()) {
            Pair<e<T>, Executor> pair = (Pair) it.next();
            pair.second.execute(new 2(this, pair.first));
        }
    }
}
