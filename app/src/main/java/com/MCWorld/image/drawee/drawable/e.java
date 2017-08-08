package com.MCWorld.image.drawee.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import javax.annotation.Nullable;

/* compiled from: DrawableUtils */
public class e {
    @Nullable
    public static Drawable f(Drawable drawable) {
        if (drawable instanceof b) {
            return ((b) drawable).jO();
        }
        ConstantState constantState = drawable.getConstantState();
        return constantState != null ? constantState.newDrawable() : null;
    }

    public static void b(Drawable to, Drawable from) {
        if (from != null && to != null && to != from) {
            to.setBounds(from.getBounds());
            to.setChangingConfigurations(from.getChangingConfigurations());
            to.setLevel(from.getLevel());
            to.setVisible(from.isVisible(), false);
            to.setState(from.getState());
        }
    }

    public static void a(Drawable drawable, d properties) {
        if (drawable != null && properties != null) {
            properties.e(drawable);
        }
    }

    public static void a(Drawable drawable, @Nullable Callback callback, @Nullable r transformCallback) {
        if (drawable != null) {
            drawable.setCallback(callback);
            if (drawable instanceof q) {
                ((q) drawable).a(transformCallback);
            }
        }
    }

    public static int r(int color, int alpha) {
        if (alpha == 255) {
            return color;
        }
        if (alpha == 0) {
            return color & 16777215;
        }
        return ((((color >>> 24) * (alpha + (alpha >> 7))) >> 8) << 24) | (16777215 & color);
    }

    public static int bG(int color) {
        int colorAlpha = color >>> 24;
        if (colorAlpha == 255) {
            return -1;
        }
        if (colorAlpha == 0) {
            return -2;
        }
        return -3;
    }
}
