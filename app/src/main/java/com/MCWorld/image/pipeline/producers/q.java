package com.MCWorld.image.pipeline.producers;

import android.util.Pair;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.pipeline.request.ImageRequest$RequestLevel;
import java.io.Closeable;

/* compiled from: EncodedCacheKeyMultiplexProducer */
public class q extends af<Pair<b, ImageRequest$RequestLevel>, d> {
    private final com.MCWorld.image.pipeline.cache.d EY;

    protected /* synthetic */ Object b(ao aoVar) {
        return a(aoVar);
    }

    public /* synthetic */ Closeable d(Closeable closeable) {
        return a((d) closeable);
    }

    public q(com.MCWorld.image.pipeline.cache.d cacheKeyFactory, am inputProducer) {
        super(inputProducer);
        this.EY = cacheKeyFactory;
    }

    protected Pair<b, ImageRequest$RequestLevel> a(ao producerContext) {
        return Pair.create(this.EY.c(producerContext.oA(), producerContext.gk()), producerContext.oC());
    }

    public d a(d encodedImage) {
        return d.a(encodedImage);
    }
}
