package com.MCWorld.image.drawee.drawable;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/* compiled from: DrawableProperties */
public class d {
    private static final int Bq = -1;
    private boolean Br = false;
    private ColorFilter Bs = null;
    private int Bt = -1;
    private int Bu = -1;
    private int mAlpha = -1;

    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.Bs = colorFilter;
        this.Br = true;
    }

    public void setDither(boolean dither) {
        this.Bt = dither ? 1 : 0;
    }

    public void setFilterBitmap(boolean filterBitmap) {
        this.Bu = filterBitmap ? 1 : 0;
    }

    public void e(Drawable drawable) {
        boolean z = true;
        if (drawable != null) {
            if (this.mAlpha != -1) {
                drawable.setAlpha(this.mAlpha);
            }
            if (this.Br) {
                drawable.setColorFilter(this.Bs);
            }
            if (this.Bt != -1) {
                boolean z2;
                if (this.Bt != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                drawable.setDither(z2);
            }
            if (this.Bu != -1) {
                if (this.Bu == 0) {
                    z = false;
                }
                drawable.setFilterBitmap(z);
            }
        }
    }
}
