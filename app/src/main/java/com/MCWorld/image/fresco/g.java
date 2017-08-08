package com.MCWorld.image.fresco;

import android.content.res.Resources;
import com.MCWorld.framework.base.utils.ImmutableList;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.imagepipeline.animated.factory.a;
import com.MCWorld.image.base.imagepipeline.cache.e;
import com.MCWorld.image.core.datasource.c;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: PipelineDraweeControllerFactory */
public class g {
    private boolean DY;
    private a Ec;
    @Nullable
    private ImmutableList<a> Ed;
    private e<b, com.MCWorld.image.base.imagepipeline.image.b> Ee;
    private Executor Ek;
    private Resources mResources;
    private com.MCWorld.image.drawee.components.a zU;

    public g(Resources resources, com.MCWorld.image.drawee.components.a deferredReleaser, a animatedDrawableFactory, Executor uiThreadExecutor, e<b, com.MCWorld.image.base.imagepipeline.image.b> memoryCache, @Nullable ImmutableList<a> drawableFactories, boolean drawDebugOverlay) {
        this.mResources = resources;
        this.zU = deferredReleaser;
        this.Ec = animatedDrawableFactory;
        this.Ek = uiThreadExecutor;
        this.Ee = memoryCache;
        this.Ed = drawableFactories;
        this.DY = drawDebugOverlay;
    }

    public d b(Supplier<c<com.MCWorld.image.core.common.references.a<com.MCWorld.image.base.imagepipeline.image.b>>> dataSourceSupplier, String id, b cacheKey, Object callerContext) {
        d controller = new d(this.mResources, this.zU, this.Ec, this.Ek, this.Ee, dataSourceSupplier, id, cacheKey, callerContext, this.Ed);
        if (this.DY) {
            controller.af(true);
        }
        return controller;
    }
}
