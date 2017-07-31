package com.huluxia.framework.base.widget.hlistview;

import android.content.Context;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;

class OverScroller$SplineOverScroller {
    private static final int BALLISTIC = 2;
    private static final int CUBIC = 1;
    private static float DECELERATION_RATE = ((float) (Math.log(0.78d) / Math.log(0.9d)));
    private static final float END_TENSION = 1.0f;
    private static final float GRAVITY = 2000.0f;
    private static final float INFLEXION = 0.35f;
    private static final int NB_SAMPLES = 100;
    private static final float P1 = 0.175f;
    private static final float P2 = 0.35000002f;
    private static final int SPLINE = 0;
    private static final float[] SPLINE_POSITION = new float[101];
    private static final float[] SPLINE_TIME = new float[101];
    private static final float START_TENSION = 0.5f;
    private float mCurrVelocity;
    private int mCurrentPosition;
    private float mDeceleration;
    private int mDuration;
    private int mFinal;
    private boolean mFinished = true;
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    private int mOver;
    private float mPhysicalCoeff;
    private int mSplineDistance;
    private int mSplineDuration;
    private int mStart;
    private long mStartTime;
    private int mState = 0;
    private int mVelocity;

    static {
        float x_min = 0.0f;
        float y_min = 0.0f;
        for (int i = 0; i < 100; i++) {
            float x;
            float coef;
            float y;
            float alpha = ((float) i) / 100.0f;
            float x_max = END_TENSION;
            while (true) {
                x = x_min + ((x_max - x_min) / 2.0f);
                coef = (3.0f * x) * (END_TENSION - x);
                float tx = ((((END_TENSION - x) * P1) + (P2 * x)) * coef) + ((x * x) * x);
                if (((double) Math.abs(tx - alpha)) < 1.0E-5d) {
                    break;
                } else if (tx > alpha) {
                    x_max = x;
                } else {
                    x_min = x;
                }
            }
            SPLINE_POSITION[i] = ((((END_TENSION - x) * 0.5f) + x) * coef) + ((x * x) * x);
            float y_max = END_TENSION;
            while (true) {
                y = y_min + ((y_max - y_min) / 2.0f);
                coef = (3.0f * y) * (END_TENSION - y);
                float dy = ((((END_TENSION - y) * 0.5f) + y) * coef) + ((y * y) * y);
                if (((double) Math.abs(dy - alpha)) < 1.0E-5d) {
                    break;
                } else if (dy > alpha) {
                    y_max = y;
                } else {
                    y_min = y;
                }
            }
            SPLINE_TIME[i] = ((((END_TENSION - y) * P1) + (P2 * y)) * coef) + ((y * y) * y);
        }
        float[] fArr = SPLINE_POSITION;
        SPLINE_TIME[100] = END_TENSION;
        fArr[100] = END_TENSION;
    }

    void setFriction(float friction) {
        this.mFlingFriction = friction;
    }

    OverScroller$SplineOverScroller(Context context) {
        this.mPhysicalCoeff = (386.0878f * (context.getResources().getDisplayMetrics().density * 160.0f)) * 0.84f;
    }

    void updateScroll(float q) {
        this.mCurrentPosition = this.mStart + Math.round(((float) (this.mFinal - this.mStart)) * q);
    }

    private static float getDeceleration(int velocity) {
        return velocity > 0 ? -2000.0f : GRAVITY;
    }

    private void adjustDuration(int start, int oldFinal, int newFinal) {
        float x = Math.abs(((float) (newFinal - start)) / ((float) (oldFinal - start)));
        int index = (int) (100.0f * x);
        if (index < 100) {
            float x_inf = ((float) index) / 100.0f;
            float x_sup = ((float) (index + 1)) / 100.0f;
            float t_inf = SPLINE_TIME[index];
            this.mDuration = (int) (((float) this.mDuration) * (t_inf + (((x - x_inf) / (x_sup - x_inf)) * (SPLINE_TIME[index + 1] - t_inf))));
        }
    }

    void startScroll(int start, int distance, int duration) {
        this.mFinished = false;
        this.mStart = start;
        this.mFinal = start + distance;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = duration;
        this.mDeceleration = 0.0f;
        this.mVelocity = 0;
    }

