package com.huluxia.widget.menudrawer.compat;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

/* compiled from: ActionBarHelperCompat */
final class b {
    private static final String TAG = "ActionBarHelperCompat";

    /* compiled from: ActionBarHelperCompat */
    private static class a {
        public ImageView bCj;
        public Object bCk;
        public Method bCl;

        a(Activity activity) {
            try {
                String appPackage = activity.getPackageName();
                try {
                    this.bCj = (ImageView) ((ViewGroup) activity.findViewById(activity.getResources().getIdentifier("abs__home", "id", appPackage)).getParent()).findViewById(activity.getResources().getIdentifier("abs__up", "id", appPackage));
                } catch (Throwable th) {
                }
                if (this.bCj == null) {
                    this.bCj = (ImageView) ((ViewGroup) activity.findViewById(activity.getResources().getIdentifier("home", "id", appPackage)).getParent()).findViewById(activity.getResources().getIdentifier("up", "id", appPackage));
                }
                this.bCk = activity.getClass().getMethod("getSupportActionBar", new Class[0]).invoke(activity, null);
                this.bCl = this.bCk.getClass().getMethod("setDisplayHomeAsUpEnabled", new Class[]{Boolean.TYPE});
            } catch (Throwable th2) {
            }
        }
    }

    private b() {
    }

    public static void a(Object info, Activity activity, Drawable drawable, int contentDescRes) {
        a sii = (a) info;
        if (sii.bCj != null) {
            sii.bCj.setImageDrawable(drawable);
            sii.bCj.setContentDescription(contentDescRes == 0 ? null : activity.getString(contentDescRes));
        }
    }

    public static void b(Object info, Activity activity, int contentDescRes) {
        a sii = (a) info;
        if (sii.bCj != null) {
            sii.bCj.setContentDescription(contentDescRes == 0 ? null : activity.getString(contentDescRes));
        }
    }

    public static Drawable ax(Object info) {
        a sii = (a) info;
        if (sii.bCj != null) {
            return sii.bCj.getDrawable();
        }
        return null;
    }

    public static Object C(Activity activity) {
        return new a(activity);
    }

    public static void g(Object info, boolean enabled) {
        a sii = (a) info;
        if (sii.bCl != null) {
            try {
                sii.bCl.invoke(sii.bCk, new Object[]{Boolean.valueOf(enabled)});
            } catch (Throwable th) {
            }
        }
    }
}
