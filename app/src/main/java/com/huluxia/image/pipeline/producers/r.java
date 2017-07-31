package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.utils.ImmutableMap;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.imagepipeline.cache.e;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.pipeline.request.ImageRequest$RequestLevel;
import java.util.Map;

/* compiled from: EncodedMemoryCacheProducer */
public class r implements am<d> {
    public static final String Ji = "EncodedMemoryCacheProducer";
    public static final String Jj = "cached_value_found";
    private final com.huluxia.image.pipeline.cache.d EY;
    private final e<b, PooledByteBuffer> Ee;
    private final am<d> IY;

    /* compiled from: EncodedMemoryCacheProducer */
    private static class a extends m<d, d> {
        private final e<b, PooledByteBuffer> Ee;
        private final b JN;

        public /* synthetic */ void d(Object obj, boolean z) {
            a((d) obj, z);
        }

        public a(j<d> consumer, e<b, PooledByteBuffer> memoryCache, b requestedCacheKey) {
            super(consumer);
            this.Ee = memoryCache;
            this.JN = requestedCacheKey;
        }

        public void a(d newResult, boolean isLast) {
            if (!isLast || newResult == null) {
                oM().e(newResult, isLast);
                return;
            }
            com.huluxia.image.core.common.references.a<PooledByteBuffer> ref = newResult.hS();
            if (ref != null) {
                try {
                    com.huluxia.image.core.common.references.a<PooledByteBuffer> cachedResult = this.Ee.a(newResult.hV() != null ? newResult.hV() : this.JN, ref);
                    if (cachedResult != null) {
                        try {
                            d cachedEncodedImage = new d(cachedResult);
                            cachedEncodedImage.b(newResult);
                            try {
                                oM().onProgressUpdate(1.0f);
                                oM().e(cachedEncodedImage, true);
                                return;
                            } finally {
                                d.d(cachedEncodedImage);
                            }
                        } finally {
                            com.huluxia.image.core.common.references.a.c(cachedResult);
                        }
                    }
                } finally {
                    com.huluxia.image.core.common.references.a.c(ref);
                }
            }
            oM().e(newResult, true);
        }
    }

    public r(e<b, PooledByteBuffer> memoryCache, com.huluxia.image.pipeline.cache.d cacheKeyFactory, am<d> inputProducer) {
        this.Ee = memoryCache;
        this.EY = cacheKeyFactory;
        this.IY = inputProducer;
    }

    public void b(j<d> consumer, ao producerContext) {
        Map map = null;
        String requestId = producerContext.getId();
        aq listener = producerContext.oB();
        listener.n(requestId, Ji);
        b cacheKey = this.EY.c(producerContext.oA(), producerContext.gk());
        com.huluxia.image.core.common.references.a<PooledByteBuffer> cachedReference = this.Ee.m(cacheKey);
        String str;
        if (cachedReference != null) {
            d cachedEncodedImage;
            try {
                cachedEncodedImage = new d(cachedReference);
                cachedEncodedImage.j(cacheKey);
                str = Ji;
                if (listener.bE(requestId)) {
                    map = ImmutableMap.of("cached_value_found", "true");
                }
                listener.c(requestId, str, map);
                consumer.onProgressUpdate(1.0f);
                consumer.e(cachedEncodedImage, true);
                d.d(cachedEncodedImage);
                com.huluxia.image.core.common.references.a.c(cachedReference);
            } catch (Throwable th) {
                com.huluxia.image.core.common.references.a.c(cachedReference);
            }
        } else if (producerContext.oC().getValue() >= ImageRequest$RequestLevel.ENCODED_MEMORY_CACHE.getValue()) {
            str = Ji;
            if (listener.bE(requestId)) {
                map = ImmutableMap.of("cached_value_found", "false");
            }
            listener.c(requestId, str, map);
            consumer.e(null, true);
            com.huluxia.image.core.common.references.a.c(cachedReference);
        } else {
            j consumerOfInputProducer = new a(consumer, this.Ee, cacheKey);
            str = Ji;
            if (listener.bE(requestId)) {
                map = ImmutableMap.of("cached_value_found", "false");
            }
            listener.c(requestId, str, map);
            this.IY.b(consumerOfInputProducer, producerContext);
            com.huluxia.image.core.common.references.a.c(cachedReference);
        }
    }
}
