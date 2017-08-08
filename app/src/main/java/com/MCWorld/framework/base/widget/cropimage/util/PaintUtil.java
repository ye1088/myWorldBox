package com.MCWorld.framework.base.widget.cropimage.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.TypedValue;

public class PaintUtil {
    private static final String DEFAULT_BACKGROUND_COLOR_ID = "#B0000000";
    private static final int DEFAULT_CORNER_COLOR = -1;
    private static final float DEFAULT_CORNER_THICKNESS_DP = 5.0f;
    private static final float DEFAULT_GUIDELINE_THICKNESS_PX = 1.0f;
    private static final float DEFAULT_LINE_THICKNESS_DP = 3.0f;
    private static final String SEMI_TRANSPARENT = "#AAFFFFFF";

    public static Paint newBorderPaint(Context context) {
        float lineThicknessPx = TypedValue.applyDimension(1, DEFAULT_LINE_THICKNESS_DP, context.getResources().getDisplayMetrics());
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.parseColor(SEMI_TRANSPARENT));
        borderPaint.setStrokeWidth(lineThicknessPx);
        borderPaint.setStyle(Style.STROKE);
        return borderPaint;
    }

    public static Paint newGuidelinePaint() {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(SEMI_TRANSPARENT));
        paint.setStrokeWidth(DEFAULT_GUIDELINE_THICKNESS_PX);
        return paint;
    }

    public static Paint newBackgroundPaint(Context context) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(DEFAULT_BACKGROUND_COLOR_ID));
        return paint;
    }

    public static Paint newCornerPaint(Context context) {
        float lineThicknessPx = TypedValue.applyDimension(1, DEFAULT_CORNER_THICKNESS_DP, context.getResources().getDisplayMetrics());
        Paint cornerPaint = new Paint();
        cornerPaint.setColor(-1);
        cornerPaint.setStrokeWidth(lineThicknessPx);
        cornerPaint.setStyle(Style.STROKE);
        return cornerPaint;
    }

    public static float getCornerThickness() {
        return DEFAULT_CORNER_THICKNESS_DP;
    }

    public static float getLineThickness() {
        return DEFAULT_LINE_THICKNESS_DP;
    }
}
