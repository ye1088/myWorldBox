package com.huluxia.image.fresco;

import android.content.res.Resources;
import com.huluxia.framework.base.utils.ImmutableList;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.imagepipeline.animated.factory.a;
import com.huluxia.image.base.imagepipeline.cache.e;
import com.huluxia.image.core.datasource.c;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: PipelineDraweeControllerFactory */
public class g {
    private boolean DY;
    private a Ec;
    @Nullable
    private ImmutableList<a> Ed;
    private e<b, com.huluxia.image.base.imagepipeline.image.b> Ee;
    private Executor Ek;
    private Resources mResources;
    private com.huluxia.image.drawee.components.a zU;

    public g(Resources resources, com.huluxia.image.drawee.components.a deferredReleaser, a animatedDrawableFactory, Executor uiThreadExecutor, e<b, com.huluxia.image.base.imagepipeline.image.b> memoryCache, @Nullable ImmutableList<a> drawableFactories, boolean drawDebugOverlay) {
        this.mResources = resources;
        this.zU = deferredReleaser;
        this.Ec = animatedDrawableFactory;
        this.Ek = uiThreadExecutor;
        this.Ee = memoryCache;
        this.Ed = drawableFactories;
        this.DY = drawDebugOverlay;
    }

    public d b(Supplier<c<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>>> dataSourceSupplier, String id, b cacheKey, Object callerContext) {
        d controller = new d(this.mResources, this.zU, this.Ec, this.Ek, this.Ee, dataSourceSupplier, id, cacheKey, callerContext, this.Ed);
        if (this.DY) {
            controller.af(true);
        }
        return controller;
    }
}
