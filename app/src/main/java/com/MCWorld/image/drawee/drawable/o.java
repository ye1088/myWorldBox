package com.MCWorld.image.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.widget.ImageView.ScaleType;
import javax.annotation.Nullable;

/* compiled from: ScalingUtils */
public class o {

    /* compiled from: ScalingUtils */
    public interface c {
        public static final c CE = j.CM;
        public static final c CF = i.CM;
        public static final c CG = g.CM;
        public static final c CH = h.CM;
        public static final c CI = d.CM;
        public static final c CJ = f.CM;
        public static final c CK = e.CM;
        public static final c CL = k.CM;

        Matrix a(Matrix matrix, Rect rect, int i, int i2, float f, float f2);
    }

    /* compiled from: ScalingUtils */
    public static class b implements c, l {
        private final float[] CA;
        private final float[] CB;
        private final float[] CC;
        private float CD;
        private final c Cw;
        private final c Cx;
        @Nullable
        private final Rect Cy;
        @Nullable
        private final Rect Cz;

        public b(c scaleTypeFrom, c scaleTypeTo, @Nullable Rect boundsFrom, @Nullable Rect boundsTo) {
            this.CA = new float[9];
            this.CB = new float[9];
            this.CC = new float[9];
            this.Cw = scaleTypeFrom;
            this.Cx = scaleTypeTo;
            this.Cy = boundsFrom;
            this.Cz = boundsTo;
        }

        public b(c scaleTypeFrom, c scaleTypeTo) {
            this(scaleTypeFrom, scaleTypeTo, null, null);
        }

        public c kq() {
            return this.Cw;
        }

        public c kr() {
            return this.Cx;
        }

        @Nullable
        public Rect ks() {
            return this.Cy;
        }

        @Nullable
        public Rect kt() {
            return this.Cz;
        }

        public void h(float value) {
            this.CD = value;
        }

        public float ku() {
            return this.CD;
        }

        public Object getState() {
            return Float.valueOf(this.CD);
        }

        public Matrix a(Matrix transform, Rect parentBounds, int childWidth, int childHeight, float focusX, float focusY) {
            Rect boundsFrom;
            Rect boundsTo;
            if (this.Cy != null) {
                boundsFrom = this.Cy;
            } else {
                boundsFrom = parentBounds;
            }
            if (this.Cz != null) {
                boundsTo = this.Cz;
            } else {
                boundsTo = parentBounds;
            }
            this.Cw.a(transform, boundsFrom, childWidth, childHeight, focusX, focusY);
            transform.getValues(this.CA);
            this.Cx.a(transform, boundsTo, childWidth, childHeight, focusX, focusY);
            transform.getValues(this.CB);
            for (int i = 0; i < 9; i++) {
                this.CC[i] = (this.CA[i] * (1.0f - this.CD)) + (this.CB[i] * this.CD);
            }
            transform.setValues(this.CC);
            return transform;
        }
    }

    @Deprecated
    public static Matrix a(Matrix transform, Rect parentBounds, int childWidth, int childHeight, float focusX, float focusY, c scaleType) {
        return scaleType.a(transform, parentBounds, childWidth, childHeight, focusX, focusY);
    }

    public static c a(ScaleType scaleType) {
        if (scaleType == ScaleType.FIT_START) {
            return c.CF;
        }
        if (scaleType == ScaleType.FIT_CENTER) {
            return c.CG;
        }
        if (scaleType == ScaleType.FIT_END) {
            return c.CH;
        }
        if (scaleType == ScaleType.FIT_XY) {
            return c.CE;
        }
        if (scaleType == ScaleType.CENTER) {
            return c.CI;
        }
        if (scaleType == ScaleType.CENTER_CROP) {
            return c.CK;
        }
        if (scaleType == ScaleType.CENTER_INSIDE) {
            return c.CJ;
        }
        return c.CL;
    }
}
