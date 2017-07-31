package com.huluxia.widget.menudrawer;

import android.view.animation.Interpolator;

/* compiled from: PeekInterpolator */
class c implements Interpolator {
    private static final String TAG = "PeekInterpolator";
    private static final e bBQ = new e();

    c() {
    }

    public float getInterpolation(float input) {
        if (input < 0.33333334f) {
            return bBQ.getInterpolation(input * 3.0f);
        }
        if (input <= com.huluxia.image.base.imagepipeline.common.c.wt) {
            return 1.0f;
        }
        return 1.0f - bBQ.getInterpolation(((input + 0.33333334f) - 1.0f) * 3.0f);
    }
}
