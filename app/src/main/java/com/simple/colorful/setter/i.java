package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.view.View;

/* compiled from: ViewBackgroundDrawableSetter */
public final class i extends k {
    public i(View targetView, int resId) {
        super(targetView, resId);
    }

    public i(int viewId, int resId) {
        super(viewId, resId);
    }

    public void e(Theme newTheme, int themeId) {
        if (this.mView != null) {
            mD(c(newTheme));
        }
    }

    private void mD(int resId) {
        if (VERSION.SDK_INT < 21) {
            int paddingTop = this.mView.getPaddingTop();
            int paddingLeft = this.mView.getPaddingLeft();
            int paddingRight = this.mView.getPaddingRight();
            int paddingBottom = this.mView.getPaddingBottom();
            this.mView.setBackgroundResource(resId);
            this.mView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            return;
        }
        this.mView.setBackgroundResource(resId);
    }
}
