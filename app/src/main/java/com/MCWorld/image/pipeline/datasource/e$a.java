package com.MCWorld.image.pipeline.datasource;

import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.core.datasource.c;
import com.MCWorld.image.core.datasource.e;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: ListDataSource */
class e$a implements e<a<T>> {
    final /* synthetic */ e GO;
    @GuardedBy("InternalDataSubscriber.this")
    boolean mFinished;

    private e$a(e eVar) {
        this.GO = eVar;
        this.mFinished = false;
    }

    private synchronized boolean nz() {
        boolean z = true;
        synchronized (this) {
            if (this.mFinished) {
                z = false;
            } else {
                this.mFinished = true;
            }
        }
        return z;
    }

    public void onFailure(c<a<T>> dataSource) {
        e.a(this.GO, dataSource);
    }

    public void onCancellation(c<a<T>> cVar) {
        e.a(this.GO);
    }

    public void onNewResult(c<a<T>> dataSource) {
        if (dataSource.isFinished() && nz()) {
            e.b(this.GO);
        }
    }

    public void onProgressUpdate(c<a<T>> cVar) {
        e.c(this.GO);
    }
}
