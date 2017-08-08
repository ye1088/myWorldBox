package com.MCWorld.widget.cropimage.util;

import android.graphics.Rect;

/* compiled from: AspectRatioUtil */
public class a {
    public static float calculateAspectRatio(float left, float top, float right, float bottom) {
        return (right - left) / (bottom - top);
    }

    public static float calculateAspectRatio(Rect rect) {
        return ((float) rect.width()) / ((float) rect.height());
    }

    public static float calculateLeft(float top, float right, float bottom, float targetAspectRatio) {
        return right - (targetAspectRatio * (bottom - top));
    }

    public static float calculateTop(float left, float right, float bottom, float targetAspectRatio) {
        return bottom - ((right - left) / targetAspectRatio);
    }

    public static float calculateRight(float left, float top, float bottom, float targetAspectRatio) {
        return (targetAspectRatio * (bottom - top)) + left;
    }

    public static float calculateBottom(float left, float top, float right, float targetAspectRatio) {
        return ((right - left) / targetAspectRatio) + top;
    }

    public static float calculateWidth(float top, float bottom, float targetAspectRatio) {
        return targetAspectRatio * (bottom - top);
    }

    public static float calculateHeight(float left, float right, float targetAspectRatio) {
        return (right - left) / targetAspectRatio;
    }
}
