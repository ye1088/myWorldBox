package com.MCWorld.image.core.datasource;

import com.MCWorld.framework.base.utils.Preconditions;

/* compiled from: SimpleDataSource */
public class h<T> extends AbstractDataSource<T> {
    private h() {
    }

    public static <T> h<T> iW() {
        return new h();
    }

    public boolean b(T value, boolean isLast) {
        return super.b(Preconditions.checkNotNull(value), isLast);
    }

    public boolean u(T value) {
        return super.b(Preconditions.checkNotNull(value), true);
    }

    public boolean b(Throwable throwable) {
        return super.b((Throwable) Preconditions.checkNotNull(throwable));
    }

    public boolean d(float progress) {
        return super.d(progress);
    }
}
