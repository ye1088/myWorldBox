package com.huluxia.widget.cropimage.util;

import android.content.Context;
import android.util.Pair;
import android.util.TypedValue;
import com.huluxia.widget.cropimage.cropwindow.CropOverlayView;
import com.huluxia.widget.cropimage.cropwindow.handle.Handle;

/* compiled from: HandleUtil */
public class b {
    private static final int TARGET_RADIUS_DP = 48;

    public static float getTargetRadius(Context context) {
        return TypedValue.applyDimension(1, 48.0f, context.getResources().getDisplayMetrics());
    }

    public static Handle a(float x, float y, float left, float top, float right, float bottom, float targetRadius) {
        if (isInCornerTargetZone(x, y, left, top, targetRadius)) {
            return Handle.TOP_LEFT;
        }
        if (isInCornerTargetZone(x, y, right, top, targetRadius)) {
            return Handle.TOP_RIGHT;
        }
        if (isInCornerTargetZone(x, y, left, bottom, targetRadius)) {
            return Handle.BOTTOM_LEFT;
        }
        if (isInCornerTargetZone(x, y, right, bottom, targetRadius)) {
            return Handle.BOTTOM_RIGHT;
        }
        if (isInCenterTargetZone(x, y, left, top, right, bottom) && Oj()) {
            return Handle.CENTER;
        }
        if (isInHorizontalTargetZone(x, y, left, right, top, targetRadius)) {
            return Handle.TOP;
        }
        if (isInHorizontalTargetZone(x, y, left, right, bottom, targetRadius)) {
            return Handle.BOTTOM;
        }
        if (isInVerticalTargetZone(x, y, left, top, bottom, targetRadius)) {
            return Handle.LEFT;
        }
        if (isInVerticalTargetZone(x, y, right, top, bottom, targetRadius)) {
            return Handle.RIGHT;
        }
        if (!isInCenterTargetZone(x, y, left, top, right, bottom) || Oj()) {
            return null;
        }
        return Handle.CENTER;
    }

    public static Pair<Float, Float> a(Handle handle, float x, float y, float left, float top, float right, float bottom) {
        if (handle == null) {
            return null;
        }
        float touchOffsetX = 0.0f;
        float touchOffsetY = 0.0f;
        switch (handle) {
            case TOP_LEFT:
                touchOffsetX = left - x;
                touchOffsetY = top - y;
                break;
            case TOP_RIGHT:
                touchOffsetX = right - x;
                touchOffsetY = top - y;
                break;
            case BOTTOM_LEFT:
                touchOffsetX = left - x;
                touchOffsetY = bottom - y;
                break;
            case BOTTOM_RIGHT:
                touchOffsetX = right - x;
                touchOffsetY = bottom - y;
                break;
            case LEFT:
                touchOffsetX = left - x;
                touchOffsetY = 0.0f;
                break;
            case TOP:
                touchOffsetX = 0.0f;
                touchOffsetY = top - y;
                break;
            case RIGHT:
                touchOffsetX = right - x;
                touchOffsetY = 0.0f;
                break;
            case BOTTOM:
                touchOffsetX = 0.0f;
                touchOffsetY = bottom - y;
                break;
            case CENTER:
                touchOffsetX = ((right + left) / 2.0f) - x;
                touchOffsetY = ((top + bottom) / 2.0f) - y;
                break;
        }
        return new Pair(Float.valueOf(touchOffsetX), Float.valueOf(touchOffsetY));
    }

    private static boolean isInCornerTargetZone(float x, float y, float handleX, float handleY, float targetRadius) {
        if (Math.abs(x - handleX) > targetRadius || Math.abs(y - handleY) > targetRadius) {
            return false;
        }
        return true;
    }

    private static boolean isInHorizontalTargetZone(float x, float y, float handleXStart, float handleXEnd, float handleY, float targetRadius) {
        if (x <= handleXStart || x >= handleXEnd || Math.abs(y - handleY) > targetRadius) {
            return false;
        }
        return true;
    }

    private static boolean isInVerticalTargetZone(float x, float y, float handleX, float handleYStart, float handleYEnd, float targetRadius) {
        if (Math.abs(x - handleX) > targetRadius || y <= handleYStart || y >= handleYEnd) {
            return false;
        }
        return true;
    }

    private static boolean isInCenterTargetZone(float x, float y, float left, float top, float right, float bottom) {
        if (x <= left || x >= right || y <= top || y >= bottom) {
            return false;
        }
        return true;
    }

    private static boolean Oj() {
        return !CropOverlayView.Oh();
    }
}
