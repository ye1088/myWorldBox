package com.huluxia.widget.menudrawer.compat;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import java.lang.reflect.Method;

/* compiled from: ActionBarHelper */
public final class a {
    static final boolean DEBUG = false;
    private static final String TAG = "ActionBarHelper";
    private Object bCh;
    private boolean bCi;
    private Activity mActivity;

    public a(Activity activity) {
        this.mActivity = activity;
        try {
            Method m = activity.getClass().getMethod("getSupportActionBar", new Class[0]);
            this.bCi = true;
        } catch (NoSuchMethodException e) {
        }
        this.bCh = PC();
    }

    private Object PC() {
        if (this.bCi && VERSION.SDK_INT < 14) {
            return b.C(this.mActivity);
        }
        if (VERSION.SDK_INT >= 11) {
            return c.C(this.mActivity);
        }
        return null;
    }

    public void setActionBarUpIndicator(Drawable drawable, int contentDesc) {
        if (this.bCi && VERSION.SDK_INT < 14) {
            b.a(this.bCh, this.mActivity, drawable, contentDesc);
        } else if (VERSION.SDK_INT >= 11) {
            c.a(this.bCh, this.mActivity, drawable, contentDesc);
        }
    }

    public void setActionBarDescription(int contentDesc) {
        if (this.bCi && VERSION.SDK_INT < 14) {
            b.b(this.bCh, this.mActivity, contentDesc);
        } else if (VERSION.SDK_INT >= 11) {
            c.b(this.bCh, this.mActivity, contentDesc);
        }
    }

    public Drawable getThemeUpIndicator() {
        if (this.bCi && VERSION.SDK_INT < 14) {
            return b.ax(this.bCh);
        }
        if (VERSION.SDK_INT >= 11) {
            return c.a(this.bCh, this.mActivity);
        }
        return null;
    }

    public void do(boolean enabled) {
        if (this.bCi && VERSION.SDK_INT < 14) {
            b.g(this.bCh, enabled);
        } else if (VERSION.SDK_INT >= 11) {
            c.a(this.mActivity, enabled);
        }
    }
}
