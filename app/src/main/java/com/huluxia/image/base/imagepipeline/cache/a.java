package com.huluxia.image.base.imagepipeline.cache;

import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.base.cache.common.b;

/* compiled from: BitmapCountingMemoryCacheFactory */
public class a {
    public static d<b, com.huluxia.image.base.imagepipeline.image.b> a(Supplier<f> bitmapMemoryCacheParamsSupplier, com.huluxia.image.core.common.memory.b memoryTrimmableRegistry, com.huluxia.image.base.imagepipeline.bitmaps.a platformBitmapFactory, boolean isExternalCreatedBitmapLogEnabled) {
        d<b, com.huluxia.image.base.imagepipeline.image.b> countingCache = new d(new g<com.huluxia.image.base.imagepipeline.image.b>() {
            public /* synthetic */ int j(Object obj) {
                return b((com.huluxia.image.base.imagepipeline.image.b) obj);
            }

            public int b(com.huluxia.image.base.imagepipeline.image.b value) {
                return value.hi();
            }
        }, new b(), bitmapMemoryCacheParamsSupplier, platformBitmapFactory, isExternalCreatedBitmapLogEnabled);
        memoryTrimmableRegistry.a(countingCache);
        return countingCache;
    }
}
