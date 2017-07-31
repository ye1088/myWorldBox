package com.huluxia.widget.menudrawer;

import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* compiled from: FloatScroller */
class b {
    private float bAE;
    private float bAF;
    private float bAG;
    private float bAH;
    private float bAI;
    private int mDuration;
    private boolean mFinished = true;
    private Interpolator mInterpolator;
    private long mStartTime;

    public b(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public final void forceFinished(boolean finished) {
        this.mFinished = finished;
    }

    public final int getDuration() {
        return this.mDuration;
    }

    public final float Pn() {
        return this.bAG;
    }

    public final float Po() {
        return this.bAE;
    }

    public final float Pp() {
        return this.bAF;
    }

    public boolean computeScrollOffset() {
        if (this.mFinished) {
            return false;
        }
        int timePassed = (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
        if (timePassed < this.mDuration) {
            this.bAG = this.bAE + (this.bAI * this.mInterpolator.getInterpolation(((float) timePassed) * this.bAH));
            return true;
        }
        this.bAG = this.bAF;
        this.mFinished = true;
        return true;
    }

    public void a(float start, float delta, int duration) {
        this.mFinished = false;
        this.mDuration = duration;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.bAE = start;
        this.bAF = start + delta;
        this.bAI = delta;
        this.bAH = 1.0f / ((float) this.mDuration);
    }

    public void abortAnimation() {
        this.bAG = this.bAF;
        this.mFinished = true;
    }

    public void extendDuration(int extend) {
        this.mDuration = timePassed() + extend;
        this.bAH = 1.0f / ((float) this.mDuration);
        this.mFinished = false;
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    }

    public void N(float newVal) {
        this.bAF = newVal;
        this.bAI = this.bAF - this.bAE;
        this.mFinished = false;
    }
}
