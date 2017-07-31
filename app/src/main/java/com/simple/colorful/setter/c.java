package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.widget.ImageView;

/* compiled from: ImageViewSetter */
public class c extends k {
    public c(ImageView targetView, int resId) {
        super(targetView, resId);
    }

    public c(int viewId, int resId) {
        super(viewId, resId);
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            a((ImageView) this.mView, e(newTheme));
        }
    }

    private void a(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 1.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 1.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }
}
