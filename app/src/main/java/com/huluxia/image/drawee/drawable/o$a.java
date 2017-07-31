package com.huluxia.image.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;
import com.huluxia.image.drawee.drawable.o.c;

/* compiled from: ScalingUtils */
public abstract class o$a implements c {
    public abstract void a(Matrix matrix, Rect rect, int i, int i2, float f, float f2, float f3, float f4);

    public Matrix a(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY) {
        a(outTransform, parentRect, childWidth, childHeight, focusX, focusY, ((float) parentRect.width()) / ((float) childWidth), ((float) parentRect.height()) / ((float) childHeight));
        return outTransform;
    }
}
