package com.huluxia.widget.menudrawer.compat;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

/* compiled from: ActionBarHelperNative */
final class c {
    private static final String TAG = "ActionBarHelperNative";
    private static final int[] THEME_ATTRS = new int[]{16843531};

    /* compiled from: ActionBarHelperNative */
    private static class a {
        public Method setHomeActionContentDescription;
        public Method setHomeAsUpIndicator;
        public ImageView upIndicatorView;

        a(Activity activity) {
            try {
                this.setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", new Class[]{Drawable.class});
                this.setHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", new Class[]{Integer.TYPE});
            } catch (Throwable th) {
                View home = activity.findViewById(16908332);
                if (home != null) {
                    ViewGroup parent = (ViewGroup) home.getParent();
                    if (parent.getChildCount() == 2) {
                        View up;
                        View first = parent.getChildAt(0);
                        View second = parent.getChildAt(1);
                        if (first.getId() == 16908332) {
                            up = second;
                        } else {
                            up = first;
                        }
                        if (up instanceof ImageView) {
                            this.upIndicatorView = (ImageView) up;
                        }
                    }
                }
            }
        }
    }

    private c() {
    }

    public static void a(Object info, Activity activity, Drawable drawable, int contentDescRes) {
        a sii = (a) info;
        if (sii.setHomeAsUpIndicator != null) {
            try {
                ActionBar actionBar = activity.getActionBar();
                sii.setHomeAsUpIndicator.invoke(actionBar, new Object[]{drawable});
                sii.setHomeActionContentDescription.invoke(actionBar, new Object[]{Integer.valueOf(contentDescRes)});
            } catch (Throwable th) {
            }
        } else if (sii.upIndicatorView != null) {
            sii.upIndicatorView.setImageDrawable(drawable);
        }
    }

    public static void b(Object info, Activity activity, int contentDescRes) {
        a sii = (a) info;
        if (sii.setHomeAsUpIndicator != null) {
            try {
                ActionBar actionBar = activity.getActionBar();
                sii.setHomeActionContentDescription.invoke(actionBar, new Object[]{Integer.valueOf(contentDescRes)});
            } catch (Throwable th) {
            }
        }
    }

    public static Drawable a(Object info, Activity activity) {
        TypedArray a = activity.obtainStyledAttributes(THEME_ATTRS);
        Drawable result = a.getDrawable(0);
        a.recycle();
        return result;
    }

    public static Object C(Activity activity) {
        return new a(activity);
    }

    public static void a(Activity activity, boolean b) {
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(b);
        }
    }
}
