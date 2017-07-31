package com.huluxia.widget.menudrawer;

import android.view.animation.Interpolator;

/* compiled from: SmoothInterpolator */
class g implements Interpolator {
    g() {
    }

    public float getInterpolation(float t) {
        t -= 1.0f;
        return ((((t * t) * t) * t) * t) + 1.0f;
    }
}
