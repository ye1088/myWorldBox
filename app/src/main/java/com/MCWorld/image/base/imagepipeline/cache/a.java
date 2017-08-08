package com.MCWorld.image.base.imagepipeline.cache;

import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.cache.common.b;

/* compiled from: BitmapCountingMemoryCacheFactory */
public class a {
    public static d<b, com.MCWorld.image.base.imagepipeline.image.b> a(Supplier<f> bitmapMemoryCacheParamsSupplier, com.MCWorld.image.core.common.memory.b memoryTrimmableRegistry, com.MCWorld.image.base.imagepipeline.bitmaps.a platformBitmapFactory, boolean isExternalCreatedBitmapLogEnabled) {
        d<b, com.MCWorld.image.base.imagepipeline.image.b> countingCache = new d(new g<com.MCWorld.image.base.imagepipeline.image.b>() {
            public /* synthetic */ int j(Object obj) {
                return b((com.MCWorld.image.base.imagepipeline.image.b) obj);
            }

            public int b(com.MCWorld.image.base.imagepipeline.image.b value) {
                return value.hi();
            }
        }, new b(), bitmapMemoryCacheParamsSupplier, platformBitmapFactory, isExternalCreatedBitmapLogEnabled);
        memoryTrimmableRegistry.a(countingCache);
        return countingCache;
    }
}
