package com.huluxia.utils;

/* compiled from: UtilsFunction */
public abstract class ad$b<A> implements ad$a<Boolean, A, A> {
    public abstract boolean eq(A a, A a2);

    public Boolean apply(A x, A y) {
        return Boolean.valueOf(eq(x, y));
    }
}
