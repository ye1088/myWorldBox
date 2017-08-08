package com.MCWorld.image.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;
import com.MCWorld.image.drawee.drawable.o.c;

/* compiled from: ScalingUtils */
class o$f extends o$a {
    public static final c CM = new o$f();

    private o$f() {
    }

    public void a(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
        float scale = Math.min(Math.min(scaleX, scaleY), 1.0f);
        float dx = ((float) parentRect.left) + ((((float) parentRect.width()) - (((float) childWidth) * scale)) * 0.5f);
        float dy = ((float) parentRect.top) + ((((float) parentRect.height()) - (((float) childHeight) * scale)) * 0.5f);
        outTransform.setScale(scale, scale);
        outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (dy + 0.5f)));
    }
}
