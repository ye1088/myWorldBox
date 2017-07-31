package com.huluxia.image.pipeline.request;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.pipeline.nativecode.Bitmaps;
import javax.annotation.Nullable;

/* compiled from: BasePostprocessor */
public abstract class a implements d {
    public static final Config Mk = Config.ARGB_8888;

    public String getName() {
        return "Unknown postprocessor";
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap sourceBitmap, com.huluxia.image.base.imagepipeline.bitmaps.a bitmapFactory) {
        Config sourceBitmapConfig = sourceBitmap.getConfig();
        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();
        if (sourceBitmapConfig == null) {
            sourceBitmapConfig = Mk;
        }
        com.huluxia.image.core.common.references.a destBitmapRef = bitmapFactory.b(width, height, sourceBitmapConfig);
        try {
            b((Bitmap) destBitmapRef.get(), sourceBitmap);
            com.huluxia.image.core.common.references.a<Bitmap> b = com.huluxia.image.core.common.references.a.b(destBitmapRef);
            return b;
        } finally {
            com.huluxia.image.core.common.references.a.c(destBitmapRef);
        }
    }

    public void b(Bitmap destBitmap, Bitmap sourceBitmap) {
        c(destBitmap, sourceBitmap);
        n(destBitmap);
    }

    public void n(Bitmap bitmap) {
    }

    @Nullable
    public b oz() {
        return null;
    }

    private static void c(Bitmap destBitmap, Bitmap sourceBitmap) {
        if (destBitmap.getConfig() == sourceBitmap.getConfig()) {
            Bitmaps.a(destBitmap, sourceBitmap);
        } else {
            new Canvas(destBitmap).drawBitmap(sourceBitmap, 0.0f, 0.0f, null);
        }
    }
}
