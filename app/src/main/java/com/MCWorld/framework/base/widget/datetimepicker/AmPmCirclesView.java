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
import java.text.DateFormatSymbols;

public class AmPmCirclesView extends View {
    private static final int AM = 0;
    private static final int PM = 1;
    private static final int PRESSED_ALPHA = 175;
    private static final int SELECTED_ALPHA = 51;
    private static final String TAG = "AmPmCirclesView";
    private int mAmOrPm;
    private int mAmOrPmPressed;
    private int mAmPmCircleRadius;
    private float mAmPmCircleRadiusMultiplier;
    private int mAmPmTextColor;
    private int mAmPmYCenter;
    private String mAmText;
    private int mAmXCenter;
    private int mBlue;
    private float mCircleRadiusMultiplier;
    private boolean mDrawValuesReady;
    private boolean mIsInitialized = false;
    private final Paint mPaint = new Paint();
    private String mPmText;
    private int mPmXCenter;
    private int mWhite;

    public AmPmCirclesView(Context context) {
        super(context);
    }

    public void initialize(Context context, int amOrPm) {
        if (this.mIsInitialized) {
            Log.e(TAG, "AmPmCirclesView may only be initialized once.");
            return;
        }
        Resources res = context.getResources();
        this.mWhite = res.getColor(R.color.white);
        this.mAmPmTextColor = res.getColor(R.color.ampm_text_color);
        this.mBlue = res.getColor(R.color.blue);
        this.mPaint.setTypeface(Typeface.create(res.getString(R$string.sans_serif), 0));
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Align.CENTER);
        this.mCircleRadiusMultiplier = Float.parseFloat(res.getString(R$string.circle_radius_multiplier));
        this.mAmPmCircleRadiusMultiplier = Float.parseFloat(res.getString(R$string.ampm_circle_radius_multiplier));
        String[] amPmTexts = new DateFormatSymbols().getAmPmStrings();
        this.mAmText = amPmTexts[0];
        this.mPmText = amPmTexts[1];
        setAmOrPm(amOrPm);
        this.mAmOrPmPressed = -1;
        this.mIsInitialized = true;
    }

    public void setAmOrPm(int amOrPm) {
        this.mAmOrPm = amOrPm;
    }

    public void setAmOrPmPressed(int amOrPmPressed) {
        this.mAmOrPmPressed = amOrPmPressed;
    }

    public int getIsTouchingAmOrPm(float xCoord, float yCoord) {
        if (!this.mDrawValuesReady) {
            return -1;
        }
        int squaredYDistance = (int) ((yCoord - ((float) this.mAmPmYCenter)) * (yCoord - ((float) this.mAmPmYCenter)));
        if (((int) Math.sqrt((double) (((xCoord - ((float) this.mAmXCenter)) * (xCoord - ((float) this.mAmXCenter))) + ((float) squaredYDistance)))) <= this.mAmPmCircleRadius) {
            return 0;
        }
        if (((int) Math.sqrt((double) (((xCoord - ((float) this.mPmXCenter)) * (xCoord - ((float) this.mPmXCenter))) + ((float) squaredYDistance)))) <= this.mAmPmCircleRadius) {
            return 1;
        }
        return -1;
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.mIsInitialized) {
            if (!this.mDrawValuesReady) {
                int layoutXCenter = getWidth() / 2;
                int layoutYCenter = getHeight() / 2;
                int circleRadius = (int) (((float) Math.min(layoutXCenter, layoutYCenter)) * this.mCircleRadiusMultiplier);
                this.mAmPmCircleRadius = (int) (((float) circleRadius) * this.mAmPmCircleRadiusMultiplier);
                this.mPaint.setTextSize((float) ((this.mAmPmCircleRadius * 3) / 4));
                this.mAmPmYCenter = (layoutYCenter - (this.mAmPmCircleRadius / 2)) + circleRadius;
                this.mAmXCenter = (layoutXCenter - circleRadius) + this.mAmPmCircleRadius;
                this.mPmXCenter = (layoutXCenter + circleRadius) - this.mAmPmCircleRadius;
                this.mDrawValuesReady = true;
            }
            int amColor = this.mWhite;
            int amAlpha = 255;
            int pmColor = this.mWhite;
            int pmAlpha = 255;
            if (this.mAmOrPm == 0) {
                amColor = this.mBlue;
                amAlpha = 51;
            } else if (this.mAmOrPm == 1) {
                pmColor = this.mBlue;
                pmAlpha = 51;
            }
            if (this.mAmOrPmPressed == 0) {
                amColor = this.mBlue;
                amAlpha = 175;
            } else if (this.mAmOrPmPressed == 1) {
                pmColor = this.mBlue;
                pmAlpha = 175;
            }
            this.mPaint.setColor(amColor);
            this.mPaint.setAlpha(amAlpha);
            canvas.drawCircle((float) this.mAmXCenter, (float) this.mAmPmYCenter, (float) this.mAmPmCircleRadius, this.mPaint);
            this.mPaint.setColor(pmColor);
            this.mPaint.setAlpha(pmAlpha);
            canvas.drawCircle((float) this.mPmXCenter, (float) this.mAmPmYCenter, (float) this.mAmPmCircleRadius, this.mPaint);
            this.mPaint.setColor(this.mAmPmTextColor);
            int textYCenter = this.mAmPmYCenter - (((int) (this.mPaint.descent() + this.mPaint.ascent())) / 2);
            canvas.drawText(this.mAmText, (float) this.mAmXCenter, (float) textYCenter, this.mPaint);
            canvas.drawText(this.mPmText, (float) this.mPmXCenter, (float) textYCenter, this.mPaint);
        }
    }
}
