package com.huluxia.image.pipeline.cache;

import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.imagepipeline.cache.d;
import com.huluxia.image.base.imagepipeline.cache.e;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;

/* compiled from: EncodedMemoryCacheFactory */
public class j {
    public static e<b, PooledByteBuffer> a(d<b, PooledByteBuffer> encodedCountingMemoryCache, final k imageCacheStatsTracker) {
        imageCacheStatsTracker.b(encodedCountingMemoryCache);
        return new l(encodedCountingMemoryCache, new m<b>() {
            public /* synthetic */ void H(Object obj) {
                k((b) obj);
            }

            public void k(b cacheKey) {
                imageCacheStatsTracker.t(cacheKey);
            }

            public void lp() {
                imageCacheStatsTracker.lB();
            }

            public void lq() {
                imageCacheStatsTracker.lA();
            }
        });
    }
}
