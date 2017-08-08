package com.MCWorld.image.pipeline.postprocessors;

import android.graphics.Bitmap;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.cache.common.h;
import com.MCWorld.image.pipeline.nativecode.NativeBlurFilter;
import java.util.Locale;
import javax.annotation.Nullable;

/* compiled from: IterativeBoxBlurPostProcessor */
public class a extends com.MCWorld.image.pipeline.request.a {
    private static final int IV = 3;
    private final int IW;
    private final int IX;
    private b uS;

    public a(int blurRadius) {
        this(3, blurRadius);
    }

    public a(int iterations, int blurRadius) {
        boolean z;
        boolean z2 = true;
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
        this.IW = iterations;
        this.IX = blurRadius;
    }

    public void n(Bitmap bitmap) {
        NativeBlurFilter.a(bitmap, this.IW, this.IX);
    }

    @Nullable
    public b oz() {
        if (this.uS == null) {
            this.uS = new h(String.format((Locale) null, "i%dr%d", new Object[]{Integer.valueOf(this.IW), Integer.valueOf(this.IX)}));
        }
        return this.uS;
    }
}
