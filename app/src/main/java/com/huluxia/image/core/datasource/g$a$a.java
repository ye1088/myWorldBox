package com.huluxia.image.core.datasource;

import com.huluxia.image.core.datasource.g.a;

/* compiled from: IncreasingQualityDataSourceSupplier */
class g$a$a implements e<T> {
    private int mIndex;
    final /* synthetic */ a zF;

    public g$a$a(a aVar, int index) {
        this.zF = aVar;
        this.mIndex = index;
    }

    public void onNewResult(c<T> dataSource) {
        if (dataSource.iN()) {
            a.a(this.zF, this.mIndex, dataSource);
        } else if (dataSource.isFinished()) {
            a.b(this.zF, this.mIndex, dataSource);
        }
    }

    public void onFailure(c<T> dataSource) {
        a.b(this.zF, this.mIndex, dataSource);
    }

    public void onCancellation(c<T> cVar) {
    }

    public void onProgressUpdate(c<T> dataSource) {
        if (this.mIndex == 0) {
            this.zF.d(dataSource.getProgress());
        }
    }
}
