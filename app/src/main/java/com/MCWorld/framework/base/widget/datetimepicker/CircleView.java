package com.MCWorld.framework.base.widget.datetimepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import com.MCWorld.framework.R;
import com.MCWorld.framework.R$string;

public class CircleView extends View {
    private static final String TAG = "CircleView";
    private float mAmPmCircleRadiusMultiplier;
    private int mBlack;
    private int mCircleRadius;
    private float mCircleRadiusMultiplier;
    private boolean mDrawValuesReady;
    private boolean mIs24HourMode;
    private boolean mIsInitialized;
    private final Paint mPaint = new Paint();
    private int mWhite;
    private int mXCenter;
    private int mYCenter;

    public CircleView(Context context) {
        super(context);
        Resources res = context.getResources();
        this.mWhite = res.getColor(R.color.white);
        this.mBlack = res.getColor(R.color.numbers_text_color);
        this.mPaint.setAntiAlias(true);
        this.mIsInitialized = false;
    }

    public void initialize(Context context, boolean is24HourMode) {
        if (this.mIsInitialized) {
            Log.e(TAG, "CircleView may only be initialized once.");
            return;
        }
        Resources res = context.getResources();
        this.mIs24HourMode = is24HourMode;
        if (is24HourMode) {
            this.mCircleRadiusMultiplier = Float.parseFloat(res.getString(R$string.circle_radius_multiplier_24HourMode));
        } else {
            this.mCircleRadiusMultiplier = Float.parseFloat(res.getString(R$string.circle_radius_multiplier));
            this.mAmPmCircleRadiusMultiplier = Float.parseFloat(res.getString(R$string.ampm_circle_radius_multiplier));
        }
        this.mIsInitialized = true;
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.mIsInitialized) {
            if (!this.mDrawValuesReady) {
                this.mXCenter = getWidth() / 2;
                this.mYCenter = getHeight() / 2;
                this.mCircleRadius = (int) (((float) Math.min(this.mXCenter, this.mYCenter)) * this.mCircleRadiusMultiplier);
                if (!this.mIs24HourMode) {
                    this.mYCenter -= ((int) (((float) this.mCircleRadius) * this.mAmPmCircleRadiusMultiplier)) / 2;
                }
                this.mDrawValuesReady = true;
            }
            this.mPaint.setColor(this.mWhite);
            canvas.drawCircle((float) this.mXCenter, (float) this.mYCenter, (float) this.mCircleRadius, this.mPaint);
            this.mPaint.setColor(this.mBlack);
            canvas.drawCircle((float) this.mXCenter, (float) this.mYCenter, 2.0f, this.mPaint);
        }
    }
}
