package com.huluxia.image.core.datasource;

import com.huluxia.framework.base.utils.Supplier;

/* compiled from: DataSources */
public class d {
    private d() {
    }

    public static <T> c<T> d(Throwable failure) {
        h<T> simpleDataSource = h.iW();
        simpleDataSource.b(failure);
        return simpleDataSource;
    }

    public static <T> c<T> t(T result) {
        h<T> simpleDataSource = h.iW();
        simpleDataSource.u(result);
        return simpleDataSource;
    }

    public static <T> Supplier<c<T>> e(final Throwable failure) {
        return new Supplier<c<T>>() {
            public c<T> get() {
                return d.d(failure);
            }
        };
    }
}
