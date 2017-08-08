package com.MCWorld.image.pipeline.producers;

import com.MCWorld.framework.base.utils.ImmutableMap;
import com.MCWorld.image.base.imagepipeline.cache.e;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.base.imagepipeline.image.g;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.pipeline.cache.d;
import com.MCWorld.image.pipeline.request.ImageRequest$RequestLevel;
import java.util.Map;

/* compiled from: BitmapMemoryCacheProducer */
public class h implements am<a<b>> {
    public static final String Ji = "BitmapMemoryCacheProducer";
    public static final String Jj = "cached_value_found";
    private final d EY;
    private final e<com.MCWorld.image.base.cache.common.b, b> Ee;
    private final am<a<b>> IY;

    public h(e<com.MCWorld.image.base.cache.common.b, b> memoryCache, d cacheKeyFactory, am<a<b>> inputProducer) {
        this.Ee = memoryCache;
        this.EY = cacheKeyFactory;
        this.IY = inputProducer;
    }

    public void b(j<a<b>> consumer, ao producerContext) {
        Map of;
        Map map = null;
        aq listener = producerContext.oB();
        String requestId = producerContext.getId();
        listener.n(requestId, oK());
        com.MCWorld.image.base.cache.common.b cacheKey = this.EY.a(producerContext.oA(), producerContext.gk());
        a<b> cachedReference = this.Ee.m(cacheKey);
        if (cachedReference != null) {
            boolean isFinal = ((b) cachedReference.get()).hN().ib();
            if (isFinal) {
                String oK = oK();
                if (listener.bE(requestId)) {
                    of = ImmutableMap.of("cached_value_found", "true");
                } else {
                    of = null;
                }
                listener.c(requestId, oK, of);
                consumer.onProgressUpdate(1.0f);
            }
            consumer.e(cachedReference, isFinal);
            cachedReference.close();
            if (isFinal) {
                return;
            }
        }
        if (producerContext.oC().getValue() >= ImageRequest$RequestLevel.BITMAP_MEMORY_CACHE.getValue()) {
            oK = oK();
            if (listener.bE(requestId)) {
                of = ImmutableMap.of("cached_value_found", "false");
            } else {
                of = null;
            }
            listener.c(requestId, oK, of);
            consumer.e(null, true);
            return;
        }
        j<a<b>> wrappedConsumer = a(consumer, cacheKey);
        String oK2 = oK();
        if (listener.bE(requestId)) {
            map = ImmutableMap.of("cached_value_found", "false");
        }
        listener.c(requestId, oK2, map);
        this.IY.b(wrappedConsumer, producerContext);
    }

    protected j<a<b>> a(j<a<b>> consumer, final com.MCWorld.image.base.cache.common.b cacheKey) {
        return new m<a<b>, a<b>>(this, consumer) {
            final /* synthetic */ h Jk;

            public /* synthetic */ void d(Object obj, boolean z) {
                a((a) obj, z);
            }

            public void a(a<b> newResult, boolean isLast) {
                if (newResult == null) {
                    if (isLast) {
                        oM().e(null, true);
                    }
                } else if (((b) newResult.get()).isStateful()) {
                    oM().e(newResult, isLast);
                } else {
                    if (!isLast) {
                        a<b> currentCachedResult = this.Jk.Ee.m(cacheKey);
                        if (currentCachedResult != null) {
                            try {
                                g newInfo = ((b) newResult.get()).hN();
                                g cachedInfo = ((b) currentCachedResult.get()).hN();
                                if (cachedInfo.ib() || cachedInfo.getQuality() >= newInfo.getQuality()) {
                                    oM().e(currentCachedResult, false);
                                    return;
                                }
                                a.c(currentCachedResult);
                            } finally {
                                a.c(currentCachedResult);
                            }
                        }
                    }
                    a<b> newCachedResult = this.Jk.Ee.a(cacheKey, newResult);
                    if (isLast) {
                        try {
                            oM().onProgressUpdate(1.0f);
                        } catch (Throwable th) {
                            a.c(newCachedResult);
                        }
                    }
                    j oM = oM();
                    if (newCachedResult != null) {
                        newResult = newCachedResult;
                    }
                    oM.e(newResult, isLast);
                    a.c(newCachedResult);
                }
            }
        };
    }

    protected String oK() {
        return Ji;
    }
}
