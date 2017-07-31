package com.huluxia.image.base.imagepipeline.bitmaps;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.huluxia.framework.base.utils.Preconditions;
import javax.annotation.Nullable;

/* compiled from: PlatformBitmapFactory */
public abstract class a {
    private static a vH;

    /* compiled from: PlatformBitmapFactory */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config = new int[Config.values().length];

        static {
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.RGB_565.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ALPHA_8.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ARGB_8888.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* compiled from: PlatformBitmapFactory */
    public interface a {
        void c(Bitmap bitmap, @Nullable Object obj);
    }

    public abstract com.huluxia.image.core.common.references.a<Bitmap> b(int i, int i2, Config config);

    public com.huluxia.image.core.common.references.a<Bitmap> a(int width, int height, Config bitmapConfig) {
        return a(width, height, bitmapConfig, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> j(int width, int height) {
        return a(width, height, Config.ARGB_8888);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(int width, int height, Config bitmapConfig, @Nullable Object callerContext) {
        com.huluxia.image.core.common.references.a<Bitmap> reference = b(width, height, bitmapConfig);
        b((Bitmap) reference.get(), callerContext);
        return reference;
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(int width, int height, @Nullable Object callerContext) {
        return a(width, height, Config.ARGB_8888, callerContext);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap source) {
        return a(source, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap source, @Nullable Object callerContext) {
        return a(source, 0, 0, source.getWidth(), source.getHeight(), callerContext);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap source, int x, int y, int width, int height) {
        return a(source, x, y, width, height, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap source, int x, int y, int width, int height, @Nullable Object callerContext) {
        return a(source, x, y, width, height, null, false, callerContext);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap source, int x, int y, int width, int height, @Nullable Matrix matrix, boolean filter) {
        return a(source, x, y, width, height, matrix, filter, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap source, int destinationWidth, int destinationHeight, boolean filter) {
        return a(source, destinationWidth, destinationHeight, filter, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap source, int destinationWidth, int destinationHeight, boolean filter, @Nullable Object callerContext) {
        k(destinationWidth, destinationHeight);
        Matrix matrix = new Matrix();
        int width = source.getWidth();
        int height = source.getHeight();
        matrix.setScale(((float) destinationWidth) / ((float) width), ((float) destinationHeight) / ((float) height));
        return a(source, 0, 0, width, height, matrix, filter, callerContext);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(Bitmap source, int x, int y, int width, int height, @Nullable Matrix matrix, boolean filter, @Nullable Object callerContext) {
        com.huluxia.image.core.common.references.a<Bitmap> bitmapRef;
        Paint paint;
        Preconditions.checkNotNull(source, "Source bitmap cannot be null");
        l(x, y);
        k(width, height);
        b(source, x, y, width, height);
        int newWidth = width;
        int newHeight = height;
        Canvas canvas = new Canvas();
        Rect rect = new Rect(x, y, x + width, y + height);
        RectF rectF = new RectF(0.0f, 0.0f, (float) width, (float) height);
        Config newConfig = b(source);
        if (matrix == null || matrix.isIdentity()) {
            bitmapRef = a(newWidth, newHeight, newConfig, source.hasAlpha(), callerContext);
            paint = null;
        } else {
            Config config;
            boolean transformed = !matrix.rectStaysRect();
            RectF deviceRectangle = new RectF();
            matrix.mapRect(deviceRectangle, rectF);
            newWidth = Math.round(deviceRectangle.width());
            newHeight = Math.round(deviceRectangle.height());
            if (transformed) {
                config = Config.ARGB_8888;
            } else {
                config = newConfig;
            }
            boolean z = transformed || source.hasAlpha();
            bitmapRef = a(newWidth, newHeight, config, z, callerContext);
            canvas.translate(-deviceRectangle.left, -deviceRectangle.top);
            canvas.concat(matrix);
            paint = new Paint();
            paint.setFilterBitmap(filter);
            if (transformed) {
                paint.setAntiAlias(true);
            }
        }
        Bitmap bitmap = (Bitmap) bitmapRef.get();
        bitmap.setDensity(source.getDensity());
        if (VERSION.SDK_INT >= 12) {
            bitmap.setHasAlpha(source.hasAlpha());
        }
        if (VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(source.isPremultiplied());
        }
        canvas.setBitmap(bitmap);
        canvas.drawBitmap(source, rect, rectF, paint);
        canvas.setBitmap(null);
        return bitmapRef;
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(DisplayMetrics display, int width, int height, Config config) {
        return a(display, width, height, config, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(DisplayMetrics display, int width, int height, Config config, @Nullable Object callerContext) {
        return a(display, width, height, config, true, callerContext);
    }

    private com.huluxia.image.core.common.references.a<Bitmap> a(int width, int height, Config config, boolean hasAlpha) {
        return a(width, height, config, hasAlpha, null);
    }

    private com.huluxia.image.core.common.references.a<Bitmap> a(int width, int height, Config config, boolean hasAlpha, @Nullable Object callerContext) {
        return a(null, width, height, config, hasAlpha, callerContext);
    }

    private com.huluxia.image.core.common.references.a<Bitmap> a(DisplayMetrics display, int width, int height, Config config, boolean hasAlpha) {
        return a(display, width, height, config, hasAlpha, null);
    }

    private com.huluxia.image.core.common.references.a<Bitmap> a(DisplayMetrics display, int width, int height, Config config, boolean hasAlpha, @Nullable Object callerContext) {
        k(width, height);
        com.huluxia.image.core.common.references.a<Bitmap> bitmapRef = b(width, height, config);
        Bitmap bitmap = (Bitmap) bitmapRef.get();
        if (display != null) {
            bitmap.setDensity(display.densityDpi);
        }
        if (VERSION.SDK_INT >= 12) {
            bitmap.setHasAlpha(hasAlpha);
        }
        if (config == Config.ARGB_8888 && !hasAlpha) {
            bitmap.eraseColor(-16777216);
        }
        b((Bitmap) bitmapRef.get(), callerContext);
        return bitmapRef;
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(int[] colors, int width, int height, Config config) {
        return a(colors, width, height, config, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(int[] colors, int width, int height, Config config, @Nullable Object callerContext) {
        com.huluxia.image.core.common.references.a<Bitmap> bitmapRef = b(width, height, config);
        ((Bitmap) bitmapRef.get()).setPixels(colors, 0, width, 0, 0, width, height);
        b((Bitmap) bitmapRef.get(), callerContext);
        return bitmapRef;
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(DisplayMetrics display, int[] colors, int width, int height, Config config) {
        return a(display, colors, width, height, config, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(DisplayMetrics display, int[] colors, int width, int height, Config config, @Nullable Object callerContext) {
        return a(display, colors, 0, width, width, height, config, callerContext);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(DisplayMetrics display, int[] colors, int offset, int stride, int width, int height, Config config) {
        return a(display, colors, offset, stride, width, height, config, null);
    }

    public com.huluxia.image.core.common.references.a<Bitmap> a(DisplayMetrics display, int[] colors, int offset, int stride, int width, int height, Config config, @Nullable Object callerContext) {
        com.huluxia.image.core.common.references.a<Bitmap> bitmapRef = a(display, width, height, config, callerContext);
        ((Bitmap) bitmapRef.get()).setPixels(colors, offset, stride, 0, 0, width, height);
        return bitmapRef;
    }

    private static Config b(Bitmap source) {
        Config finalConfig = Config.ARGB_8888;
        Config sourceConfig = source.getConfig();
        if (sourceConfig == null) {
            return finalConfig;
        }
        switch (AnonymousClass1.$SwitchMap$android$graphics$Bitmap$Config[sourceConfig.ordinal()]) {
            case 1:
                return Config.RGB_565;
            case 2:
                return Config.ALPHA_8;
            default:
                return Config.ARGB_8888;
        }
    }

    private static void k(int width, int height) {
        boolean z;
        boolean z2 = true;
        if (width > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "width must be > 0");
        if (height <= 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "height must be > 0");
    }

    private static void l(int x, int y) {
        boolean z;
        boolean z2 = true;
        if (x >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "x must be >= 0");
        if (y < 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "y must be >= 0");
    }

    private static void b(Bitmap source, int x, int y, int width, int height) {
        boolean z = true;
        Preconditions.checkArgument(x + width <= source.getWidth(), "x + width must be <= bitmap.width()");
        if (y + height > source.getHeight()) {
            z = false;
        }
        Preconditions.checkArgument(z, "y + height must be <= bitmap.height()");
    }

    public void a(a bitmapCreationObserver) {
        if (vH == null) {
            vH = bitmapCreationObserver;
        }
    }

    public void b(Bitmap bitmap, @Nullable Object callerContext) {
        if (vH != null) {
            vH.c(bitmap, callerContext);
        }
    }
}
