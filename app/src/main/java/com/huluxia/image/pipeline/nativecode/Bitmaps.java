package com.huluxia.image.pipeline.nativecode;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.huluxia.framework.base.utils.DoNotStrip;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.base.imageutils.a;
import java.nio.ByteBuffer;

@DoNotStrip
public class Bitmaps {
    @DoNotStrip
    private static native void nativeCopyBitmap(Bitmap bitmap, int i, Bitmap bitmap2, int i2, int i3);

    @DoNotStrip
    private static native ByteBuffer nativeGetByteBuffer(Bitmap bitmap, long j, long j2);

    @DoNotStrip
    private static native void nativePinBitmap(Bitmap bitmap);

    @DoNotStrip
    private static native void nativeReleaseByteBuffer(Bitmap bitmap);

    static {
        a.load();
    }

    public static void k(Bitmap bitmap) {
        Preconditions.checkNotNull(bitmap);
        nativePinBitmap(bitmap);
    }

    public static ByteBuffer a(Bitmap bitmap, long start, long size) {
        Preconditions.checkNotNull(bitmap);
        return nativeGetByteBuffer(bitmap, start, size);
    }

    public static void l(Bitmap bitmap) {
        Preconditions.checkNotNull(bitmap);
        nativeReleaseByteBuffer(bitmap);
    }

    public static void a(Bitmap dest, Bitmap src) {
        boolean z;
        boolean z2 = true;
        Preconditions.checkArgument(src.getConfig() == dest.getConfig());
        Preconditions.checkArgument(dest.isMutable());
        if (dest.getWidth() == src.getWidth()) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (dest.getHeight() != src.getHeight()) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        nativeCopyBitmap(dest, dest.getRowBytes(), src, src.getRowBytes(), dest.getHeight());
    }

    @TargetApi(19)
    public static void a(Bitmap bitmap, int width, int height, Config bitmapConfig) {
        Preconditions.checkArgument(bitmap.getAllocationByteCount() >= (width * height) * a.b(bitmapConfig));
        bitmap.reconfigure(width, height, bitmapConfig);
    }
}
