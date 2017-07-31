package com.simple.colorful.setter;

import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import com.simple.colorful.d;

/* compiled from: ViewSetter */
public abstract class k {
    protected int bIR;
    protected int bIS;
    protected View mView;

    public abstract void e(Theme theme, int i);

    public k(View targetView, int resId) {
        this.mView = targetView;
        this.bIS = resId;
    }

    public k(int viewId, int resId) {
        this.bIR = viewId;
        this.bIS = resId;
    }

    protected int RE() {
        return this.mView != null ? this.mView.getId() : -1;
    }

    protected boolean RF() {
        return this.mView == null;
    }

    protected int a(Theme newTheme) {
        TypedValue typedValue = new TypedValue();
        newTheme.resolveAttribute(this.bIS, typedValue, true);
        return typedValue.data;
    }

    protected ColorStateList b(Theme newTheme) {
        return d.b(newTheme, this.bIS);
    }

    protected int c(Theme newTheme) {
        return d.c(newTheme, this.bIS);
    }

    protected Drawable d(Theme newTheme) {
        return d.a(newTheme, this.bIS);
    }

    protected int e(Theme newTheme) {
        return d.d(newTheme, this.bIS);
    }
}
