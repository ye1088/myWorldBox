package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.widget.ImageView;

/* compiled from: ImageDrawableSetter */
public class b extends k {
    public b(ImageView targetView, int resId) {
        super(targetView, resId);
    }

    public b(int viewId, int resId) {
        super(viewId, resId);
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            ((ImageView) this.mView).setImageResource(c(newTheme));
        }
    }
}
