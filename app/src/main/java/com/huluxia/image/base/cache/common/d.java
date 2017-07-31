package com.huluxia.image.base.cache.common;

import javax.annotation.Nullable;

/* compiled from: DebuggingCacheKey */
public class d extends h {
    private final Object ty;

    public d(String key, @Nullable Object callerContext) {
        super(key);
        this.ty = callerContext;
    }

    @Nullable
    public Object gk() {
        return this.ty;
    }
}
