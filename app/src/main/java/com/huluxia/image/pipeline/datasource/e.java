package com.huluxia.image.pipeline.datasource;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.core.datasource.AbstractDataSource;
import com.huluxia.image.core.datasource.c;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: ListDataSource */
public class e<T> extends AbstractDataSource<List<a<T>>> {
    private final c<a<T>>[] GM;
    @GuardedBy("this")
    private int GN = 0;

    @Nullable
    public /* synthetic */ Object getResult() {
        return nu();
    }

    protected e(c<a<T>>[] dataSources) {
        this.GM = dataSources;
    }

    public static <T> e<T> a(c<a<T>>... dataSources) {
        boolean z;
        int i = 0;
        Preconditions.checkNotNull(dataSources);
        if (dataSources.length > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z);
        e<T> listDataSource = new e(dataSources);
        int length = dataSources.length;
        while (i < length) {
            c<a<T>> dataSource = dataSources[i];
            if (dataSource != null) {
                listDataSource.getClass();
                dataSource.a(new a(listDataSource, null), com.huluxia.image.core.common.executors.a.ik());
            }
            i++;
        }
        return listDataSource;
    }

    @Nullable
    public synchronized List<a<T>> nu() {
        List<a<T>> results;
        if (iN()) {
            results = new ArrayList(this.GM.length);
            for (c<a<T>> dataSource : this.GM) {
                results.add(dataSource.getResult());
            }
        } else {
            results = null;
        }
        return results;
    }

    public synchronized boolean iN() {
        boolean z;
        z = !isClosed() && this.GN == this.GM.length;
        return z;
    }

    public boolean close() {
        int i = 0;
        if (!super.close()) {
            return false;
        }
        c[] cVarArr = this.GM;
        int length = cVarArr.length;
        while (i < length) {
            cVarArr[i].close();
            i++;
        }
        return true;
    }

    private void nv() {
        if (nw()) {
            b(null, true);
        }
    }

    private synchronized boolean nw() {
        int i;
        i = this.GN + 1;
        this.GN = i;
        return i == this.GM.length;
    }

    private void c(c<a<T>> dataSource) {
        b(dataSource.iP());
    }

    private void nx() {
        b(new CancellationException());
    }

    private void ny() {
        float progress = 0.0f;
        for (c<?> dataSource : this.GM) {
            progress += dataSource.getProgress();
        }
        d(progress / ((float) this.GM.length));
    }
}
