package com.simple.colorful;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import com.huluxia.bbs.b.n;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.pref.a;
import com.huluxia.utils.ah;

/* compiled from: UtilsTheme */
public class d {
    public static int RA() {
        return ah.KZ().Lo() == 0 ? n.HtAppTheme_Night : n.HtAppTheme;
    }

    public static int Lo() {
        return ah.KZ().Lo();
    }

    public static boolean RB() {
        if (ah.KZ().Lo() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isDayMode() {
        return ah.KZ().Lo() == 0;
    }

    public static void RC() {
        int newTheme;
        if (isDayMode()) {
            newTheme = 1;
        } else {
            newTheme = 0;
        }
        ah.KZ().lj(newTheme);
        EventNotifyCenter.notifyEvent(a.class, 0, Integer.valueOf(newTheme));
    }

    public static Drawable o(Context context, int attrId) {
        return a(context.getTheme(), attrId);
    }

    public static Drawable a(Theme theme, int attrId) {
        Drawable drawable = null;
        if (theme == null) {
            return null;
        }
        try {
            TypedArray a = theme.obtainStyledAttributes(new int[]{attrId});
            drawable = a.getDrawable(0);
            a.recycle();
        } catch (Exception e) {
            HLog.error("UtilsTheme", "exception caught " + e.toString(), new Object[0]);
        }
        return drawable;
    }

    public static ColorStateList getColorStateList(Context context, int attrId) {
        return b(context.getTheme(), attrId);
    }

    public static ColorStateList b(Theme theme, int attrId) {
        ColorStateList color = null;
        if (theme == null) {
            return null;
        }
        try {
            TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
            color = ta.getColorStateList(0);
            ta.recycle();
        } catch (Exception e) {
            HLog.error("UtilsTheme", "exception caught " + e.toString(), new Object[0]);
        }
        return color;
    }

    public static int p(Context context, int attrId) {
        return r(context, attrId);
    }

    public static int getColor(Context context, int attrId) {
        if (context == null || context.getTheme() == null) {
            return 0;
        }
        int color = 0;
        try {
            TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{attrId});
            color = ta.getColor(0, 0);
            ta.recycle();
            return color;
        } catch (Exception e) {
            HLog.error("UtilsTheme", "exception caught " + e.toString(), new Object[0]);
            return color;
        }
    }

    public static final int q(Context context, int id) {
        if (VERSION.SDK_INT >= 23) {
            return ContextCompat.getColor(context, id);
        }
        return context.getResources().getColor(id);
    }

    public static int r(Context context, int attrId) {
        if (context == null || context.getTheme() == null) {
            return 0;
        }
        int resId = 0;
        try {
            TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{attrId});
            resId = ta.getResourceId(0, 0);
            ta.recycle();
            return resId;
        } catch (Exception e) {
            HLog.error("UtilsTheme", "exception caught " + e.toString(), new Object[0]);
            return resId;
        }
    }

    public static int c(Theme theme, int attrId) {
        if (theme == null) {
            return 0;
        }
        int resId = 0;
        try {
            TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
            resId = ta.getResourceId(0, 0);
            ta.recycle();
            return resId;
        } catch (Exception e) {
            HLog.error("UtilsTheme", "exception caught " + e.toString(), new Object[0]);
            return resId;
        }
    }

    public static int RD() {
        return isDayMode() ? n.AppDialog : n.AppDialogNight;
    }

    public static int d(Theme theme, int attrId) {
        if (theme == null) {
            return 0;
        }
        int val = 0;
        try {
            TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
            val = ta.getInteger(0, 0);
            ta.recycle();
            return val;
        } catch (Exception e) {
            HLog.error("UtilsTheme", "exception caught " + e.toString(), new Object[0]);
            return val;
        }
    }

    public static int s(Context context, int attrId) {
        if (context == null || context.getTheme() == null) {
            return 0;
        }
        int val = 0;
        try {
            TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{attrId});
            val = ta.getInteger(0, 0);
            ta.recycle();
            return val;
        } catch (Exception e) {
            HLog.error("UtilsTheme", "exception caught " + e.toString(), new Object[0]);
            return val;
        }
    }
}
