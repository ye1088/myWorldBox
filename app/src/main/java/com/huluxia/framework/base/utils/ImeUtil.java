package com.huluxia.framework.base.utils;

import android.app.Activity;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ImeUtil {
    public static void hideIME(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            hideIME(activity, view);
        }
    }

    public static void hideIME(Activity activity, View v) {
        if (activity != null && v != null) {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void showIME(Activity activity, View view) {
        if (view == null) {
            view = activity.getCurrentFocus();
            if (view == null) {
                return;
            }
        }
        ((InputMethodManager) activity.getSystemService("input_method")).showSoftInput(view, 2);
    }

    public static void showIME(Activity activity, View view, int flag) {
        if (view == null) {
            view = activity.getCurrentFocus();
            if (view == null) {
                return;
            }
        }
        ((InputMethodManager) activity.getSystemService("input_method")).showSoftInput(view, flag);
    }

    public static void showIMEDelay(Activity activity, View view, long time) {
        new SafeDispatchHandler(Looper.getMainLooper()).postDelayed(new 1(activity, view), time);
    }
}
