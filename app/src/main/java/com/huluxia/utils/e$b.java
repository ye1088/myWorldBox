package com.huluxia.utils;

/* compiled from: Function */
public abstract class e$b<A> implements e$a<Boolean, A, A> {
    public abstract boolean eq(A a, A a2);

    public Boolean apply(A x, A y) {
        return Boolean.valueOf(eq(x, y));
    }
}
