package com.huluxia.image.pipeline.producers;

import android.util.Pair;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.pipeline.cache.d;
import com.huluxia.image.pipeline.request.ImageRequest$RequestLevel;
import java.io.Closeable;

/* compiled from: BitmapMemoryCacheKeyMultiplexProducer */
public class g extends af<Pair<b, ImageRequest$RequestLevel>, a<com.huluxia.image.base.imagepipeline.image.b>> {
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

    public a<com.huluxia.image.base.imagepipeline.image.b> b(a<com.huluxia.image.base.imagepipeline.image.b> closeableImage) {
        return a.b(closeableImage);
    }
}
