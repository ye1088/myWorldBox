package com.MCWorld.image.pipeline.cache;

import bolts.h;

import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.MCWorld.image.pipeline.request.ImageRequest$CacheChoice;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: SmallCacheIfRequestedDiskCachePolicy */
public class p implements h {
    private final c EW;
    private final c EX;
    private final d EY;

    public p(c defaultBufferedDiskCache, c smallImageBufferedDiskCache, d cacheKeyFactory) {
        this.EW = defaultBufferedDiskCache;
        this.EX = smallImageBufferedDiskCache;
        this.EY = cacheKeyFactory;
    }

    public h<d> a(ImageRequest imageRequest, Object callerContext, AtomicBoolean isCancelled) {
        b cacheKey = this.EY.c(imageRequest, callerContext);
        if (imageRequest.pu() == ImageRequest$CacheChoice.SMALL) {
            return this.EX.a(cacheKey, isCancelled);
        }
        return this.EW.a(cacheKey, isCancelled);
    }

    public void a(d newResult, ImageRequest imageRequest, Object callerContext) {
        b cacheKey = this.EY.c(imageRequest, callerContext);
        if (imageRequest.pu() == ImageRequest$CacheChoice.SMALL) {
            this.EX.a(cacheKey, newResult);
        } else {
            this.EW.a(cacheKey, newResult);
        }
    }
}
