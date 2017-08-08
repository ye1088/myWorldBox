package com.MCWorld.widget.menudrawer;

import android.content.Context;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* compiled from: Scroller */
class d {
    private static final float DECELERATION_RATE = ((float) (Math.log(0.75d) / Math.log(0.9d)));
    private static final int DEFAULT_DURATION = 250;
    private static final float END_TENSION = 0.6f;
    private static final int FLING_MODE = 1;
    private static final int NB_SAMPLES = 100;
    private static final int SCROLL_MODE = 0;
    private static final float START_TENSION = 0.4f;
    private static final float bCc = 800.0f;
    private static final float[] bCd = new float[101];
    private static float sViscousFluidNormalize;
    private static float sViscousFluidScale = 8.0f;
    private float bAH;
    private float bAI;
    private int bBR;
    private int bBS;
    private int bBT;
    private int bBU;
    private int bBV;
    private int bBW;
    private int bBX;
    private int bBY;
    private int bBZ;
    private float bCa;
    private float bCb;
    private final float bCe;
    private int buo;
    private float mDeceleration;
    private int mDuration;
    private boolean mFinished;
    private boolean mFlywheel;
    private Interpolator mInterpolator;
    private int mMode;
    private long mStartTime;

    static {
        float xMin = 0.0f;
        for (int i = 0; i <= 100; i++) {
            float x;
            float coef;
            float t = ((float) i) / 100.0f;
            float xMax = 1.0f;
            while (true) {
                x = xMin + ((xMax - xMin) / 2.0f);
                coef = (3.0f * x) * (1.0f - x);
                float tx = ((((1.0f - x) * START_TENSION) + (END_TENSION * x)) * coef) + ((x * x) * x);
                if (((double) Math.abs(tx - t)) < 1.0E-5d) {
                    break;
                } else if (tx > t) {
                    xMax = x;
                } else {
                    xMin = x;
                }
            }
            bCd[i] = coef + ((x * x) * x);
        }
        bCd[100] = 1.0f;
        sViscousFluidNormalize = 1.0f;
        sViscousFluidNormalize = 1.0f / viscousFluid(1.0f);
    }

    public d(Context context) {
        this(context, null);
    }

    public d(Context context, Interpolator interpolator) {
        this(context, interpolator, context.getApplicationInfo().targetSdkVersion >= 11);
    }

    public d(Context context, Interpolator interpolator, boolean flywheel) {
        this.mFinished = true;
        this.mInterpolator = interpolator;
        this.bCe = context.getResources().getDisplayMetrics().density * 160.0f;
        this.mDeceleration = O(ViewConfiguration.getScrollFriction());
        this.mFlywheel = flywheel;
    }

    public final void setFriction(float friction) {
        this.mDeceleration = O(friction);
    }

