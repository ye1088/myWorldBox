package com.huluxia.image.drawee.generic;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.drawee.drawable.RoundedCornersDrawable;
import com.huluxia.image.drawee.drawable.g;
import com.huluxia.image.drawee.drawable.h;
import com.huluxia.image.drawee.drawable.k;
import com.huluxia.image.drawee.drawable.l;
import com.huluxia.image.drawee.drawable.m;
import com.huluxia.image.drawee.drawable.n;
import com.huluxia.image.drawee.drawable.o.c;
import com.huluxia.image.drawee.generic.RoundingParams.RoundingMethod;
import javax.annotation.Nullable;

/* compiled from: WrappingUtils */
public class e {
    private static final Drawable DA = new ColorDrawable(0);

    @Nullable
    public static Drawable j(@Nullable Drawable drawable, @Nullable c scaleType) {
        return a(drawable, scaleType, null);
    }

    @Nullable
    static Drawable a(@Nullable Drawable drawable, @Nullable c scaleType, @Nullable PointF focusPoint) {
        if (drawable == null || scaleType == null) {
            return drawable;
        }
        Drawable scaleTypeDrawable = new n(drawable, scaleType);
        if (focusPoint == null) {
            return scaleTypeDrawable;
        }
        scaleTypeDrawable.a(focusPoint);
        return scaleTypeDrawable;
    }

    @Nullable
    static Drawable a(@Nullable Drawable drawable, @Nullable Matrix matrix) {
        return (drawable == null || matrix == null) ? drawable : new h(drawable, matrix);
    }

    static n a(com.huluxia.image.drawee.drawable.c parent, c scaleType) {
        Drawable child = j(parent.d(DA), scaleType);
        parent.d(child);
        Preconditions.checkNotNull(child, "Parent has no child drawable!");
        return (n) child;
    }

    static void a(com.huluxia.image.drawee.drawable.c parent, @Nullable RoundingParams roundingParams) {
        Drawable child = parent.getDrawable();
        if (roundingParams == null || roundingParams.kT() != RoundingMethod.OVERLAY_COLOR) {
            if (child instanceof RoundedCornersDrawable) {
                parent.d(((RoundedCornersDrawable) child).g(DA));
                DA.setCallback(null);
            }
        } else if (child instanceof RoundedCornersDrawable) {
            k roundedCornersDrawable = (RoundedCornersDrawable) child;
            a(roundedCornersDrawable, roundingParams);
            roundedCornersDrawable.bQ(roundingParams.kn());
        } else {
            parent.d(a(parent.d(DA), roundingParams));
        }
    }

    static void a(com.huluxia.image.drawee.drawable.c parent, @Nullable RoundingParams roundingParams, Resources resources) {
        parent = a(parent);
        Drawable child = parent.getDrawable();
        if (roundingParams == null || roundingParams.kT() != RoundingMethod.BITMAP_ONLY) {
            if (child instanceof k) {
                a((k) child);
            }
        } else if (child instanceof k) {
            a((k) child, roundingParams);
        } else if (child != null) {
            parent.d(DA);
            parent.d(b(child, roundingParams, resources));
        }
    }

    static Drawable a(@Nullable Drawable drawable, @Nullable RoundingParams roundingParams) {
        if (drawable == null || roundingParams == null || roundingParams.kT() != RoundingMethod.OVERLAY_COLOR) {
            return drawable;
        }
        k roundedCornersDrawable = new RoundedCornersDrawable(drawable);
        a(roundedCornersDrawable, roundingParams);
        roundedCornersDrawable.bQ(roundingParams.kn());
        return roundedCornersDrawable;
    }

    static Drawable a(@Nullable Drawable drawable, @Nullable RoundingParams roundingParams, Resources resources) {
        if (drawable == null || roundingParams == null || roundingParams.kT() != RoundingMethod.BITMAP_ONLY) {
            return drawable;
        }
        if (!(drawable instanceof g)) {
            return b(drawable, roundingParams, resources);
        }
        com.huluxia.image.drawee.drawable.c parent = a((g) drawable);
        parent.d(b(parent.d(DA), roundingParams, resources));
        return drawable;
    }

    private static Drawable b(Drawable drawable, RoundingParams roundingParams, Resources resources) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            k roundedBitmapDrawable = new l(resources, bitmapDrawable.getBitmap(), bitmapDrawable.getPaint());
            a(roundedBitmapDrawable, roundingParams);
            return roundedBitmapDrawable;
        } else if (!(drawable instanceof ColorDrawable) || VERSION.SDK_INT < 11) {
            return drawable;
        } else {
            k roundedColorDrawable = m.a((ColorDrawable) drawable);
            a(roundedColorDrawable, roundingParams);
            return roundedColorDrawable;
        }
    }

    static void a(k rounded, RoundingParams roundingParams) {
        rounded.ab(roundingParams.kR());
        rounded.a(roundingParams.kS());
        rounded.a(roundingParams.kg(), roundingParams.kh());
        rounded.g(roundingParams.ki());
    }

    static void a(k rounded) {
        rounded.ab(false);
        rounded.setRadius(0.0f);
        rounded.a(0, 0.0f);
        rounded.g(0.0f);
    }

    static com.huluxia.image.drawee.drawable.c a(com.huluxia.image.drawee.drawable.c parent) {
        while (true) {
            Drawable child = parent.getDrawable();
            if (child == parent || !(child instanceof com.huluxia.image.drawee.drawable.c)) {
                return parent;
            }
            parent = (com.huluxia.image.drawee.drawable.c) child;
        }
        return parent;
    }
}
