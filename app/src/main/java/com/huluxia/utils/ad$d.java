package com.huluxia.utils;

/* compiled from: UtilsFunction */
public abstract class ad$d<A> implements ad$f<Boolean, A> {
    public abstract boolean pred(A a);

    public Boolean apply(A x) {
        return Boolean.valueOf(pred(x));
    }
}
