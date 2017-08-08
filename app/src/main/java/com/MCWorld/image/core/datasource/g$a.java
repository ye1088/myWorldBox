package com.MCWorld.image.core.datasource;

import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.core.common.executors.a;
import java.util.ArrayList;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: IncreasingQualityDataSourceSupplier */
class g$a extends AbstractDataSource<T> {
    @GuardedBy("IncreasingQualityDataSource.this")
    @Nullable
    private ArrayList<c<T>> zC;
    @GuardedBy("IncreasingQualityDataSource.this")
    private int zD;
    final /* synthetic */ g zE;

    public g$a(g gVar) {
        this.zE = gVar;
        int n = g.a(gVar).size();
        this.zD = n;
        this.zC = new ArrayList(n);
        int i = 0;
        while (i < n) {
            c<T> dataSource = (c) ((Supplier) g.a(gVar).get(i)).get();
            this.zC.add(dataSource);
            dataSource.a(new a(this, i), a.ik());
            if (!dataSource.iN()) {
                i++;
            } else {
                return;
            }
        }
    }

    @Nullable
    private synchronized c<T> bz(int i) {
        c<T> cVar;
        cVar = (this.zC == null || i >= this.zC.size()) ? null : (c) this.zC.get(i);
        return cVar;
    }

    @Nullable
    private synchronized c<T> bA(int i) {
        c<T> cVar = null;
        synchronized (this) {
            if (this.zC != null && i < this.zC.size()) {
                cVar = (c) this.zC.set(i, null);
            }
        }
        return cVar;
    }

    @Nullable
    private synchronized c<T> iV() {
        return bz(this.zD);
    }

    @Nullable
    public synchronized T getResult() {
        c<T> dataSourceWithResult;
        dataSourceWithResult = iV();
        return dataSourceWithResult != null ? dataSourceWithResult.getResult() : null;
    }

    public synchronized boolean iN() {
        boolean z;
        c<T> dataSourceWithResult = iV();
        z = dataSourceWithResult != null && dataSourceWithResult.iN();
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean close() {
        /*
        r3 = this;
        monitor-enter(r3);
        r2 = super.close();	 Catch:{ all -> 0x0025 }
        if (r2 != 0) goto L_0x000a;
    L_0x0007:
        r2 = 0;
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
    L_0x0009:
        return r2;
    L_0x000a:
        r0 = r3.zC;	 Catch:{ all -> 0x0025 }
        r2 = 0;
        r3.zC = r2;	 Catch:{ all -> 0x0025 }
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
        if (r0 == 0) goto L_0x0028;
    L_0x0012:
        r1 = 0;
    L_0x0013:
        r2 = r0.size();
        if (r1 >= r2) goto L_0x0028;
    L_0x0019:
        r2 = r0.get(r1);
        r2 = (com.huluxia.image.core.datasource.c) r2;
        r3.e(r2);
        r1 = r1 + 1;
        goto L_0x0013;
    L_0x0025:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
        throw r2;
    L_0x0028:
        r2 = 1;
        goto L_0x0009;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.core.datasource.g$a_isRightVersion.close():boolean");
    }

    private void a(int index, c<T> dataSource) {
        a(index, (c) dataSource, dataSource.isFinished());
        if (dataSource == iV()) {
            boolean z = index == 0 && dataSource.isFinished();
            b(null, z);
        }
    }

    private void b(int index, c<T> dataSource) {
        e(c(index, dataSource));
        if (index == 0) {
            b(dataSource.iP());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r5, com.MCWorld.image.core.datasource.c<T> r6, boolean r7) {
        /*
        r4 = this;
        monitor-enter(r4);
        r2 = r4.zD;	 Catch:{ all -> 0x002e }
        r1 = r4.zD;	 Catch:{ all -> 0x002e }
        r3 = r4.bz(r5);	 Catch:{ all -> 0x002e }
        if (r6 != r3) goto L_0x000f;
    L_0x000b:
        r3 = r4.zD;	 Catch:{ all -> 0x002e }
        if (r5 != r3) goto L_0x0011;
    L_0x000f:
        monitor-exit(r4);	 Catch:{ all -> 0x002e }
    L_0x0010:
        return;
    L_0x0011:
        r3 = r4.iV();	 Catch:{ all -> 0x002e }
        if (r3 == 0) goto L_0x001d;
    L_0x0017:
        if (r7 == 0) goto L_0x0020;
    L_0x0019:
        r3 = r4.zD;	 Catch:{ all -> 0x002e }
        if (r5 >= r3) goto L_0x0020;
    L_0x001d:
        r1 = r5;
        r4.zD = r5;	 Catch:{ all -> 0x002e }
    L_0x0020:
        monitor-exit(r4);	 Catch:{ all -> 0x002e }
        r0 = r2;
    L_0x0022:
        if (r0 <= r1) goto L_0x0010;
    L_0x0024:
        r3 = r4.bA(r0);
        r4.e(r3);
        r0 = r0 + -1;
        goto L_0x0022;
    L_0x002e:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x002e }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.core.datasource.g$a_isRightVersion.a_isRightVersion(int, com.huluxia.image.core.datasource.c, boolean):void");
    }

    @Nullable
    private synchronized c<T> c(int i, c<T> dataSource) {
        if (dataSource == iV()) {
            dataSource = null;
        } else if (dataSource == bz(i)) {
            dataSource = bA(i);
        }
        return dataSource;
    }

    private void e(c<T> dataSource) {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
