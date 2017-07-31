package com.huluxia.image.base.imageutils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.support.v4.util.Pools.SynchronizedPool;
import android.util.Pair;
import com.huluxia.framework.base.utils.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

/* compiled from: BitmapUtil */
public final class a {
    private static final int POOL_SIZE = 12;
    private static final int xo = 16384;
    private static final SynchronizedPool<ByteBuffer> xp = new SynchronizedPool(12);
    public static final int xq = 1;
    public static final int xr = 2;
    public static final int xs = 4;
    public static final int xt = 2;
    public static final float xu = 2048.0f;

    @SuppressLint({"NewApi"})
    public static int d(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        if (VERSION.SDK_INT > 19) {
            try {
                return bitmap.getAllocationByteCount();
            } catch (NullPointerException e) {
            }
        }
        if (VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getWidth() * bitmap.getRowBytes();
    }

    @Nullable
    public static Pair<Integer, Integer> k(byte[] bytes) {
        return j(new ByteArrayInputStream(bytes));
    }

    @Nullable
    public static Pair<Integer, Integer> j(InputStream is) {
        Pair<Integer, Integer> pair = null;
        Preconditions.checkNotNull(is);
        ByteBuffer byteBuffer = (ByteBuffer) xp.acquire();
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(16384);
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            options.inTempStorage = byteBuffer.array();
            BitmapFactory.decodeStream(is, null, options);
            if (!(options.outWidth == -1 || options.outHeight == -1)) {
                pair = new Pair(Integer.valueOf(options.outWidth), Integer.valueOf(options.outHeight));
            }
            xp.release(byteBuffer);
            return pair;
        } catch (Throwable th) {
            xp.release(byteBuffer);
        }
    }

    public static int b(Config bitmapConfig) {
        switch (1.$SwitchMap$android$graphics$Bitmap$Config[bitmapConfig.ordinal()]) {
            case 1:
                return 4;
            case 2:
                return 1;
            case 3:
            case 4:
                return 2;
            default:
                throw new UnsupportedOperationException("The provided Bitmap.Config is not supported");
        }
    }

    public static int c(int width, int height, Config bitmapConfig) {
        return (width * height) * b(bitmapConfig);
    }
}
