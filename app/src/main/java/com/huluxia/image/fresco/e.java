package com.huluxia.image.fresco;

import android.content.Context;
import android.net.Uri;
import com.huluxia.image.base.imagepipeline.image.b;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.drawee.controller.AbstractDraweeControllerBuilder;
import com.huluxia.image.drawee.controller.AbstractDraweeControllerBuilder$CacheLevel;
import com.huluxia.image.drawee.controller.c;
import com.huluxia.image.drawee.interfaces.d;
import com.huluxia.image.pipeline.request.ImageRequest;
import com.huluxia.image.pipeline.request.ImageRequest$RequestLevel;
import com.huluxia.image.pipeline.request.ImageRequestBuilder;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: PipelineDraweeControllerBuilder */
public class e extends AbstractDraweeControllerBuilder<e, ImageRequest, a<b>, com.huluxia.image.base.imagepipeline.image.e> {
    private final com.huluxia.image.pipeline.core.e Eh;
    private final g Ei;

    public /* synthetic */ d bB(@Nullable String str) {
        return bC(str);
    }

    public /* synthetic */ d i(@Nullable Uri uri) {
        return j(uri);
    }

    protected /* synthetic */ com.huluxia.image.drawee.controller.a jF() {
        return lm();
    }

    protected /* synthetic */ AbstractDraweeControllerBuilder jG() {
        return ln();
    }

    public e(Context context, g pipelineDraweeControllerFactory, com.huluxia.image.pipeline.core.e imagePipeline, Set<c> boundControllerListeners) {
        super(context, boundControllerListeners);
        this.Eh = imagePipeline;
        this.Ei = pipelineDraweeControllerFactory;
    }

    public e j(@Nullable Uri uri) {
        if (uri == null) {
            return (e) super.D(null);
        }
        return (e) super.D(ImageRequestBuilder.w(uri).a(com.huluxia.image.base.imagepipeline.common.d.hC()).pM());
    }

    public e bC(@Nullable String uriString) {
        if (uriString == null || uriString.isEmpty()) {
            return (e) super.D(ImageRequest.bL(uriString));
        }
        return j(Uri.parse(uriString));
    }

    protected d lm() {
        com.huluxia.image.drawee.interfaces.a oldController = jA();
        if (!(oldController instanceof d)) {
            return this.Ei.b(jE(), jD(), gb(), gk());
        }
        d controller = (d) oldController;
        controller.a(jE(), jD(), gb(), gk());
        return controller;
    }

    private com.huluxia.image.base.cache.common.b gb() {
        ImageRequest imageRequest = (ImageRequest) js();
        com.huluxia.image.pipeline.cache.d cacheKeyFactory = this.Eh.lN();
        if (cacheKeyFactory == null || imageRequest == null) {
            return null;
        }
        if (imageRequest.pH() != null) {
            return cacheKeyFactory.b(imageRequest, gk());
        }
        return cacheKeyFactory.a(imageRequest, gk());
    }

    protected com.huluxia.image.core.datasource.c<a<b>> a(ImageRequest imageRequest, Object callerContext, AbstractDraweeControllerBuilder$CacheLevel cacheLevel) {
        return this.Eh.b(imageRequest, callerContext, a(cacheLevel));
    }

    protected e ln() {
        return this;
    }

    public static ImageRequest$RequestLevel a(AbstractDraweeControllerBuilder$CacheLevel cacheLevel) {
        switch (cacheLevel) {
            case FULL_FETCH:
                return ImageRequest$RequestLevel.FULL_FETCH;
            case DISK_CACHE:
                return ImageRequest$RequestLevel.DISK_CACHE;
            case BITMAP_MEMORY_CACHE:
                return ImageRequest$RequestLevel.BITMAP_MEMORY_CACHE;
            default:
                throw new RuntimeException("Cache level" + cacheLevel + "is not supported. ");
        }
    }
}