    private float O(float friction) {
        return (386.0878f * this.bCe) * friction;
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

    public final int getCurrX() {
        return this.bBY;
    }

    public final int getCurrY() {
        return this.bBZ;
    }

    public float getCurrVelocity() {
        return this.bCb - ((this.mDeceleration * ((float) timePassed())) / 2000.0f);
    }

    public final int getStartX() {
        return this.bBR;
    }

    public final int getStartY() {
        return this.buo;
    }

    public final int getFinalX() {
        return this.bBS;
    }

    public final int getFinalY() {
        return this.bBT;
    }

    public boolean computeScrollOffset() {
        if (this.mFinished) {
            return false;
        }
        int timePassed = (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
        if (timePassed < this.mDuration) {
            switch (this.mMode) {
                case 0:
                    float x = ((float) timePassed) * this.bAH;
                    if (this.mInterpolator == null) {
                        x = viscousFluid(x);
                    } else {
                        x = this.mInterpolator.getInterpolation(x);
                    }
                    this.bBY = this.bBR + Math.round(this.bAI * x);
                    this.bBZ = this.buo + Math.round(this.bCa * x);
                    return true;
                case 1:
                    float t = ((float) timePassed) / ((float) this.mDuration);
                    int index = (int) (100.0f * t);
                    float tInf = ((float) index) / 100.0f;
                    float tSup = ((float) (index + 1)) / 100.0f;
                    float dInf = bCd[index];
                    float distanceCoef = dInf + (((t - tInf) / (tSup - tInf)) * (bCd[index + 1] - dInf));
                    this.bBY = this.bBR + Math.round(((float) (this.bBS - this.bBR)) * distanceCoef);
                    this.bBY = Math.min(this.bBY, this.bBV);
                    this.bBY = Math.max(this.bBY, this.bBU);
                    this.bBZ = this.buo + Math.round(((float) (this.bBT - this.buo)) * distanceCoef);
                    this.bBZ = Math.min(this.bBZ, this.bBX);
                    this.bBZ = Math.max(this.bBZ, this.bBW);
                    if (this.bBY != this.bBS || this.bBZ != this.bBT) {
                        return true;
                    }
                    this.mFinished = true;
                    return true;
                default:
                    return true;
            }
        }
        this.bBY = this.bBS;
        this.bBZ = this.bBT;
        this.mFinished = true;
        return true;
    }

    public void startScroll(int startX, int startY, int dx, int dy) {
        startScroll(startX, startY, dx, dy, 250);
    }

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        this.mMode = 0;
        this.mFinished = false;
        this.mDuration = duration;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.bBR = startX;
        this.buo = startY;
        this.bBS = startX + dx;
        this.bBT = startY + dy;
        this.bAI = (float) dx;
        this.bCa = (float) dy;
        this.bAH = 1.0f / ((float) this.mDuration);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        if (this.mFlywheel && !this.mFinished) {
            float oldVel = getCurrVelocity();
            float dx = (float) (this.bBS - this.bBR);
            float dy = (float) (this.bBT - this.buo);
            float hyp = (float) Math.sqrt((double) ((dx * dx) + (dy * dy)));
            float oldVelocityX = (dx / hyp) * oldVel;
            float oldVelocityY = (dy / hyp) * oldVel;
            if (Math.signum((float) velocityX) == Math.signum(oldVelocityX) && Math.signum((float) velocityY) == Math.signum(oldVelocityY)) {
                velocityX = (int) (((float) velocityX) + oldVelocityX);
                velocityY = (int) (((float) velocityY) + oldVelocityY);
            }
        }
        this.mMode = 1;
        this.mFinished = false;
        float velocity = (float) Math.sqrt((double) ((velocityX * velocityX) + (velocityY * velocityY)));
        this.bCb = velocity;
        double l = Math.log((double) ((START_TENSION * velocity) / bCc));
        this.mDuration = (int) (1000.0d * Math.exp(l / (((double) DECELERATION_RATE) - 1.0d)));
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.bBR = startX;
        this.buo = startY;
        float coeffX = velocity == 0.0f ? 1.0f : ((float) velocityX) / velocity;
        float coeffY = velocity == 0.0f ? 1.0f : ((float) velocityY) / velocity;
        int totalDistance = (int) (800.0d * Math.exp((((double) DECELERATION_RATE) / (((double) DECELERATION_RATE) - 1.0d)) * l));
        this.bBU = minX;
        this.bBV = maxX;
        this.bBW = minY;
        this.bBX = maxY;
        this.bBS = Math.round(((float) totalDistance) * coeffX) + startX;
        this.bBS = Math.min(this.bBS, this.bBV);
        this.bBS = Math.max(this.bBS, this.bBU);
        this.bBT = Math.round(((float) totalDistance) * coeffY) + startY;
        this.bBT = Math.min(this.bBT, this.bBX);
        this.bBT = Math.max(this.bBT, this.bBW);
    }

    static float viscousFluid(float x) {
        x *= sViscousFluidScale;
        if (x < 1.0f) {
            x -= 1.0f - ((float) Math.exp((double) (-x)));
        } else {
            x = 0.36787945f + ((1.0f - 0.36787945f) * (1.0f - ((float) Math.exp((double) (1.0f - x)))));
        }
        return x * sViscousFluidNormalize;
    }

    public void abortAnimation() {
        this.bBY = this.bBS;
        this.bBZ = this.bBT;
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

    public void setFinalX(int newX) {
        this.bBS = newX;
        this.bAI = (float) (this.bBS - this.bBR);
        this.mFinished = false;
    }

    public void setFinalY(int newY) {
        this.bBT = newY;
        this.bCa = (float) (this.bBT - this.buo);
        this.mFinished = false;
    }

    public boolean isScrollingInDirection(float xvel, float yvel) {
        return !this.mFinished && Math.signum(xvel) == Math.signum((float) (this.bBS - this.bBR)) && Math.signum(yvel) == Math.signum((float) (this.bBT - this.buo));
    }
}
