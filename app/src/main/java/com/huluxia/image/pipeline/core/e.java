package com.huluxia.image.pipeline.core;

import android.net.Uri;
import com.android.internal.util.Predicate;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.imagepipeline.common.Priority;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.core.common.util.f;
import com.huluxia.image.core.datasource.h;
import com.huluxia.image.pipeline.cache.c;
import com.huluxia.image.pipeline.cache.d;
import com.huluxia.image.pipeline.producers.am;
import com.huluxia.image.pipeline.producers.at;
import com.huluxia.image.pipeline.producers.aw;
import com.huluxia.image.pipeline.request.ImageRequest;
import com.huluxia.image.pipeline.request.ImageRequest.CacheChoice;
import com.huluxia.image.pipeline.request.ImageRequest.RequestLevel;
import com.huluxia.image.pipeline.request.ImageRequestBuilder;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: ImagePipeline */
public class e {
    private static final CancellationException Ff = new CancellationException("Prefetching is not enabled");
    private final c EX;
    private final d EY;
    private final j Fg;
    private final com.huluxia.image.pipeline.listener.c Fh;
    private final Supplier<Boolean> Fi;
    private final com.huluxia.image.base.imagepipeline.cache.e<b, com.huluxia.image.base.imagepipeline.image.b> Fj;
    private final com.huluxia.image.base.imagepipeline.cache.e<b, PooledByteBuffer> Fk;
    private final c Fl;
    private final aw Fm;
    private final Supplier<Boolean> Fn;
    private AtomicLong Fo = new AtomicLong();

    public e(j producerSequenceFactory, Set<com.huluxia.image.pipeline.listener.c> requestListeners, Supplier<Boolean> isPrefetchEnabledSupplier, com.huluxia.image.base.imagepipeline.cache.e<b, com.huluxia.image.base.imagepipeline.image.b> bitmapMemoryCache, com.huluxia.image.base.imagepipeline.cache.e<b, PooledByteBuffer> encodedMemoryCache, c mainBufferedDiskCache, c smallImageBufferedDiskCache, d cacheKeyFactory, aw threadHandoffProducerQueue, Supplier<Boolean> suppressBitmapPrefetchingSupplier) {
        this.Fg = producerSequenceFactory;
        this.Fh = new com.huluxia.image.pipeline.listener.b(requestListeners);
        this.Fi = isPrefetchEnabledSupplier;
        this.Fj = bitmapMemoryCache;
        this.Fk = encodedMemoryCache;
        this.Fl = mainBufferedDiskCache;
        this.EX = smallImageBufferedDiskCache;
        this.EY = cacheKeyFactory;
        this.Fm = threadHandoffProducerQueue;
        this.Fn = suppressBitmapPrefetchingSupplier;
    }

    private String lJ() {
        return String.valueOf(this.Fo.getAndIncrement());
    }

    @Deprecated
    public Supplier<com.huluxia.image.core.datasource.c<a<com.huluxia.image.base.imagepipeline.image.b>>> a(ImageRequest imageRequest, Object callerContext, boolean bitmapCacheOnly) {
        return a(imageRequest, callerContext, bitmapCacheOnly ? RequestLevel.BITMAP_MEMORY_CACHE : RequestLevel.FULL_FETCH);
    }

    public Supplier<com.huluxia.image.core.datasource.c<a<com.huluxia.image.base.imagepipeline.image.b>>> a(ImageRequest imageRequest, Object callerContext, RequestLevel requestLevel) {
        return new 1(this, imageRequest, callerContext, requestLevel);
    }

    public Supplier<com.huluxia.image.core.datasource.c<a<PooledByteBuffer>>> d(ImageRequest imageRequest, Object callerContext) {
        return new 2(this, imageRequest, callerContext);
    }

    public com.huluxia.image.core.datasource.c<a<com.huluxia.image.base.imagepipeline.image.b>> e(ImageRequest imageRequest, Object callerContext) {
        return b(imageRequest, callerContext, RequestLevel.BITMAP_MEMORY_CACHE);
    }

