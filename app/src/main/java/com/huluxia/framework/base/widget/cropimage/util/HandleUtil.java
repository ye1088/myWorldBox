package com.huluxia.framework.base.widget.cropimage.util;

import android.content.Context;
import android.util.Pair;
import android.util.TypedValue;
import com.huluxia.framework.base.widget.cropimage.cropwindow.CropOverlayView;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdges;
import com.huluxia.framework.base.widget.cropimage.cropwindow.handle.HandleFactory;
import com.huluxia.framework.base.widget.cropimage.cropwindow.handle.HandleFactory.HandleType;
import com.huluxia.framework.base.widget.cropimage.cropwindow.handle.HandleHelper;

public class HandleUtil {
    private static final int TARGET_RADIUS_DP = 48;

    public static float getTargetRadius(Context context) {
        return TypedValue.applyDimension(1, 48.0f, context.getResources().getDisplayMetrics());
    }

    public static HandleHelper getPressedHandleHelper(float x, float y, float left, float top, float right, float bottom, float targetRadius, MyEdges edges) {
        if (isInCornerTargetZone(x, y, left, top, targetRadius)) {
            return HandleFactory.createHelper(edges, HandleType.TOP_LEFT);
        }
        if (isInCornerTargetZone(x, y, right, top, targetRadius)) {
            return HandleFactory.createHelper(edges, HandleType.TOP_RIGHT);
        }
        if (isInCornerTargetZone(x, y, left, bottom, targetRadius)) {
            return HandleFactory.createHelper(edges, HandleType.BOTTOM_LEFT);
        }
        if (isInCornerTargetZone(x, y, right, bottom, targetRadius)) {
            return HandleFactory.createHelper(edges, HandleType.BOTTOM_RIGHT);
        }
        if (isInCenterTargetZone(x, y, left, top, right, bottom) && focusCenter(edges)) {
            return HandleFactory.createHelper(edges, HandleType.CENTER);
        }
        if (isInHorizontalTargetZone(x, y, left, right, top, targetRadius)) {
            return HandleFactory.createHelper(edges, HandleType.TOP);
        }
        if (isInHorizontalTargetZone(x, y, left, right, bottom, targetRadius)) {
            return HandleFactory.createHelper(edges, HandleType.BOTTOM);
        }
        if (isInVerticalTargetZone(x, y, left, top, bottom, targetRadius)) {
            return HandleFactory.createHelper(edges, HandleType.LEFT);
        }
        if (isInVerticalTargetZone(x, y, right, top, bottom, targetRadius)) {
            return HandleFactory.createHelper(edges, HandleType.RIGHT);
        }
        if (!isInCenterTargetZone(x, y, left, top, right, bottom) || focusCenter(edges)) {
            return null;
        }
        return HandleFactory.createHelper(edges, HandleType.CENTER);
    }

    public static Pair<Float, Float> getOffset(HandleHelper handleHelper, float x, float y, float left, float top, float right, float bottom) {
        if (handleHelper == null) {
            return null;
        }
        float touchOffsetX = 0.0f;
        float touchOffsetY = 0.0f;
        switch (handleHelper.getHandleType()) {
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

    private static boolean focusCenter(MyEdges edges) {
        return !CropOverlayView.showGuidelines(edges);
    }
}
