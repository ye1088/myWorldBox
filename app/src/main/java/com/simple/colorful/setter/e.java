package com.simple.colorful.setter;

import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.widget.TextView;

/* compiled from: TextColorSetter */
public class e extends k {
    public e(TextView textView, int resId) {
        super(textView, resId);
    }

    public e(int viewId, int resId) {
        super(viewId, resId);
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            ColorStateList colorStateList = b(newTheme);
            if (colorStateList != null) {
                ((TextView) this.mView).setTextColor(colorStateList);
            }
        }
    }
}
