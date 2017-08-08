package com.MCWorld.widget.menudrawer;

import android.view.animation.Interpolator;
import org.bytedeco.javacpp.avutil;

/* compiled from: SinusoidalInterpolator */
class e implements Interpolator {
    e() {
    }

    public float getInterpolation(float input) {
        return (float) ((Math.sin((((double) input) * avutil.M_PI) - avutil.M_PI_2) * 0.5d) + 0.5d);
    }
}
