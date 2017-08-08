package com.MCWorld.framework.base.widget.datetimepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import com.MCWorld.framework.R;
import com.MCWorld.framework.R$string;
import com.nineoldandroids.animation.Keyframe;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class RadialTextsView extends View {
    private static final String TAG = "RadialTextsView";
    private float mAmPmCircleRadiusMultiplier;
    private float mAnimationRadiusMultiplier;
    private float mCircleRadius;
    private float mCircleRadiusMultiplier;
    ObjectAnimator mDisappearAnimator;
    private boolean mDrawValuesReady;
    private boolean mHasInnerCircle;
    private float mInnerNumbersRadiusMultiplier;
    private float[] mInnerTextGridHeights;
    private float[] mInnerTextGridWidths;
    private float mInnerTextSize;
    private float mInnerTextSizeMultiplier;
    private String[] mInnerTexts;
    private InvalidateUpdateListener mInvalidateUpdateListener;
    private boolean mIs24HourMode;
    private boolean mIsInitialized = false;
    private float mNumbersRadiusMultiplier;
    private final Paint mPaint = new Paint();
    ObjectAnimator mReappearAnimator;
    private float[] mTextGridHeights;
    private boolean mTextGridValuesDirty;
    private float[] mTextGridWidths;
    private float mTextSize;
    private float mTextSizeMultiplier;
    private String[] mTexts;
    private float mTransitionEndRadiusMultiplier;
    private float mTransitionMidRadiusMultiplier;
    private Typeface mTypefaceLight;
    private Typeface mTypefaceRegular;
    private int mXCenter;
    private int mYCenter;

    private class InvalidateUpdateListener implements AnimatorUpdateListener {
        private InvalidateUpdateListener() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            RadialTextsView.this.invalidate();
        }
    }

    public RadialTextsView(Context context) {
        super(context);
    }

    public void initialize(Resources res, String[] texts, String[] innerTexts, boolean is24HourMode, boolean disappearsOut) {
        int i = -1;
        boolean z = false;
        if (this.mIsInitialized) {
            Log.e(TAG, "This RadialTextsView may only be initialized once.");
            return;
        }
        int i2;
        this.mPaint.setColor(res.getColor(R.color.numbers_text_color));
        this.mTypefaceLight = Typeface.create(res.getString(R$string.radial_numbers_typeface), 0);
        this.mTypefaceRegular = Typeface.create(res.getString(R$string.sans_serif), 0);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Align.CENTER);
        this.mTexts = texts;
        this.mInnerTexts = innerTexts;
        this.mIs24HourMode = is24HourMode;
        if (innerTexts != null) {
            z = true;
        }
        this.mHasInnerCircle = z;
        if (is24HourMode) {
            this.mCircleRadiusMultiplier = Float.parseFloat(res.getString(R$string.circle_radius_multiplier_24HourMode));
        } else {
            this.mCircleRadiusMultiplier = Float.parseFloat(res.getString(R$string.circle_radius_multiplier));
            this.mAmPmCircleRadiusMultiplier = Float.parseFloat(res.getString(R$string.ampm_circle_radius_multiplier));
        }
        this.mTextGridHeights = new float[7];
        this.mTextGridWidths = new float[7];
        if (this.mHasInnerCircle) {
            this.mNumbersRadiusMultiplier = Float.parseFloat(res.getString(R$string.numbers_radius_multiplier_outer));
            this.mTextSizeMultiplier = Float.parseFloat(res.getString(R$string.text_size_multiplier_outer));
            this.mInnerNumbersRadiusMultiplier = Float.parseFloat(res.getString(R$string.numbers_radius_multiplier_inner));
            this.mInnerTextSizeMultiplier = Float.parseFloat(res.getString(R$string.text_size_multiplier_inner));
            this.mInnerTextGridHeights = new float[7];
            this.mInnerTextGridWidths = new float[7];
        } else {
            this.mNumbersRadiusMultiplier = Float.parseFloat(res.getString(R$string.numbers_radius_multiplier_normal));
            this.mTextSizeMultiplier = Float.parseFloat(res.getString(R$string.text_size_multiplier_normal));
        }
        this.mAnimationRadiusMultiplier = 1.0f;
        if (disappearsOut) {
            i2 = -1;
        } else {
            i2 = 1;
        }
        this.mTransitionMidRadiusMultiplier = (((float) i2) * 0.05f) + 1.0f;
        if (disappearsOut) {
            i = 1;
        }
        this.mTransitionEndRadiusMultiplier = (0.3f * ((float) i)) + 1.0f;
        this.mInvalidateUpdateListener = new InvalidateUpdateListener();
        this.mTextGridValuesDirty = true;
        this.mIsInitialized = true;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void setAnimationRadiusMultiplier(float animationRadiusMultiplier) {
        this.mAnimationRadiusMultiplier = animationRadiusMultiplier;
        this.mTextGridValuesDirty = true;
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.mIsInitialized) {
            if (!this.mDrawValuesReady) {
                this.mXCenter = getWidth() / 2;
                this.mYCenter = getHeight() / 2;
                this.mCircleRadius = ((float) Math.min(this.mXCenter, this.mYCenter)) * this.mCircleRadiusMultiplier;
                if (!this.mIs24HourMode) {
                    this.mYCenter = (int) (((float) this.mYCenter) - ((this.mCircleRadius * this.mAmPmCircleRadiusMultiplier) / 2.0f));
                }
                this.mTextSize = this.mCircleRadius * this.mTextSizeMultiplier;
                if (this.mHasInnerCircle) {
                    this.mInnerTextSize = this.mCircleRadius * this.mInnerTextSizeMultiplier;
                }
                renderAnimations();
                this.mTextGridValuesDirty = true;
                this.mDrawValuesReady = true;
            }
            if (this.mTextGridValuesDirty) {
                calculateGridSizes((this.mCircleRadius * this.mNumbersRadiusMultiplier) * this.mAnimationRadiusMultiplier, (float) this.mXCenter, (float) this.mYCenter, this.mTextSize, this.mTextGridHeights, this.mTextGridWidths);
                if (this.mHasInnerCircle) {
                    calculateGridSizes((this.mCircleRadius * this.mInnerNumbersRadiusMultiplier) * this.mAnimationRadiusMultiplier, (float) this.mXCenter, (float) this.mYCenter, this.mInnerTextSize, this.mInnerTextGridHeights, this.mInnerTextGridWidths);
                }
                this.mTextGridValuesDirty = false;
            }
            drawTexts(canvas, this.mTextSize, this.mTypefaceLight, this.mTexts, this.mTextGridWidths, this.mTextGridHeights);
            if (this.mHasInnerCircle) {
                drawTexts(canvas, this.mInnerTextSize, this.mTypefaceRegular, this.mInnerTexts, this.mInnerTextGridWidths, this.mInnerTextGridHeights);
            }
        }
    }

    private void calculateGridSizes(float numbersRadius, float xCenter, float yCenter, float textSize, float[] textGridHeights, float[] textGridWidths) {
        float offset1 = numbersRadius;
        float offset2 = (((float) Math.sqrt(3.0d)) * numbersRadius) / 2.0f;
        float offset3 = numbersRadius / 2.0f;
        this.mPaint.setTextSize(textSize);
        yCenter -= (this.mPaint.descent() + this.mPaint.ascent()) / 2.0f;
        textGridHeights[0] = yCenter - offset1;
        textGridWidths[0] = xCenter - offset1;
        textGridHeights[1] = yCenter - offset2;
        textGridWidths[1] = xCenter - offset2;
        textGridHeights[2] = yCenter - offset3;
        textGridWidths[2] = xCenter - offset3;
        textGridHeights[3] = yCenter;
        textGridWidths[3] = xCenter;
        textGridHeights[4] = yCenter + offset3;
        textGridWidths[4] = xCenter + offset3;
        textGridHeights[5] = yCenter + offset2;
        textGridWidths[5] = xCenter + offset2;
        textGridHeights[6] = yCenter + offset1;
        textGridWidths[6] = xCenter + offset1;
    }

    private void drawTexts(Canvas canvas, float textSize, Typeface typeface, String[] texts, float[] textGridWidths, float[] textGridHeights) {
        this.mPaint.setTextSize(textSize);
        this.mPaint.setTypeface(typeface);
        canvas.drawText(texts[0], textGridWidths[3], textGridHeights[0], this.mPaint);
        canvas.drawText(texts[1], textGridWidths[4], textGridHeights[1], this.mPaint);
        canvas.drawText(texts[2], textGridWidths[5], textGridHeights[2], this.mPaint);
        canvas.drawText(texts[3], textGridWidths[6], textGridHeights[3], this.mPaint);
        canvas.drawText(texts[4], textGridWidths[5], textGridHeights[4], this.mPaint);
        canvas.drawText(texts[5], textGridWidths[4], textGridHeights[5], this.mPaint);
        canvas.drawText(texts[6], textGridWidths[3], textGridHeights[6], this.mPaint);
        canvas.drawText(texts[7], textGridWidths[2], textGridHeights[5], this.mPaint);
        canvas.drawText(texts[8], textGridWidths[1], textGridHeights[4], this.mPaint);
        canvas.drawText(texts[9], textGridWidths[0], textGridHeights[3], this.mPaint);
        canvas.drawText(texts[10], textGridWidths[1], textGridHeights[2], this.mPaint);
        canvas.drawText(texts[11], textGridWidths[2], textGridHeights[1], this.mPaint);
    }

    private void renderAnimations() {
        Keyframe kf0 = Keyframe.ofFloat(0.0f, 1.0f);
        Keyframe kf1 = Keyframe.ofFloat(0.2f, this.mTransitionMidRadiusMultiplier);
        Keyframe kf2 = Keyframe.ofFloat(1.0f, this.mTransitionEndRadiusMultiplier);
        PropertyValuesHolder radiusDisappear = PropertyValuesHolder.ofKeyframe("animationRadiusMultiplier", kf0, kf1, kf2);
        kf0 = Keyframe.ofFloat(0.0f, 1.0f);
        kf1 = Keyframe.ofFloat(1.0f, 0.0f);
        PropertyValuesHolder fadeOut = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1);
        this.mDisappearAnimator = ObjectAnimator.ofPropertyValuesHolder(this, radiusDisappear, fadeOut).setDuration((long) 500);
        this.mDisappearAnimator.addUpdateListener(this.mInvalidateUpdateListener);
        int totalDuration = (int) (((float) 500) * (1.0f + 0.25f));
        float delayPoint = (((float) 500) * 0.25f) / ((float) totalDuration);
        float midwayPoint = 1.0f - ((1.0f - delayPoint) * 0.2f);
        kf0 = Keyframe.ofFloat(0.0f, this.mTransitionEndRadiusMultiplier);
        kf1 = Keyframe.ofFloat(delayPoint, this.mTransitionEndRadiusMultiplier);
        kf2 = Keyframe.ofFloat(midwayPoint, this.mTransitionMidRadiusMultiplier);
        Keyframe kf3 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder radiusReappear = PropertyValuesHolder.ofKeyframe("animationRadiusMultiplier", kf0, kf1, kf2, kf3);
        kf0 = Keyframe.ofFloat(0.0f, 0.0f);
        kf1 = Keyframe.ofFloat(delayPoint, 0.0f);
        kf2 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder fadeIn = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2);
        this.mReappearAnimator = ObjectAnimator.ofPropertyValuesHolder(this, radiusReappear, fadeIn).setDuration((long) totalDuration);
        this.mReappearAnimator.addUpdateListener(this.mInvalidateUpdateListener);
    }

    public ObjectAnimator getDisappearAnimator() {
        if (this.mIsInitialized && this.mDrawValuesReady && this.mDisappearAnimator != null) {
            return this.mDisappearAnimator;
        }
        Log.e(TAG, "RadialTextView was not ready for animation.");
        return null;
    }

    public ObjectAnimator getReappearAnimator() {
        if (this.mIsInitialized && this.mDrawValuesReady && this.mReappearAnimator != null) {
            return this.mReappearAnimator;
        }
        Log.e(TAG, "RadialTextView was not ready for animation.");
        return null;
    }
}
