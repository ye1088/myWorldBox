package com.huluxia.framework.base.utils;

public abstract class UtilsFunction$Pred<A> implements UtilsFunction$UnaryFunc<Boolean, A> {
    public abstract boolean pred(A a);

    public Boolean apply(A x) {
        return Boolean.valueOf(pred(x));
    }
}
