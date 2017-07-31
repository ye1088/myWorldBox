package com.huluxia.framework.base.utils;

class UtilsFunction$1 extends UtilsFunction$Pred<E> {
    final /* synthetic */ UtilsFunction$Pred val$p;

    UtilsFunction$1(UtilsFunction$Pred utilsFunction$Pred) {
        this.val$p = utilsFunction$Pred;
    }

    public boolean pred(E x) {
        return !this.val$p.pred(x);
    }
}
