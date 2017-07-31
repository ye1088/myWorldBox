package com.huluxia.image.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;
import com.huluxia.image.drawee.drawable.o.c;

/* compiled from: ScalingUtils */
class o$i extends o$a {
    public static final c CM = new o$i();

    private o$i() {
    }

    public void a(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
        float scale = Math.min(scaleX, scaleY);
        float dx = (float) parentRect.left;
        float dy = (float) parentRect.top;
        outTransform.setScale(scale, scale);
        outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
    }
}
