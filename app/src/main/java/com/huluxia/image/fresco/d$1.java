package com.huluxia.image.fresco;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.huluxia.image.base.imagepipeline.image.b;
import com.huluxia.image.base.imagepipeline.image.c;
import com.huluxia.image.drawee.drawable.i;

/* compiled from: PipelineDraweeController */
class d$1 implements a {
    final /* synthetic */ d Eg;

    d$1(d this$0) {
        this.Eg = this$0;
    }

    public boolean c(b image) {
        return true;
    }

    public Drawable d(b closeableImage) {
        if (closeableImage instanceof c) {
            c closeableStaticBitmap = (c) closeableImage;
            Drawable bitmapDrawable = new BitmapDrawable(d.a(this.Eg), closeableStaticBitmap.hM());
            if (closeableStaticBitmap.hQ() == 0 || closeableStaticBitmap.hQ() == -1) {
                return bitmapDrawable;
            }
            return new i(bitmapDrawable, closeableStaticBitmap.hQ());
        } else if (d.b(this.Eg) != null) {
            return d.b(this.Eg).a(closeableImage);
        } else {
            return null;
        }
    }
}
