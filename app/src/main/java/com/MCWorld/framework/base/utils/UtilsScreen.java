package com.MCWorld.framework.base.utils;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class UtilsScreen {
    public static int getScreenWidth(Context context) {
        return getScreenSize(context, null).x;
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context, null).y;
    }

    @SuppressLint({"NewApi"})
    public static Point getScreenSize(Context context, Point outSize) {
        Point ret;
        WindowManager wm = (WindowManager) context.getSystemService("window");
        if (outSize == null) {
            ret = new Point();
        } else {
            ret = outSize;
        }
        Display defaultDisplay = wm.getDefaultDisplay();
        if (VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(ret);
        } else {
            ret.x = defaultDisplay.getWidth();
            ret.y = defaultDisplay.getHeight();
        }
        return ret;
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static int getScreenPixWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenPixHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dipToPx(Context context, int dip) {
        return (int) ((((float) dip) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int pxToDip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (imm != null) {
            imm.showSoftInput(view, 1);
        }
    }

    public static void showInputMethod(View view, long delayMillis) {
        new Handler().postDelayed(new 1(view), delayMillis);
    }

    public static boolean isScreenLocked(Context c) {
        return !((KeyguardManager) c.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    public static int getBarHeight(Context context) {
        int sbar = 38;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            sbar = context.getResources().getDimensionPixelSize(Integer.parseInt(c.getField("status_bar_height").get(c.newInstance()).toString()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
}
