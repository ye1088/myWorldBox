package com.huluxia.image.drawee.view;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import javax.annotation.Nullable;

/* compiled from: AspectRatioMeasure */
public class a {

    /* compiled from: AspectRatioMeasure */
    public static class a {
        public int height;
        public int width;
    }

    public static void a(a spec, float aspectRatio, @Nullable LayoutParams layoutParams, int widthPadding, int heightPadding) {
        if (aspectRatio > 0.0f && layoutParams != null) {
            if (ci(layoutParams.height)) {
                spec.height = MeasureSpec.makeMeasureSpec(View.resolveSize((int) ((((float) (MeasureSpec.getSize(spec.width) - widthPadding)) / aspectRatio) + ((float) heightPadding)), spec.height), 1073741824);
            } else if (ci(layoutParams.width)) {
                spec.width = MeasureSpec.makeMeasureSpec(View.resolveSize((int) ((((float) (MeasureSpec.getSize(spec.height) - heightPadding)) * aspectRatio) + ((float) widthPadding)), spec.width), 1073741824);
            }
        }
    }

    private static boolean ci(int layoutDimension) {
        return layoutDimension == 0 || layoutDimension == -2;
    }
}
