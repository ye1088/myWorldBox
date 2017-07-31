package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.view.View;

/* compiled from: ViewBackgroundColorSetter */
public class h extends k {
    public h(View target, int resId) {
        super(target, resId);
    }

    public h(int viewId, int resId) {
        super(viewId, resId);
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            this.mView.setBackgroundColor(a(newTheme));
        }
    }
}
