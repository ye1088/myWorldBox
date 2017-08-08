package com.MCWorld.image.pipeline.cache;

import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.imagepipeline.bitmaps.a;
import com.MCWorld.image.base.imagepipeline.cache.d;
import com.MCWorld.image.base.imagepipeline.cache.f;
import com.MCWorld.image.base.imagepipeline.cache.g;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;

/* compiled from: EncodedCountingMemoryCacheFactory */
public class i {
    public static d<b, PooledByteBuffer> a(Supplier<f> encodedMemoryCacheParamsSupplier, com.MCWorld.image.core.common.memory.b memoryTrimmableRegistry, a platformBitmapFactory) {
        d<b, PooledByteBuffer> countingCache = new d(new g<PooledByteBuffer>() {
            public /* synthetic */ int j(Object obj) {
                return a((PooledByteBuffer) obj);
            }

            public int a(PooledByteBuffer value) {
                return value.size();
            }
        }, new n(), encodedMemoryCacheParamsSupplier, platformBitmapFactory, false);
        memoryTrimmableRegistry.a(countingCache);
        return countingCache;
    }
}
