package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.utils.ImmutableMap;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.imagepipeline.cache.e;
import com.huluxia.image.base.imagepipeline.image.b;
import com.huluxia.image.pipeline.cache.d;
import com.huluxia.image.pipeline.request.ImageRequest;
import java.util.Map;

/* compiled from: PostprocessedBitmapMemoryCacheProducer */
public class ak implements am<com.huluxia.image.core.common.references.a<b>> {
    public static final String Ji = "PostprocessedBitmapMemoryCacheProducer";
    @VisibleForTesting
    static final String La = "cached_value_found";
    private final d EY;
    private final e<com.huluxia.image.base.cache.common.b, b> Ee;
    private final am<com.huluxia.image.core.common.references.a<b>> IY;

    /* compiled from: PostprocessedBitmapMemoryCacheProducer */
    public static class a extends m<com.huluxia.image.core.common.references.a<b>, com.huluxia.image.core.common.references.a<b>> {
        private final e<com.huluxia.image.base.cache.common.b, b> Ee;
        private final boolean Lb;
        private final com.huluxia.image.base.cache.common.b uS;

        protected /* synthetic */ void d(Object obj, boolean z) {
            a((com.huluxia.image.core.common.references.a) obj, z);
        }

        public a(j<com.huluxia.image.core.common.references.a<b>> consumer, com.huluxia.image.base.cache.common.b cacheKey, boolean isRepeatedProcessor, e<com.huluxia.image.base.cache.common.b, b> memoryCache) {
            super(consumer);
            this.uS = cacheKey;
            this.Lb = isRepeatedProcessor;
            this.Ee = memoryCache;
        }

        protected void a(com.huluxia.image.core.common.references.a<b> newResult, boolean isLast) {
            if (newResult == null) {
                if (isLast) {
                    oM().e(null, true);
                }
            } else if (isLast || this.Lb) {
                com.huluxia.image.core.common.references.a<b> newCachedResult = this.Ee.a(this.uS, newResult);
                try {
                    oM().onProgressUpdate(1.0f);
                    j oM = oM();
                    if (newCachedResult != null) {
                        newResult = newCachedResult;
                    }
                    oM.e(newResult, isLast);
                } finally {
                    com.huluxia.image.core.common.references.a.c(newCachedResult);
                }
            }
        }
    }

    public ak(e<com.huluxia.image.base.cache.common.b, b> memoryCache, d cacheKeyFactory, am<com.huluxia.image.core.common.references.a<b>> inputProducer) {
        this.Ee = memoryCache;
        this.EY = cacheKeyFactory;
        this.IY = inputProducer;
    }

    public void b(j<com.huluxia.image.core.common.references.a<b>> consumer, ao producerContext) {
        Map map = null;
        aq listener = producerContext.oB();
        String requestId = producerContext.getId();
        ImageRequest imageRequest = producerContext.oA();
        Object callerContext = producerContext.gk();
        com.huluxia.image.pipeline.request.d postprocessor = imageRequest.pH();
        if (postprocessor == null || postprocessor.oz() == null) {
            this.IY.b(consumer, producerContext);
            return;
        }
        listener.n(requestId, oK());
        com.huluxia.image.base.cache.common.b cacheKey = this.EY.b(imageRequest, callerContext);
        com.huluxia.image.core.common.references.a<b> cachedReference = this.Ee.m(cacheKey);
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
        j<com.huluxia.image.core.common.references.a<b>> cachedConsumer = new a(consumer, cacheKey, postprocessor instanceof com.huluxia.image.pipeline.request.e, this.Ee);
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
