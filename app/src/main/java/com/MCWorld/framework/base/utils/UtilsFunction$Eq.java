package com.MCWorld.framework.base.utils;

public abstract class UtilsFunction$Eq<A> implements UtilsFunction$BinaryFunc<Boolean, A, A> {
    public abstract boolean eq(A a, A a2);

    public Boolean apply(A x, A y) {
        return Boolean.valueOf(eq(x, y));
    }
}
