package com.huluxia.image.core.datasource;

import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.core.common.executors.a;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: FirstAvailableDataSourceSupplier */
class f$a extends AbstractDataSource<T> {
    private int mIndex = 0;
    final /* synthetic */ f zA;
    private c<T> zy = null;
    private c<T> zz = null;

    public f$a(f fVar) {
        this.zA = fVar;
        if (!iT()) {
            b(new RuntimeException("No data source supplier or supplier returned null."));
        }
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

    public boolean close() {
        synchronized (this) {
            if (super.close()) {
                c<T> currentDataSource = this.zy;
                this.zy = null;
                c<T> dataSourceWithResult = this.zz;
                this.zz = null;
                e(dataSourceWithResult);
                e(currentDataSource);
                return true;
            }
            return false;
        }
    }

    private boolean iT() {
        c<T> dataSource;
        Supplier<c<T>> dataSourceSupplier = iU();
        if (dataSourceSupplier != null) {
            dataSource = (c) dataSourceSupplier.get();
        } else {
            dataSource = null;
        }
        if (!a(dataSource) || dataSource == null) {
            e(dataSource);
            return false;
        }
        dataSource.a(new a(this, null), a.ik());
        return true;
    }

    @Nullable
    private synchronized Supplier<c<T>> iU() {
        Supplier<c<T>> supplier;
        if (isClosed() || this.mIndex >= f.a(this.zA).size()) {
            supplier = null;
        } else {
            List a = f.a(this.zA);
            int i = this.mIndex;
            this.mIndex = i + 1;
            supplier = (Supplier) a.get(i);
        }
        return supplier;
    }

    private synchronized boolean a(c<T> dataSource) {
        boolean z;
        if (isClosed()) {
            z = false;
        } else {
            this.zy = dataSource;
            z = true;
        }
        return z;
    }

    private synchronized boolean b(c<T> dataSource) {
        boolean z;
        if (isClosed() || dataSource != this.zy) {
            z = false;
        } else {
            this.zy = null;
            z = true;
        }
        return z;
    }

    @Nullable
    private synchronized c<T> iV() {
        return this.zz;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.huluxia.image.core.datasource.c<T> r3, boolean r4) {
        /*
        r2 = this;
        r0 = 0;
        monitor-enter(r2);
        r1 = r2.zy;	 Catch:{ all -> 0x001b }
        if (r3 != r1) goto L_0x000a;
    L_0x0006:
        r1 = r2.zz;	 Catch:{ all -> 0x001b }
        if (r3 != r1) goto L_0x000c;
    L_0x000a:
        monitor-exit(r2);	 Catch:{ all -> 0x001b }
    L_0x000b:
        return;
    L_0x000c:
        r1 = r2.zz;	 Catch:{ all -> 0x001b }
        if (r1 == 0) goto L_0x0012;
    L_0x0010:
        if (r4 == 0) goto L_0x0016;
    L_0x0012:
        r0 = r2.zz;	 Catch:{ all -> 0x001b }
        r2.zz = r3;	 Catch:{ all -> 0x001b }
    L_0x0016:
        monitor-exit(r2);	 Catch:{ all -> 0x001b }
        r2.e(r0);
        goto L_0x000b;
    L_0x001b:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x001b }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.core.datasource.f$a_isRightVersion.a_isRightVersion(com.huluxia.image.core.datasource.c, boolean):void");
    }

    private void c(c<T> dataSource) {
        if (b(dataSource)) {
            if (dataSource != iV()) {
                e(dataSource);
            }
            if (!iT()) {
                b(dataSource.iP());
            }
        }
    }

    private void d(c<T> dataSource) {
        a((c) dataSource, dataSource.isFinished());
        if (dataSource == iV()) {
            b(null, dataSource.isFinished());
        }
    }

    private void e(c<T> dataSource) {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