    public com.huluxia.image.core.datasource.c<a<com.huluxia.image.base.imagepipeline.image.b>> f(ImageRequest imageRequest, Object callerContext) {
        return b(imageRequest, callerContext, RequestLevel.FULL_FETCH);
    }

    public com.huluxia.image.core.datasource.c<a<com.huluxia.image.base.imagepipeline.image.b>> b(ImageRequest imageRequest, Object callerContext, RequestLevel lowestPermittedRequestLevelOnSubmit) {
        try {
            return a(this.Fg.i(imageRequest), imageRequest, lowestPermittedRequestLevelOnSubmit, callerContext);
        } catch (Exception exception) {
            return com.huluxia.image.core.datasource.d.d(exception);
        }
    }

    public com.huluxia.image.core.datasource.c<a<PooledByteBuffer>> g(ImageRequest imageRequest, Object callerContext) {
        Preconditions.checkNotNull(imageRequest.pv());
        try {
            am<a<PooledByteBuffer>> producerSequence = this.Fg.f(imageRequest);
            if (imageRequest.pz() != null) {
                imageRequest = ImageRequestBuilder.r(imageRequest).c(null).pM();
            }
            return a(producerSequence, imageRequest, RequestLevel.FULL_FETCH, callerContext);
        } catch (Exception exception) {
            return com.huluxia.image.core.datasource.d.d(exception);
        }
    }

    public com.huluxia.image.core.datasource.c<Void> h(ImageRequest imageRequest, Object callerContext) {
        if (!((Boolean) this.Fi.get()).booleanValue()) {
            return com.huluxia.image.core.datasource.d.d(Ff);
        }
        try {
            am<Void> producerSequence;
            if (((Boolean) this.Fn.get()).booleanValue()) {
                producerSequence = this.Fg.g(imageRequest);
            } else {
                producerSequence = this.Fg.j(imageRequest);
            }
            return a(producerSequence, imageRequest, RequestLevel.FULL_FETCH, callerContext, Priority.MEDIUM);
        } catch (Exception exception) {
            return com.huluxia.image.core.datasource.d.d(exception);
        }
    }

    public com.huluxia.image.core.datasource.c<Void> i(ImageRequest imageRequest, Object callerContext) {
        return a(imageRequest, callerContext, Priority.MEDIUM);
    }

    public com.huluxia.image.core.datasource.c<Void> a(ImageRequest imageRequest, Object callerContext, Priority priority) {
        if (!((Boolean) this.Fi.get()).booleanValue()) {
            return com.huluxia.image.core.datasource.d.d(Ff);
        }
        try {
            return a(this.Fg.g(imageRequest), imageRequest, RequestLevel.FULL_FETCH, callerContext, priority);
        } catch (Exception exception) {
            return com.huluxia.image.core.datasource.d.d(exception);
        }
    }

    public void l(Uri uri) {
        Predicate<b> predicate = r(uri);
        this.Fj.c(predicate);
        this.Fk.c(predicate);
    }

    public void m(Uri uri) {
        a(ImageRequest.v(uri));
    }

    public void a(ImageRequest imageRequest) {
        b cacheKey = this.EY.c(imageRequest, null);
        this.Fl.q(cacheKey);
        this.EX.q(cacheKey);
    }

    public void n(Uri uri) {
        l(uri);
        m(uri);
    }

    public void lK() {
        Predicate<b> allPredicate = new 3(this);
        this.Fj.c(allPredicate);
        this.Fk.c(allPredicate);
    }

    public void lL() {
        this.Fl.lt();
        this.EX.lt();
    }

    public void clearCaches() {
        lK();
        lL();
    }

    public boolean o(Uri uri) {
        if (uri == null) {
            return false;
        }
        return this.Fj.d(r(uri));
    }

