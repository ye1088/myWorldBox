package com.huluxia.image.pipeline.nativecode;

import android.graphics.Bitmap;
import com.huluxia.framework.base.utils.DoNotStrip;
import com.huluxia.framework.base.utils.Preconditions;

@DoNotStrip
public class NativeBlurFilter {
    @DoNotStrip
    private static native void nativeIterativeBoxBlur(Bitmap bitmap, int i, int i2);

    static {
        a.load();
    }

    public static void a(Bitmap bitmap, int iterations, int blurRadius) {
        boolean z;
        boolean z2 = true;
        Preconditions.checkNotNull(bitmap);
        if (iterations > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (blurRadius <= 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        nativeIterativeBoxBlur(bitmap, iterations, blurRadius);
    }
}
