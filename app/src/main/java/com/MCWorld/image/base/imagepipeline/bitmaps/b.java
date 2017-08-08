package com.MCWorld.image.base.imagepipeline.bitmaps;

import android.graphics.Bitmap;
import com.MCWorld.image.core.common.references.c;

/* compiled from: SimpleBitmapReleaser */
public class b implements c<Bitmap> {
    private static b vI;

    public /* synthetic */ void release(Object obj) {
        c((Bitmap) obj);
    }

    public static b hf() {
        if (vI == null) {
            vI = new b();
        }
        return vI;
    }

    private b() {
    }

    public void c(Bitmap value) {
        value.recycle();
    }
}