    public com.huluxia.image.base.imagepipeline.cache.e<b, com.huluxia.image.base.imagepipeline.image.b> lM() {
        return this.Fj;
    }

    public boolean b(ImageRequest imageRequest) {
        if (imageRequest == null) {
            return false;
        }
        a ref = this.Fj.m(this.EY.a(imageRequest, null));
        try {
            boolean a = a.a(ref);
            return a;
        } finally {
            a.c(ref);
        }
    }

    public boolean p(Uri uri) {
        return a(uri, CacheChoice.SMALL) || a(uri, CacheChoice.DEFAULT);
    }

    public boolean a(Uri uri, CacheChoice cacheChoice) {
        return c(ImageRequestBuilder.w(uri).a(cacheChoice).pM());
    }

    public boolean c(ImageRequest imageRequest) {
        b cacheKey = this.EY.c(imageRequest, null);
        switch (7.Ft[imageRequest.pu().ordinal()]) {
            case 1:
                return this.Fl.o(cacheKey);
            case 2:
                return this.EX.o(cacheKey);
            default:
                return false;
        }
    }

    public com.huluxia.image.core.datasource.c<Boolean> q(Uri uri) {
        return d(ImageRequest.v(uri));
    }

    public com.huluxia.image.core.datasource.c<Boolean> d(ImageRequest imageRequest) {
        b cacheKey = this.EY.c(imageRequest, null);
        h<Boolean> dataSource = h.iW();
        this.Fl.m(cacheKey).b(new 5(this, cacheKey)).a(new 4(this, dataSource));
        return dataSource;
    }

    private <T> com.huluxia.image.core.datasource.c<a<T>> a(am<a<T>> producerSequence, ImageRequest imageRequest, RequestLevel lowestPermittedRequestLevelOnSubmit, Object callerContext) {
        boolean z = false;
        com.huluxia.image.pipeline.listener.c requestListener = e(imageRequest);
        try {
            RequestLevel lowestPermittedRequestLevel = RequestLevel.getMax(imageRequest.oC(), lowestPermittedRequestLevelOnSubmit);
            String lJ = lJ();
            if (!(!imageRequest.pD() && imageRequest.pw() == null && f.isNetworkUri(imageRequest.pv()))) {
                z = true;
            }
            return com.huluxia.image.pipeline.datasource.d.a(producerSequence, new at(imageRequest, lJ, requestListener, callerContext, lowestPermittedRequestLevel, false, z, imageRequest.oE()), requestListener);
        } catch (Exception exception) {
            return com.huluxia.image.core.datasource.d.d(exception);
        }
    }

    private com.huluxia.image.core.datasource.c<Void> a(am<Void> producerSequence, ImageRequest imageRequest, RequestLevel lowestPermittedRequestLevelOnSubmit, Object callerContext, Priority priority) {
        com.huluxia.image.pipeline.listener.c requestListener = e(imageRequest);
        try {
            return com.huluxia.image.pipeline.datasource.f.a(producerSequence, new at(imageRequest, lJ(), requestListener, callerContext, RequestLevel.getMax(imageRequest.oC(), lowestPermittedRequestLevelOnSubmit), true, false, priority), requestListener);
        } catch (Exception exception) {
            return com.huluxia.image.core.datasource.d.d(exception);
        }
    }

    private com.huluxia.image.pipeline.listener.c e(ImageRequest imageRequest) {
        if (imageRequest.pI() == null) {
            return this.Fh;
        }
        return new com.huluxia.image.pipeline.listener.b(new com.huluxia.image.pipeline.listener.c[]{this.Fh, imageRequest.pI()});
    }

    private Predicate<b> r(Uri uri) {
        return new 6(this, uri);
    }

    public void pause() {
        this.Fm.pn();
    }

    public void resume() {
        this.Fm.po();
    }

    public boolean isPaused() {
        return this.Fm.pq();
    }

    public d lN() {
        return this.EY;
    }
}
