package com.huluxia.image.core.datasource;

import com.huluxia.image.core.datasource.f.a;

/* compiled from: FirstAvailableDataSourceSupplier */
class f$a$a implements e<T> {
    final /* synthetic */ a zB;

    private f$a$a(a aVar) {
        this.zB = aVar;
    }

    public void onFailure(c<T> dataSource) {
        a.a(this.zB, dataSource);
    }

    public void onCancellation(c<T> cVar) {
    }

    public void onNewResult(c<T> dataSource) {
        if (dataSource.iN()) {
            a.b(this.zB, dataSource);
        } else if (dataSource.isFinished()) {
            a.a(this.zB, dataSource);
        }
    }

    public void onProgressUpdate(c<T> dataSource) {
        this.zB.d(Math.max(this.zB.getProgress(), dataSource.getProgress()));
    }
}
