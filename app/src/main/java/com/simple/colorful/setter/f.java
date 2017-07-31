package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

/* compiled from: TextDrawableSetter */
public class f extends k {
    public static final int bIM = 0;
    public static final int bIN = 1;
    public static final int bIO = 2;
    public static final int bIP = 3;
    private int mPosition;

    /* compiled from: TextDrawableSetter */
    public @interface a {
    }

    public f(TextView targetView, int resId, @a int position) {
        super((View) targetView, resId);
        this.mPosition = position;
    }

    public f(int viewId, int resId, @a int position) {
        super(viewId, resId);
        this.mPosition = position;
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            TextView textView = this.mView;
            Drawable drawable = d(newTheme);
            switch (this.mPosition) {
                case 0:
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                    return;
                case 1:
                    textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    return;
                case 2:
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                    return;
                case 3:
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
                    return;
                default:
                    return;
            }
        }
    }
}
