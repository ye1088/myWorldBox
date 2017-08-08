package com.MCWorld.image.drawee.generic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.MCWorld.image.drawee.drawable.AutoRotateDrawable;
import com.MCWorld.q.j;
import javax.annotation.Nullable;

/* compiled from: GenericDraweeHierarchyInflater */
public class c {
    public static a a(Context context, @Nullable AttributeSet attrs) {
        return b(context, attrs).kQ();
    }

    public static b b(Context context, @Nullable AttributeSet attrs) {
        return a(new b(context.getResources()), context, attrs);
    }

    public static b a(b builder, Context context, @Nullable AttributeSet attrs) {
        int progressBarAutoRotateInterval = 0;
        int roundedCornerRadius = 0;
        boolean roundTopLeft = true;
        boolean roundTopRight = true;
        boolean roundBottomLeft = true;
        boolean roundBottomRight = true;
        if (attrs != null) {
            TypedArray gdhAttrs = context.obtainStyledAttributes(attrs, j.GenericDraweeHierarchy);
            try {
                int indexCount = gdhAttrs.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int attr = gdhAttrs.getIndex(i);
                    if (attr == j.GenericDraweeHierarchy_actualImageScaleType) {
                        builder.g(a(gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_placeholderImage) {
                        builder.o(a(context, gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_pressedStateOverlayImage) {
                        builder.u(a(context, gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_progressBarImage) {
                        builder.r(a(context, gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_fadeDuration) {
                        builder.cb(gdhAttrs.getInt(attr, 0));
                    } else if (attr == j.GenericDraweeHierarchy_viewAspectRatio) {
                        builder.i(gdhAttrs.getFloat(attr, 0.0f));
                    } else if (attr == j.GenericDraweeHierarchy_placeholderImageScaleType) {
                        builder.c(a(gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_retryImage) {
                        builder.p(a(context, gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_retryImageScaleType) {
                        builder.d(a(gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_failureImage) {
                        builder.q(a(context, gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_failureImageScaleType) {
                        builder.e(a(gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_progressBarImageScaleType) {
                        builder.f(a(gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_progressBarAutoRotateInterval) {
                        progressBarAutoRotateInterval = gdhAttrs.getInteger(attr, progressBarAutoRotateInterval);
                    } else if (attr == j.GenericDraweeHierarchy_backgroundImage) {
                        builder.s(a(context, gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_overlayImage) {
                        builder.t(a(context, gdhAttrs, attr));
                    } else if (attr == j.GenericDraweeHierarchy_roundAsCircle) {
                        a(builder).ad(gdhAttrs.getBoolean(attr, false));
                    } else if (attr == j.GenericDraweeHierarchy_roundedCornerRadius) {
                        roundedCornerRadius = gdhAttrs.getDimensionPixelSize(attr, roundedCornerRadius);
                    } else if (attr == j.GenericDraweeHierarchy_roundTopLeft) {
                        roundTopLeft = gdhAttrs.getBoolean(attr, roundTopLeft);
                    } else if (attr == j.GenericDraweeHierarchy_roundTopRight) {
                        roundTopRight = gdhAttrs.getBoolean(attr, roundTopRight);
                    } else if (attr == j.GenericDraweeHierarchy_roundBottomLeft) {
                        roundBottomLeft = gdhAttrs.getBoolean(attr, roundBottomLeft);
                    } else if (attr == j.GenericDraweeHierarchy_roundBottomRight) {
                        roundBottomRight = gdhAttrs.getBoolean(attr, roundBottomRight);
                    } else if (attr == j.GenericDraweeHierarchy_roundWithOverlayColor) {
                        a(builder).cg(gdhAttrs.getColor(attr, 0));
                    } else if (attr == j.GenericDraweeHierarchy_roundingBorderWidth) {
                        a(builder).l((float) gdhAttrs.getDimensionPixelSize(attr, 0));
                    } else if (attr == j.GenericDraweeHierarchy_roundingBorderColor) {
                        a(builder).ch(gdhAttrs.getColor(attr, 0));
                    } else if (attr == j.GenericDraweeHierarchy_roundingBorderPadding) {
                        a(builder).m((float) gdhAttrs.getDimensionPixelSize(attr, 0));
                    }
                }
            } finally {
                gdhAttrs.recycle();
            }
        }
        if (builder.kK() != null && progressBarAutoRotateInterval > 0) {
            builder.r(new AutoRotateDrawable(builder.kK(), progressBarAutoRotateInterval));
        }
        if (roundedCornerRadius > 0) {
            a(builder).a(roundTopLeft ? (float) roundedCornerRadius : 0.0f, roundTopRight ? (float) roundedCornerRadius : 0.0f, roundBottomRight ? (float) roundedCornerRadius : 0.0f, roundBottomLeft ? (float) roundedCornerRadius : 0.0f);
        }
        return builder;
    }

    private static RoundingParams a(b builder) {
        if (builder.kB() == null) {
            builder.b(new RoundingParams());
        }
        return builder.kB();
    }

    @Nullable
    private static Drawable a(Context context, TypedArray gdhAttrs, int attrId) {
        int resourceId = gdhAttrs.getResourceId(attrId, 0);
        return resourceId == 0 ? null : context.getResources().getDrawable(resourceId);
    }

    @Nullable
    private static com.MCWorld.image.drawee.drawable.o.c a(TypedArray gdhAttrs, int attrId) {
        switch (gdhAttrs.getInt(attrId, -2)) {
            case -1:
                return null;
            case 0:
                return com.MCWorld.image.drawee.drawable.o.c.CE;
            case 1:
                return com.MCWorld.image.drawee.drawable.o.c.CF;
            case 2:
                return com.MCWorld.image.drawee.drawable.o.c.CG;
            case 3:
                return com.MCWorld.image.drawee.drawable.o.c.CH;
            case 4:
                return com.MCWorld.image.drawee.drawable.o.c.CI;
            case 5:
                return com.MCWorld.image.drawee.drawable.o.c.CJ;
            case 6:
                return com.MCWorld.image.drawee.drawable.o.c.CK;
            case 7:
                return com.MCWorld.image.drawee.drawable.o.c.CL;
            default:
                throw new RuntimeException("XML attribute not specified!");
        }
    }
}
