package com.huluxia.image.pipeline.cache;

import bolts.g;
import bolts.h;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.pipeline.request.ImageRequest;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: SplitCachesByImageSizeDiskCachePolicy */
public class q implements h {
    private final c EW;
    private final c EX;
    private final d EY;
    private final int EZ;

    public q(c defaultBufferedDiskCache, c smallImageBufferedDiskCache, d cacheKeyFactory, int forceSmallCacheThresholdBytes) {
        this.EW = defaultBufferedDiskCache;
        this.EX = smallImageBufferedDiskCache;
        this.EY = cacheKeyFactory;
        this.EZ = forceSmallCacheThresholdBytes;
    }

    public h<d> a(ImageRequest imageRequest, Object callerContext, final AtomicBoolean isCancelled) {
        c firstCache;
        c secondCache;
        final b cacheKey = this.EY.c(imageRequest, callerContext);
        boolean alreadyInSmall = this.EX.l(cacheKey);
        boolean alreadyInMain = this.EW.l(cacheKey);
        if (alreadyInSmall || !alreadyInMain) {
            firstCache = this.EX;
            secondCache = this.EW;
        } else {
            firstCache = this.EW;
            secondCache = this.EX;
        }
        return firstCache.a(cacheKey, isCancelled).b(new g<d, h<d>>(this) {
            final /* synthetic */ q Fc;

            public /* synthetic */ Object a(h hVar) throws Exception {
                return c(hVar);
            }

            public h<d> c(h<d> task) throws Exception {
                if (q.e(task)) {
                    return task;
                }
                return (task.ba() || task.getResult() == null) ? secondCache.a(cacheKey, isCancelled) : task;
            }
        });
    }

    public void a(d newResult, ImageRequest imageRequest, Object callerContext) {
        b cacheKey = this.EY.c(imageRequest, callerContext);
        int size = newResult.getSize();
        if (size <= 0 || size >= this.EZ) {
            this.EW.a(cacheKey, newResult);
        } else {
            this.EX.a(cacheKey, newResult);
        }
    }

    private static boolean e(h<?> task) {
        return task.isCancelled() || (task.ba() && (task.bb() instanceof CancellationException));
    }
}
