package com.MCWorld.image.pipeline.datasource;

import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.core.datasource.AbstractDataSource;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: SettableDataSource */
public final class g<T> extends AbstractDataSource<a<T>> {
    @Nullable
    public /* synthetic */ Object getResult() {
        return nt();
    }

    protected /* synthetic */ void s(@Nullable Object obj) {
        h((a) obj);
    }

    public static <V> g<V> nA() {
        return new g();
    }

    private g() {
    }

    public boolean i(@Nullable a<T> valueRef) {
        return super.b(a.b((a) valueRef), true);
    }

    public boolean i(Throwable throwable) {
        return super.b(throwable);
    }

    public boolean d(float progress) {
        return super.d(progress);
    }

    @Nullable
    public a<T> nt() {
        return a.b((a) super.getResult());
    }

    protected void h(@Nullable a<T> result) {
        a.c(result);
    }
}
