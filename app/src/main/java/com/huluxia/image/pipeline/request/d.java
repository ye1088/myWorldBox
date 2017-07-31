package com.huluxia.image.pipeline.request;

import android.graphics.Bitmap;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.core.common.references.a;
import javax.annotation.Nullable;

/* compiled from: Postprocessor */
public interface d {
    a<Bitmap> a(Bitmap bitmap, com.huluxia.image.base.imagepipeline.bitmaps.a aVar);

    String getName();

    @Nullable
    b oz();
}
