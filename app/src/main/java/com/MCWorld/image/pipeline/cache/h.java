package com.MCWorld.image.pipeline.cache;

import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.pipeline.request.ImageRequest;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: DiskCachePolicy */
public interface h {
    bolts.h<d> a(ImageRequest imageRequest, Object obj, AtomicBoolean atomicBoolean);

    void a(d dVar, ImageRequest imageRequest, Object obj);
}