    void finish() {
        this.mCurrentPosition = this.mFinal;
        this.mFinished = true;
    }

    void setFinalPosition(int position) {
        this.mFinal = position;
        this.mFinished = false;
    }

    void extendDuration(int extend) {
        this.mDuration = ((int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime)) + extend;
        this.mFinished = false;
    }

    boolean springback(int start, int min, int max) {
        this.mFinished = true;
        this.mFinal = start;
        this.mStart = start;
        this.mVelocity = 0;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = 0;
        if (start < min) {
            startSpringback(start, min, 0);
        } else if (start > max) {
            startSpringback(start, max, 0);
        }
        if (this.mFinished) {
            return false;
        }
        return true;
    }

    private void startSpringback(int start, int end, int velocity) {
        this.mFinished = false;
        this.mState = 1;
        this.mStart = start;
        this.mFinal = end;
        int delta = start - end;
        this.mDeceleration = getDeceleration(delta);
        this.mVelocity = -delta;
        this.mOver = Math.abs(delta);
        this.mDuration = (int) (1000.0d * Math.sqrt((-2.0d * ((double) delta)) / ((double) this.mDeceleration)));
    }

    void fling(int start, int velocity, int min, int max, int over) {
        this.mOver = over;
        this.mFinished = false;
        this.mVelocity = velocity;
        this.mCurrVelocity = (float) velocity;
        this.mSplineDuration = 0;
        this.mDuration = 0;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStart = start;
        this.mCurrentPosition = start;
        if (start > max || start < min) {
            startAfterEdge(start, min, max, velocity);
            return;
        }
        this.mState = 0;
        double totalDistance = 0.0d;
        if (velocity != 0) {
            int splineFlingDuration = getSplineFlingDuration(velocity);
            this.mSplineDuration = splineFlingDuration;
            this.mDuration = splineFlingDuration;
            totalDistance = getSplineFlingDistance(velocity);
        }
        this.mSplineDistance = (int) (((double) Math.signum((float) velocity)) * totalDistance);
        this.mFinal = this.mSplineDistance + start;
        if (this.mFinal < min) {
            adjustDuration(this.mStart, this.mFinal, min);
            this.mFinal = min;
        }
        if (this.mFinal > max) {
            adjustDuration(this.mStart, this.mFinal, max);
            this.mFinal = max;
        }
    }

    private double getSplineDeceleration(int velocity) {
        return Math.log((double) ((INFLEXION * ((float) Math.abs(velocity))) / (this.mFlingFriction * this.mPhysicalCoeff)));
    }

    private double getSplineFlingDistance(int velocity) {
        return ((double) (this.mFlingFriction * this.mPhysicalCoeff)) * Math.exp((((double) DECELERATION_RATE) / (((double) DECELERATION_RATE) - 1.0d)) * getSplineDeceleration(velocity));
    }

    private int getSplineFlingDuration(int velocity) {
        return (int) (1000.0d * Math.exp(getSplineDeceleration(velocity) / (((double) DECELERATION_RATE) - 1.0d)));
    }

    private void fitOnBounceCurve(int start, int end, int velocity) {
        float totalDuration = (float) Math.sqrt((2.0d * ((double) (((((float) (velocity * velocity)) / 2.0f) / Math.abs(this.mDeceleration)) + ((float) Math.abs(end - start))))) / ((double) Math.abs(this.mDeceleration)));
        this.mStartTime -= (long) ((int) (1000.0f * (totalDuration - (((float) (-velocity)) / this.mDeceleration))));
        this.mStart = end;
        this.mVelocity = (int) ((-this.mDeceleration) * totalDuration);
    }

    private void startBounceAfterEdge(int start, int end, int velocity) {
        int i;
        if (velocity == 0) {
            i = start - end;
        } else {
            i = velocity;
        }
        this.mDeceleration = getDeceleration(i);
        fitOnBounceCurve(start, end, velocity);
        onEdgeReached();
    }

