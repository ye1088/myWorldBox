package com.MCWorld.image.pipeline.cache;

import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.imagepipeline.cache.d;
import com.MCWorld.image.base.imagepipeline.cache.e;

/* compiled from: BitmapMemoryCacheFactory */
public class a {
    public static e<b, com.MCWorld.image.base.imagepipeline.image.b> a(d<b, com.MCWorld.image.base.imagepipeline.image.b> bitmapCountingMemoryCache, final k imageCacheStatsTracker) {
        imageCacheStatsTracker.a(bitmapCountingMemoryCache);
        return new l(bitmapCountingMemoryCache, new m<b>() {
            public /* synthetic */ void H(Object obj) {
                k((b) obj);
            }

            public void k(b cacheKey) {
                imageCacheStatsTracker.s(cacheKey);
            }

            public void lp() {
                imageCacheStatsTracker.lz();
            }

            public void lq() {
                imageCacheStatsTracker.ly();
            }
        });
    }
}
