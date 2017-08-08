package com.MCWorld.image.pipeline.request;

import android.graphics.Bitmap;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.core.common.references.a;
import javax.annotation.Nullable;

/* compiled from: Postprocessor */
public interface d {
    a<Bitmap> a(Bitmap bitmap, com.MCWorld.image.base.imagepipeline.bitmaps.a aVar);

    String getName();

    @Nullable
    b oz();
}
