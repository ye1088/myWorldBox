package com.MCWorld.image.pipeline.producers;

import com.MCWorld.framework.base.utils.ImmutableMap;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.cache.e;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.pipeline.cache.d;
import com.MCWorld.image.pipeline.request.ImageRequest;
import java.util.Map;

/* compiled from: PostprocessedBitmapMemoryCacheProducer */
public class ak implements am<com.MCWorld.image.core.common.references.a<b>> {
    public static final String Ji = "PostprocessedBitmapMemoryCacheProducer";
    @VisibleForTesting
    static final String La = "cached_value_found";
    private final d EY;
    private final e<com.MCWorld.image.base.cache.common.b, b> Ee;
    private final am<com.MCWorld.image.core.common.references.a<b>> IY;

    /* compiled from: PostprocessedBitmapMemoryCacheProducer */
    public static class a extends m<com.MCWorld.image.core.common.references.a<b>, com.MCWorld.image.core.common.references.a<b>> {
        private final e<com.MCWorld.image.base.cache.common.b, b> Ee;
        private final boolean Lb;
        private final com.MCWorld.image.base.cache.common.b uS;

        protected /* synthetic */ void d(Object obj, boolean z) {
            a((com.MCWorld.image.core.common.references.a) obj, z);
        }

        public a(j<com.MCWorld.image.core.common.references.a<b>> consumer, com.MCWorld.image.base.cache.common.b cacheKey, boolean isRepeatedProcessor, e<com.MCWorld.image.base.cache.common.b, b> memoryCache) {
            super(consumer);
            this.uS = cacheKey;
            this.Lb = isRepeatedProcessor;
            this.Ee = memoryCache;
        }

        protected void a(com.MCWorld.image.core.common.references.a<b> newResult, boolean isLast) {
            if (newResult == null) {
                if (isLast) {
                    oM().e(null, true);
                }
            } else if (isLast || this.Lb) {
                com.MCWorld.image.core.common.references.a<b> newCachedResult = this.Ee.a(this.uS, newResult);
                try {
                    oM().onProgressUpdate(1.0f);
                    j oM = oM();
                    if (newCachedResult != null) {
                        newResult = newCachedResult;
                    }
                    oM.e(newResult, isLast);
                } finally {
                    com.MCWorld.image.core.common.references.a.c(newCachedResult);
                }
            }
        }
    }

    public ak(e<com.MCWorld.image.base.cache.common.b, b> memoryCache, d cacheKeyFactory, am<com.MCWorld.image.core.common.references.a<b>> inputProducer) {
        this.Ee = memoryCache;
        this.EY = cacheKeyFactory;
        this.IY = inputProducer;
    }

    public void b(j<com.MCWorld.image.core.common.references.a<b>> consumer, ao producerContext) {
        Map map = null;
        aq listener = producerContext.oB();
        String requestId = producerContext.getId();
        ImageRequest imageRequest = producerContext.oA();
        Object callerContext = producerContext.gk();
        com.MCWorld.image.pipeline.request.d postprocessor = imageRequest.pH();
        if (postprocessor == null || postprocessor.oz() == null) {
            this.IY.b(consumer, producerContext);
            return;
        }
        listener.n(requestId, oK());
        com.MCWorld.image.base.cache.common.b cacheKey = this.EY.b(imageRequest, callerContext);
        com.MCWorld.image.core.common.references.a<b> cachedReference = this.Ee.m(cacheKey);
        if (cachedReference != null) {
            String oK = oK();
            if (listener.bE(requestId)) {
                map = ImmutableMap.of("cached_value_found", "true");
            }
            listener.c(requestId, oK, map);
            consumer.onProgressUpdate(1.0f);
            consumer.e(cachedReference, true);
            cachedReference.close();
            return;
        }
        j<com.MCWorld.image.core.common.references.a<b>> cachedConsumer = new a(consumer, cacheKey, postprocessor instanceof com.MCWorld.image.pipeline.request.e, this.Ee);
        oK = oK();
        if (listener.bE(requestId)) {
            map = ImmutableMap.of("cached_value_found", "false");
        }
        listener.c(requestId, oK, map);
        this.IY.b(cachedConsumer, producerContext);
    }

    protected String oK() {
        return Ji;
    }
}