    private void startAfterEdge(int start, int min, int max, int velocity) {
        if (start <= min || start >= max) {
            int edge;
            boolean positive = start > max;
            if (positive) {
                edge = max;
            } else {
                edge = min;
            }
            int overDistance = start - edge;
            if (overDistance * velocity >= 0) {
                startBounceAfterEdge(start, edge, velocity);
                return;
            } else if (getSplineFlingDistance(velocity) > ((double) Math.abs(overDistance))) {
                fling(start, velocity, positive ? min : start, positive ? start : max, this.mOver);
                return;
            } else {
                startSpringback(start, edge, velocity);
                return;
            }
        }
        Log.e("OverScroller", "startAfterEdge called from a valid position");
        this.mFinished = true;
    }

    void notifyEdgeReached(int start, int end, int over) {
        if (this.mState == 0) {
            this.mOver = over;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            startAfterEdge(start, end, end, (int) this.mCurrVelocity);
        }
    }

    private void onEdgeReached() {
        float distance = ((float) (this.mVelocity * this.mVelocity)) / (Math.abs(this.mDeceleration) * 2.0f);
        float sign = Math.signum((float) this.mVelocity);
        if (distance > ((float) this.mOver)) {
            this.mDeceleration = (((-sign) * ((float) this.mVelocity)) * ((float) this.mVelocity)) / (((float) this.mOver) * 2.0f);
            distance = (float) this.mOver;
        }
        this.mOver = (int) distance;
        this.mState = 2;
        int i = this.mStart;
        if (this.mVelocity <= 0) {
            distance = -distance;
        }
        this.mFinal = i + ((int) distance);
        this.mDuration = -((int) ((1000.0f * ((float) this.mVelocity)) / this.mDeceleration));
    }

    boolean continueWhenFinished() {
        switch (this.mState) {
            case 0:
                if (this.mDuration < this.mSplineDuration) {
                    this.mStart = this.mFinal;
                    this.mVelocity = (int) this.mCurrVelocity;
                    this.mDeceleration = getDeceleration(this.mVelocity);
                    this.mStartTime += (long) this.mDuration;
                    onEdgeReached();
                    break;
                }
                return false;
            case 1:
                return false;
            case 2:
                this.mStartTime += (long) this.mDuration;
                startSpringback(this.mFinal, this.mStart, 0);
                break;
        }
        update();
        return true;
    }

    boolean update() {
        long currentTime = AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
        if (currentTime > ((long) this.mDuration)) {
            return false;
        }
        double distance = 0.0d;
        float t;
        switch (this.mState) {
            case 0:
                t = ((float) currentTime) / ((float) this.mSplineDuration);
                int index = (int) (100.0f * t);
                float distanceCoef = END_TENSION;
                float velocityCoef = 0.0f;
                if (index < 100) {
                    float t_inf = ((float) index) / 100.0f;
                    float t_sup = ((float) (index + 1)) / 100.0f;
                    float d_inf = SPLINE_POSITION[index];
                    velocityCoef = (SPLINE_POSITION[index + 1] - d_inf) / (t_sup - t_inf);
                    distanceCoef = d_inf + ((t - t_inf) * velocityCoef);
                }
                distance = (double) (((float) this.mSplineDistance) * distanceCoef);
                this.mCurrVelocity = ((((float) this.mSplineDistance) * velocityCoef) / ((float) this.mSplineDuration)) * 1000.0f;
                break;
            case 1:
                t = ((float) currentTime) / ((float) this.mDuration);
                float t2 = t * t;
                float sign = Math.signum((float) this.mVelocity);
                distance = (double) ((((float) this.mOver) * sign) * ((3.0f * t2) - ((2.0f * t) * t2)));
                this.mCurrVelocity = ((((float) this.mOver) * sign) * 6.0f) * ((-t) + t2);
                break;
            case 2:
                t = ((float) currentTime) / 1000.0f;
                this.mCurrVelocity = ((float) this.mVelocity) + (this.mDeceleration * t);
                distance = (double) ((((float) this.mVelocity) * t) + (((this.mDeceleration * t) * t) / 2.0f));
                break;
        }
        this.mCurrentPosition = this.mStart + ((int) Math.round(distance));
        return true;
    }
}
