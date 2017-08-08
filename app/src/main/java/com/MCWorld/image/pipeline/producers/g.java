package com.MCWorld.image.pipeline.producers;

import android.util.Pair;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.pipeline.cache.d;
import com.MCWorld.image.pipeline.request.ImageRequest$RequestLevel;
import java.io.Closeable;

/* compiled from: BitmapMemoryCacheKeyMultiplexProducer */
public class g extends af<Pair<b, ImageRequest$RequestLevel>, a<com.MCWorld.image.base.imagepipeline.image.b>> {
    private final d EY;

    protected /* synthetic */ Object b(ao aoVar) {
        return a(aoVar);
    }

    public /* synthetic */ Closeable d(Closeable closeable) {
        return b((a) closeable);
    }

    public g(d cacheKeyFactory, am inputProducer) {
        super(inputProducer);
        this.EY = cacheKeyFactory;
    }

    protected Pair<b, ImageRequest$RequestLevel> a(ao producerContext) {
        return Pair.create(this.EY.a(producerContext.oA(), producerContext.gk()), producerContext.oC());
    }

    public a<com.MCWorld.image.base.imagepipeline.image.b> b(a<com.MCWorld.image.base.imagepipeline.image.b> closeableImage) {
        return a.b(closeableImage);
    }
}
