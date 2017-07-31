package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.widget.CheckBox;

/* compiled from: CheckButtonSetter */
public class a extends k {
    public a(CheckBox targetView, int resId) {
        super(targetView, resId);
    }

    public a(int viewId, int resId) {
        super(viewId, resId);
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            ((CheckBox) this.mView).setButtonDrawable(d(newTheme));
        }
    }
}
