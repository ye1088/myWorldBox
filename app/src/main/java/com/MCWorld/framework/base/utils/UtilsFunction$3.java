package com.MCWorld.framework.base.utils;

class UtilsFunction$3 extends UtilsFunction$Pred<E> {
    final /* synthetic */ UtilsFunction$Eq val$cmp;
    final /* synthetic */ Object val$x;

    UtilsFunction$3(UtilsFunction$Eq utilsFunction$Eq, Object obj) {
        this.val$cmp = utilsFunction$Eq;
        this.val$x = obj;
    }

    public boolean pred(E y) {
        return this.val$cmp.eq(this.val$x, y);
    }
}
