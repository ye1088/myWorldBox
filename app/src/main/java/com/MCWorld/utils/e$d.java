package com.MCWorld.utils;

/* compiled from: Function */
public abstract class e$d<A> implements e$f<Boolean, A> {
    public abstract boolean pred(A a);

    public Boolean apply(A x) {
        return Boolean.valueOf(pred(x));
    }
}
