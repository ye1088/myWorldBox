package com.huluxia.image.fresco;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.ImmutableList;
import com.huluxia.framework.base.utils.Objects;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.base.imagepipeline.image.b;
import com.huluxia.image.base.imagepipeline.image.e;
import com.huluxia.image.core.datasource.c;
import com.huluxia.image.drawee.controller.a;
import java.util.Iterator;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: PipelineDraweeController */
public class d extends a<com.huluxia.image.core.common.references.a<b>, e> {
    private static final Class<?> tF = d.class;
    private Supplier<c<com.huluxia.image.core.common.references.a<b>>> Aq;
    private boolean DY;
    private final com.huluxia.image.base.imagepipeline.animated.factory.a Ec;
    @Nullable
    private final ImmutableList<a> Ed;
    @Nullable
    private com.huluxia.image.base.imagepipeline.cache.e<com.huluxia.image.base.cache.common.b, b> Ee;
    private final a Ef;
    private final Resources mResources;
    private com.huluxia.image.base.cache.common.b uS;

    protected /* synthetic */ int A(@Nullable Object obj) {
        return f((com.huluxia.image.core.common.references.a) obj);
    }

    protected /* synthetic */ void B(@Nullable Object obj) {
        g((com.huluxia.image.core.common.references.a) obj);
    }

    protected /* synthetic */ Object jq() {
        return ll();
    }

    protected /* synthetic */ Drawable x(Object obj) {
        return d((com.huluxia.image.core.common.references.a) obj);
    }

    protected /* synthetic */ Object y(Object obj) {
        return e((com.huluxia.image.core.common.references.a) obj);
    }

    public d(Resources resources, com.huluxia.image.drawee.components.a deferredReleaser, com.huluxia.image.base.imagepipeline.animated.factory.a animatedDrawableFactory, Executor uiThreadExecutor, com.huluxia.image.base.imagepipeline.cache.e<com.huluxia.image.base.cache.common.b, b> memoryCache, Supplier<c<com.huluxia.image.core.common.references.a<b>>> dataSourceSupplier, String id, com.huluxia.image.base.cache.common.b cacheKey, Object callerContext) {
        this(resources, deferredReleaser, animatedDrawableFactory, uiThreadExecutor, memoryCache, dataSourceSupplier, id, cacheKey, callerContext, null);
    }

    public d(Resources resources, com.huluxia.image.drawee.components.a deferredReleaser, com.huluxia.image.base.imagepipeline.animated.factory.a animatedDrawableFactory, Executor uiThreadExecutor, com.huluxia.image.base.imagepipeline.cache.e<com.huluxia.image.base.cache.common.b, b> memoryCache, Supplier<c<com.huluxia.image.core.common.references.a<b>>> dataSourceSupplier, String id, com.huluxia.image.base.cache.common.b cacheKey, Object callerContext, @Nullable ImmutableList<a> drawableFactories) {
        super(deferredReleaser, uiThreadExecutor, id, callerContext);
        this.Ef = new 1(this);
        this.mResources = resources;
        this.Ec = animatedDrawableFactory;
        this.Ee = memoryCache;
        this.uS = cacheKey;
        this.Ed = drawableFactories;
        c((Supplier) dataSourceSupplier);
    }

    public void a(Supplier<c<com.huluxia.image.core.common.references.a<b>>> dataSourceSupplier, String id, com.huluxia.image.base.cache.common.b cacheKey, Object callerContext) {
        super.g(id, callerContext);
        c((Supplier) dataSourceSupplier);
        this.uS = cacheKey;
    }

    public void af(boolean drawDebugOverlay) {
        this.DY = drawDebugOverlay;
    }

    private void c(Supplier<c<com.huluxia.image.core.common.references.a<b>>> dataSourceSupplier) {
        this.Aq = dataSourceSupplier;
        e(null);
    }

    protected Resources getResources() {
        return this.mResources;
    }

    protected c<com.huluxia.image.core.common.references.a<b>> jp() {
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x: getDataSource", new Object[]{Integer.valueOf(System.identityHashCode(this))}), new Object[0]);
        }
        return (c) this.Aq.get();
    }

    protected Drawable d(com.huluxia.image.core.common.references.a<b> image) {
        Preconditions.checkState(com.huluxia.image.core.common.references.a.a((com.huluxia.image.core.common.references.a) image));
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
                controllerOverlay = new com.huluxia.image.drawee.debug.a();
                b(controllerOverlay);
            }
            if (controllerOverlay instanceof com.huluxia.image.drawee.debug.a) {
                com.huluxia.image.drawee.debug.a debugOverlay = (com.huluxia.image.drawee.debug.a) controllerOverlay;
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

    protected e e(com.huluxia.image.core.common.references.a<b> image) {
        Preconditions.checkState(com.huluxia.image.core.common.references.a.a((com.huluxia.image.core.common.references.a) image));
        return (e) image.get();
    }

    protected int f(@Nullable com.huluxia.image.core.common.references.a<b> image) {
        return image != null ? image.iw() : 0;
    }

    protected void g(@Nullable com.huluxia.image.core.common.references.a<b> image) {
        com.huluxia.image.core.common.references.a.c(image);
    }

    protected void c(@Nullable Drawable drawable) {
        if (drawable instanceof com.huluxia.image.base.drawable.a) {
            ((com.huluxia.image.base.drawable.a) drawable).gZ();
        }
    }

    protected com.huluxia.image.core.common.references.a<b> ll() {
        if (this.Ee == null || this.uS == null) {
            return null;
        }
        com.huluxia.image.core.common.references.a<b> closeableImage = this.Ee.m(this.uS);
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
