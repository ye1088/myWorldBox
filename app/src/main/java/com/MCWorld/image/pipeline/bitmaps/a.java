package com.MCWorld.image.pipeline.bitmaps;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.MCWorld.image.pipeline.memory.d;
import com.MCWorld.image.pipeline.nativecode.Bitmaps;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(21)
@ThreadSafe
/* compiled from: ArtBitmapFactory */
public class a extends com.MCWorld.image.base.imagepipeline.bitmaps.a {
    private final d El;

    public a(d bitmapPool) {
        this.El = bitmapPool;
    }

    public com.MCWorld.image.core.common.references.a<Bitmap> b(int width, int height, Config bitmapConfig) {
        Bitmap bitmap = (Bitmap) this.El.get(com.MCWorld.image.base.imageutils.a.c(width, height, bitmapConfig));
        Bitmaps.a(bitmap, width, height, bitmapConfig);
        return com.MCWorld.image.core.common.references.a.a(bitmap, this.El);
    }
}
