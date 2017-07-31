package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.widget.TextView;

/* compiled from: TextHintColorSetter */
public class g extends k {
    public g(TextView targetView, int resId) {
        super(targetView, resId);
    }

    public g(int viewId, int resId) {
        super(viewId, resId);
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            ((TextView) this.mView).setHintTextColor(a(newTheme));
        }
    }
}
