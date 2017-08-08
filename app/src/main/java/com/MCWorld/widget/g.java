package com.MCWorld.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacpp.avutil;

/* compiled from: StatusBarUtil */
public class g {
    public static final int bux = 112;

    public static void f(Activity activity, int color) {
        c(activity, color, 112);
    }

    public static void c(Activity activity, int color, int statusBarAlpha) {
        if (VERSION.SDK_INT >= 21) {
            activity.getWindow().addFlags(Integer.MIN_VALUE);
            activity.getWindow().clearFlags(avformat.AVFMT_SEEK_TO_PTS);
            activity.getWindow().setStatusBarColor(aJ(color, statusBarAlpha));
        } else if (VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(avformat.AVFMT_SEEK_TO_PTS);
            ((ViewGroup) activity.getWindow().getDecorView()).addView(d(activity, color, statusBarAlpha));
            z(activity);
        }
    }

    public static void g(Activity activity, int color) {
        c(activity, color, 0);
    }

    public static void h(Activity activity, int color) {
        if (VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(avformat.AVFMT_SEEK_TO_PTS);
            ((ViewGroup) activity.getWindow().getDecorView()).addView(k(activity, color));
            z(activity);
        }
    }

    public static void w(Activity activity) {
        i(activity, 112);
    }

    public static void i(Activity activity, int statusBarAlpha) {
        if (VERSION.SDK_INT >= 19) {
            x(activity);
            j(activity, statusBarAlpha);
        }
    }

    public static void x(Activity activity) {
        if (VERSION.SDK_INT >= 19) {
            A(activity);
            z(activity);
        }
    }

    public static void y(Activity activity) {
        if (VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(avformat.AVFMT_SEEK_TO_PTS);
            z(activity);
        }
    }

    public static void a(Activity activity, DrawerLayout drawerLayout, int color) {
        a(activity, drawerLayout, color, 112);
    }

    public static void b(Activity activity, DrawerLayout drawerLayout, int color) {
        a(activity, drawerLayout, color, 0);
    }

    public static void a(Activity activity, DrawerLayout drawerLayout, int color, int statusBarAlpha) {
        if (VERSION.SDK_INT >= 19) {
            if (VERSION.SDK_INT >= 21) {
                activity.getWindow().addFlags(Integer.MIN_VALUE);
                activity.getWindow().clearFlags(avformat.AVFMT_SEEK_TO_PTS);
                activity.getWindow().setStatusBarColor(0);
            } else {
                activity.getWindow().addFlags(avformat.AVFMT_SEEK_TO_PTS);
            }
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.addView(k(activity, color), 0);
            if (!((contentLayout instanceof LinearLayout) || contentLayout.getChildAt(1) == null)) {
                contentLayout.getChildAt(1).setPadding(0, bD(activity), 0, 0);
            }
            if (VERSION.SDK_INT >= 14) {
                ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
                drawerLayout.setFitsSystemWindows(false);
                contentLayout.setFitsSystemWindows(false);
                contentLayout.setClipToPadding(true);
                drawer.setFitsSystemWindows(false);
            }
            j(activity, statusBarAlpha);
        }
    }

    public static void c(Activity activity, DrawerLayout drawerLayout, int color) {
        if (VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(avformat.AVFMT_SEEK_TO_PTS);
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.addView(k(activity, color), 0);
            if (!((contentLayout instanceof LinearLayout) || contentLayout.getChildAt(1) == null)) {
                contentLayout.getChildAt(1).setPadding(0, bD(activity), 0, 0);
            }
            ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
            drawerLayout.setFitsSystemWindows(false);
            contentLayout.setFitsSystemWindows(false);
            contentLayout.setClipToPadding(true);
            drawer.setFitsSystemWindows(false);
        }
    }

    public static void a(Activity activity, DrawerLayout drawerLayout) {
        d(activity, drawerLayout, 112);
    }

    public static void d(Activity activity, DrawerLayout drawerLayout, int statusBarAlpha) {
        if (VERSION.SDK_INT >= 19) {
            b(activity, drawerLayout);
            j(activity, statusBarAlpha);
        }
    }

    public static void b(Activity activity, DrawerLayout drawerLayout) {
        if (VERSION.SDK_INT >= 19) {
            if (VERSION.SDK_INT >= 21) {
                activity.getWindow().addFlags(Integer.MIN_VALUE);
                activity.getWindow().clearFlags(avformat.AVFMT_SEEK_TO_PTS);
                activity.getWindow().setStatusBarColor(0);
            } else {
                activity.getWindow().addFlags(avformat.AVFMT_SEEK_TO_PTS);
            }
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            if (!((contentLayout instanceof LinearLayout) || contentLayout.getChildAt(1) == null)) {
                contentLayout.getChildAt(1).setPadding(0, bD(activity), 0, 0);
            }
            if (VERSION.SDK_INT >= 14) {
                ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
                drawerLayout.setFitsSystemWindows(false);
                contentLayout.setFitsSystemWindows(false);
                contentLayout.setClipToPadding(true);
                drawer.setFitsSystemWindows(false);
            }
        }
    }

    public static void c(Activity activity, DrawerLayout drawerLayout) {
        if (VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(avformat.AVFMT_SEEK_TO_PTS);
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.setFitsSystemWindows(true);
            contentLayout.setClipToPadding(true);
            ((ViewGroup) drawerLayout.getChildAt(1)).setFitsSystemWindows(false);
            drawerLayout.setFitsSystemWindows(false);
        }
    }

    private static void j(Activity activity, int statusBarAlpha) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(16908290);
        if (contentView.getChildCount() > 1) {
            contentView.removeViewAt(1);
        }
        contentView.addView(l(activity, statusBarAlpha));
    }

    private static View k(Activity activity, int color) {
        View statusBarView = new View(activity);
        statusBarView.setLayoutParams(new LayoutParams(-1, bD(activity)));
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

    private static View d(Activity activity, int color, int alpha) {
        View statusBarView = new View(activity);
        statusBarView.setLayoutParams(new LayoutParams(-1, bD(activity)));
        statusBarView.setBackgroundColor(aJ(color, alpha));
        return statusBarView;
    }

    private static void z(Activity activity) {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(16908290)).getChildAt(0);
        if (VERSION.SDK_INT >= 14) {
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    @TargetApi(19)
    private static void A(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            activity.getWindow().addFlags(Integer.MIN_VALUE);
            activity.getWindow().clearFlags(avformat.AVFMT_SEEK_TO_PTS);
            activity.getWindow().addFlags(avutil.AV_CPU_FLAG_AVXSLOW);
            activity.getWindow().setStatusBarColor(0);
            return;
        }
        activity.getWindow().addFlags(avformat.AVFMT_SEEK_TO_PTS);
    }

    private static View l(Activity activity, int alpha) {
        View statusBarView = new View(activity);
        statusBarView.setLayoutParams(new LayoutParams(-1, bD(activity)));
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        return statusBarView;
    }

    private static int bD(Context context) {
        return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
    }

    private static int aJ(int color, int alpha) {
        float a = 1.0f - (((float) alpha) / 255.0f);
        return ((-16777216 | (((int) (((double) (((float) ((color >> 16) & 255)) * a)) + 0.5d)) << 16)) | (((int) (((double) (((float) ((color >> 8) & 255)) * a)) + 0.5d)) << 8)) | ((int) (((double) (((float) (color & 255)) * a)) + 0.5d));
    }
}
