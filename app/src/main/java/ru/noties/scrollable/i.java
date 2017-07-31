package ru.noties.scrollable;

import android.view.animation.Interpolator;
import com.nineoldandroids.animation.ObjectAnimator;

/* compiled from: InterpolatorCloseUpAnimatorConfigurator */
public class i implements d {
    private final Interpolator mInterpolator;

    public i(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void a(ObjectAnimator animator) {
        animator.setInterpolator(this.mInterpolator);
    }
}
