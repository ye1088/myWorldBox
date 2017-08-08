package com.MCWorld.image.fresco;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.ImmutableList;
import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.base.imagepipeline.image.e;
import com.MCWorld.image.core.datasource.c;
import com.MCWorld.image.drawee.controller.a;
import java.util.Iterator;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: PipelineDraweeController */
public class d extends a<com.MCWorld.image.core.common.references.a<b>, e> {
    private static final Class<?> tF = d.class;
    private Supplier<c<com.MCWorld.image.core.common.references.a<b>>> Aq;
    private boolean DY;
    private final com.MCWorld.image.base.imagepipeline.animated.factory.a Ec;
    @Nullable
    private final ImmutableList<a> Ed;
    @Nullable
    private com.MCWorld.image.base.imagepipeline.cache.e<com.MCWorld.image.base.cache.common.b, b> Ee;
    private final a Ef;
    private final Resources mResources;
    private com.MCWorld.image.base.cache.common.b uS;

    protected /* synthetic */ int A(@Nullable Object obj) {
        return f((com.MCWorld.image.core.common.references.a) obj);
    }

    protected /* synthetic */ void B(@Nullable Object obj) {
        g((com.MCWorld.image.core.common.references.a) obj);
    }

    protected /* synthetic */ Object jq() {
        return ll();
    }

    protected /* synthetic */ Drawable x(Object obj) {
        return d((com.MCWorld.image.core.common.references.a) obj);
    }

    protected /* synthetic */ Object y(Object obj) {
        return e((com.MCWorld.image.core.common.references.a) obj);
    }

    public d(Resources resources, com.MCWorld.image.drawee.components.a deferredReleaser, com.MCWorld.image.base.imagepipeline.animated.factory.a animatedDrawableFactory, Executor uiThreadExecutor, com.MCWorld.image.base.imagepipeline.cache.e<com.MCWorld.image.base.cache.common.b, b> memoryCache, Supplier<c<com.MCWorld.image.core.common.references.a<b>>> dataSourceSupplier, String id, com.MCWorld.image.base.cache.common.b cacheKey, Object callerContext) {
        this(resources, deferredReleaser, animatedDrawableFactory, uiThreadExecutor, memoryCache, dataSourceSupplier, id, cacheKey, callerContext, null);
    }

    public d(Resources resources, com.MCWorld.image.drawee.components.a deferredReleaser, com.MCWorld.image.base.imagepipeline.animated.factory.a animatedDrawableFactory, Executor uiThreadExecutor, com.MCWorld.image.base.imagepipeline.cache.e<com.MCWorld.image.base.cache.common.b, b> memoryCache, Supplier<c<com.MCWorld.image.core.common.references.a<b>>> dataSourceSupplier, String id, com.MCWorld.image.base.cache.common.b cacheKey, Object callerContext, @Nullable ImmutableList<a> drawableFactories) {
        super(deferredReleaser, uiThreadExecutor, id, callerContext);
        this.Ef = new 1(this);
        this.mResources = resources;
        this.Ec = animatedDrawableFactory;
        this.Ee = memoryCache;
        this.uS = cacheKey;
        this.Ed = drawableFactories;
        c((Supplier) dataSourceSupplier);
    }

    public void a(Supplier<c<com.MCWorld.image.core.common.references.a<b>>> dataSourceSupplier, String id, com.MCWorld.image.base.cache.common.b cacheKey, Object callerContext) {
        super.g(id, callerContext);
        c((Supplier) dataSourceSupplier);
        this.uS = cacheKey;
    }

    public void af(boolean drawDebugOverlay) {
        this.DY = drawDebugOverlay;
    }

    private void c(Supplier<c<com.MCWorld.image.core.common.references.a<b>>> dataSourceSupplier) {
        this.Aq = dataSourceSupplier;
        e(null);
    }

    protected Resources getResources() {
        return this.mResources;
    }

    protected c<com.MCWorld.image.core.common.references.a<b>> jp() {
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x: getDataSource", new Object[]{Integer.valueOf(System.identityHashCode(this))}), new Object[0]);
        }
        return (c) this.Aq.get();
    }

    protected Drawable d(com.MCWorld.image.core.common.references.a<b> image) {
        Preconditions.checkState(com.MCWorld.image.core.common.references.a.a((com.MCWorld.image.core.common.references.a) image));
        b closeableImage = (b) image.get();
        e(closeableImage);
        if (this.Ed != null) {
            Iterator it = this.Ed.iterator();
            while (it.hasNext()) {
                a factory = (a) it.next();
                if (factory.c(closeableImage)) {
                    Drawable drawable = factory.d(closeableImage);
                    if (drawable != null) {
                        return drawable;
                    }
                }
            }
        }
        Drawable defaultDrawable = this.Ef.d(closeableImage);
        if (defaultDrawable != null) {
            return defaultDrawable;
        }
        throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
    }

    private void e(@Nullable b image) {
        if (this.DY) {
            Drawable controllerOverlay = jk();
            if (controllerOverlay == null) {
                controllerOverlay = new com.MCWorld.image.drawee.debug.a();
                b(controllerOverlay);
            }
            if (controllerOverlay instanceof com.MCWorld.image.drawee.debug.a) {
                com.MCWorld.image.drawee.debug.a debugOverlay = (com.MCWorld.image.drawee.debug.a) controllerOverlay;
                debugOverlay.bz(getId());
                if (image != null) {
                    debugOverlay.o(image.getWidth(), image.getHeight());
                    debugOverlay.bD(image.hi());
                    return;
                }
                debugOverlay.reset();
            }
        }
    }

    protected e e(com.MCWorld.image.core.common.references.a<b> image) {
        Preconditions.checkState(com.MCWorld.image.core.common.references.a.a((com.MCWorld.image.core.common.references.a) image));
        return (e) image.get();
    }

    protected int f(@Nullable com.MCWorld.image.core.common.references.a<b> image) {
        return image != null ? image.iw() : 0;
    }

    protected void g(@Nullable com.MCWorld.image.core.common.references.a<b> image) {
        com.MCWorld.image.core.common.references.a.c(image);
    }

    protected void c(@Nullable Drawable drawable) {
        if (drawable instanceof com.MCWorld.image.base.drawable.a) {
            ((com.MCWorld.image.base.drawable.a) drawable).gZ();
        }
    }

    protected com.MCWorld.image.core.common.references.a<b> ll() {
        if (this.Ee == null || this.uS == null) {
            return null;
        }
        com.MCWorld.image.core.common.references.a<b> closeableImage = this.Ee.m(this.uS);
        if (closeableImage == null || ((b) closeableImage.get()).hN().ib()) {
            return closeableImage;
        }
        closeableImage.close();
        return null;
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("super", super.toString()).add("dataSourceSupplier", this.Aq).toString();
    }
}
