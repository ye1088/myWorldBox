package com.huluxia.image.pipeline.producers;

import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.imagepipeline.cache.e;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.pipeline.cache.d;

/* compiled from: BitmapMemoryCacheGetProducer */
public class f extends h {
    public static final String Ji = "BitmapMemoryCacheGetProducer";

    public f(e<b, com.huluxia.image.base.imagepipeline.image.b> memoryCache, d cacheKeyFactory, am<a<com.huluxia.image.base.imagepipeline.image.b>> inputProducer) {
        super(memoryCache, cacheKeyFactory, inputProducer);
    }

    protected j<a<com.huluxia.image.base.imagepipeline.image.b>> a(j<a<com.huluxia.image.base.imagepipeline.image.b>> consumer, b cacheKey) {
        return consumer;
    }

    protected String oK() {
        return Ji;
    }
}
