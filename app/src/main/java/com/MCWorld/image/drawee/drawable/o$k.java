package com.MCWorld.image.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;
import com.MCWorld.image.drawee.drawable.o.c;

/* compiled from: ScalingUtils */
class o$k extends o$a {
    public static final c CM = new o$k();

    private o$k() {
    }

    public void a(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
        float scale;
        float dx;
        float dy;
        if (scaleY > scaleX) {
            scale = scaleY;
            dx = ((float) parentRect.left) + Math.max(Math.min((((float) parentRect.width()) * 0.5f) - ((((float) childWidth) * scale) * focusX), 0.0f), ((float) parentRect.width()) - (((float) childWidth) * scale));
            dy = (float) parentRect.top;
        } else {
            scale = scaleX;
            dx = (float) parentRect.left;
            dy = ((float) parentRect.top) + Math.max(Math.min((((float) parentRect.height()) * 0.5f) - ((((float) childHeight) * scale) * focusY), 0.0f), ((float) parentRect.height()) - (((float) childHeight) * scale));
        }
        outTransform.setScale(scale, scale);
        outTransform.postTranslate((float) ((int) (0.5f + dx)), (float) ((int) (0.5f + dy)));
    }
}
