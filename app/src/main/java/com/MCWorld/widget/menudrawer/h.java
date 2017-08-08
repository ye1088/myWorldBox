package com.MCWorld.widget.menudrawer;

import android.os.Build.VERSION;
import android.view.View;

/* compiled from: ViewHelper */
final class h {
    private h() {
    }

    public static int A(View v) {
        if (MenuDrawer.bAU) {
            return (int) (((float) v.getLeft()) + v.getTranslationX());
        }
        return v.getLeft();
    }

    public static int B(View v) {
        if (MenuDrawer.bAU) {
            return (int) (((float) v.getTop()) + v.getTranslationY());
        }
        return v.getTop();
    }

    public static int C(View v) {
        if (MenuDrawer.bAU) {
            return (int) (((float) v.getRight()) + v.getTranslationX());
        }
        return v.getRight();
    }

    public static int D(View v) {
        if (MenuDrawer.bAU) {
            return (int) (((float) v.getBottom()) + v.getTranslationY());
        }
        return v.getBottom();
    }

    public static int getLayoutDirection(View v) {
        if (VERSION.SDK_INT >= 17) {
            return v.getLayoutDirection();
        }
        return 0;
    }
}
