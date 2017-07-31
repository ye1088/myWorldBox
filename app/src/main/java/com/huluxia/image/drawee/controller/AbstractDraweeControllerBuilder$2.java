package com.huluxia.image.drawee.controller;

import com.huluxia.framework.base.utils.Objects;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.core.datasource.c;
import com.tencent.open.SocialConstants;

class AbstractDraweeControllerBuilder$2 implements Supplier<c<IMAGE>> {
    final /* synthetic */ Object Au;
    final /* synthetic */ Object Av;
    final /* synthetic */ AbstractDraweeControllerBuilder Aw;
    final /* synthetic */ AbstractDraweeControllerBuilder$CacheLevel val$cacheLevel;

    AbstractDraweeControllerBuilder$2(AbstractDraweeControllerBuilder this$0, Object obj, Object obj2, AbstractDraweeControllerBuilder$CacheLevel abstractDraweeControllerBuilder$CacheLevel) {
        this.Aw = this$0;
        this.Au = obj;
        this.Av = obj2;
        this.val$cacheLevel = abstractDraweeControllerBuilder$CacheLevel;
    }

    public c<IMAGE> get() {
        return this.Aw.a(this.Au, this.Av, this.val$cacheLevel);
    }

    public String toString() {
        return Objects.toStringHelper(this).add(SocialConstants.TYPE_REQUEST, this.Au.toString()).toString();
    }
}
