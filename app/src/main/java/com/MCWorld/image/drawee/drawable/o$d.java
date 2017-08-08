package com.MCWorld.image.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;
import com.MCWorld.image.drawee.drawable.o.c;

/* compiled from: ScalingUtils */
class o$d extends o$a {
    public static final c CM = new o$d();

    private o$d() {
    }

    public void a(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
        outTransform.setTranslate((float) ((int) ((((float) parentRect.left) + (((float) (parentRect.width() - childWidth)) * 0.5f)) + 0.5f)), (float) ((int) ((((float) parentRect.top) + (((float) (parentRect.height() - childHeight)) * 0.5f)) + 0.5f)));
    }
}
