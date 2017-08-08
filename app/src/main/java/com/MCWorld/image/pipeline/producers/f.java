package com.MCWorld.image.pipeline.producers;

import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.imagepipeline.cache.e;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.pipeline.cache.d;

/* compiled from: BitmapMemoryCacheGetProducer */
public class f extends h {
    public static final String Ji = "BitmapMemoryCacheGetProducer";

    public f(e<b, com.MCWorld.image.base.imagepipeline.image.b> memoryCache, d cacheKeyFactory, am<a<com.MCWorld.image.base.imagepipeline.image.b>> inputProducer) {
        super(memoryCache, cacheKeyFactory, inputProducer);
    }

    protected j<a<com.MCWorld.image.base.imagepipeline.image.b>> a(j<a<com.MCWorld.image.base.imagepipeline.image.b>> consumer, b cacheKey) {
        return consumer;
    }

    protected String oK() {
        return Ji;
    }
}
