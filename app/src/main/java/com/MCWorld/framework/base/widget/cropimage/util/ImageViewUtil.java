package com.MCWorld.framework.base.widget.cropimage.util;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

public class ImageViewUtil {
    public static Rect getBitmapRectCenterInside(Bitmap bitmap, View view) {
        return getBitmapRectCenterInsideHelper(bitmap.getWidth(), bitmap.getHeight(), view.getWidth(), view.getHeight());
    }

    public static Rect getBitmapRectCenterInside(int bitmapWidth, int bitmapHeight, int viewWidth, int viewHeight) {
        return getBitmapRectCenterInsideHelper(bitmapWidth, bitmapHeight, viewWidth, viewHeight);
    }

    private static Rect getBitmapRectCenterInsideHelper(int bitmapWidth, int bitmapHeight, int viewWidth, int viewHeight) {
        double resultHeight;
        double resultWidth;
        int resultX;
        int resultY;
        double viewToBitmapWidthRatio = Double.POSITIVE_INFINITY;
        double viewToBitmapHeightRatio = Double.POSITIVE_INFINITY;
        if (viewWidth < bitmapWidth) {
            viewToBitmapWidthRatio = ((double) viewWidth) / ((double) bitmapWidth);
        }
        if (viewHeight < bitmapHeight) {
            viewToBitmapHeightRatio = ((double) viewHeight) / ((double) bitmapHeight);
        }
        if (viewToBitmapWidthRatio == Double.POSITIVE_INFINITY && viewToBitmapHeightRatio == Double.POSITIVE_INFINITY) {
            resultHeight = (double) bitmapHeight;
            resultWidth = (double) bitmapWidth;
        } else if (viewToBitmapWidthRatio <= viewToBitmapHeightRatio) {
            resultWidth = (double) viewWidth;
            resultHeight = (((double) bitmapHeight) * resultWidth) / ((double) bitmapWidth);
        } else {
            resultHeight = (double) viewHeight;
            resultWidth = (((double) bitmapWidth) * resultHeight) / ((double) bitmapHeight);
        }
        if (resultWidth == ((double) viewWidth)) {
            resultX = 0;
            resultY = (int) Math.round((((double) viewHeight) - resultHeight) / 2.0d);
        } else if (resultHeight == ((double) viewHeight)) {
            resultX = (int) Math.round((((double) viewWidth) - resultWidth) / 2.0d);
            resultY = 0;
        } else {
            resultX = (int) Math.round((((double) viewWidth) - resultWidth) / 2.0d);
            resultY = (int) Math.round((((double) viewHeight) - resultHeight) / 2.0d);
        }
        return new Rect(resultX, resultY, ((int) Math.ceil(resultWidth)) + resultX, ((int) Math.ceil(resultHeight)) + resultY);
    }
}
