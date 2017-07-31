package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

/* compiled from: ProgressDrawableSetter */
public class d extends k {
    private int bIL;
    private int width;

    public d(ProgressBar targetView, int resId, int width, int height) {
        super(targetView, resId);
        this.width = width;
        this.bIL = height;
    }

    public d(int viewId, int resId, int width, int height) {
        super(viewId, resId);
        this.width = width;
        this.bIL = height;
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            Drawable drawable = d(newTheme);
            drawable.setBounds(0, 0, this.width, this.bIL);
            ((ProgressBar) this.mView).setIndeterminateDrawable(drawable);
        }
    }
}
