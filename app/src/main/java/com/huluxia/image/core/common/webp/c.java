package com.huluxia.image.core.common.webp;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import org.apache.http.protocol.HTTP;

/* compiled from: WebpSupportStatus */
public class c {
    private static final int va = 20;
    private static final int vb = 21;
    public static final boolean za = (VERSION.SDK_INT <= 17);
    public static final boolean zb;
    public static final boolean zc = iM();
    public static b zd = null;
    public static boolean ze = false;
    private static boolean zf = false;
    private static final String zg = "UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==";
    private static final byte[] zh = bk("RIFF");
    private static final byte[] zi = bk("WEBP");
    private static final byte[] zj = bk("VP8 ");
    private static final byte[] zk = bk("VP8L");
    private static final byte[] zl = bk("VP8X");

    static {
        boolean z = true;
        if (VERSION.SDK_INT < 14) {
            z = false;
        }
        zb = z;
    }

    public static b iL() {
        if (zf) {
            return zd;
        }
        b loadedWebpBitmapFactory = null;
        try {
            loadedWebpBitmapFactory = (b) Class.forName("com.facebook.webpsupport.WebpBitmapFactoryImpl").newInstance();
        } catch (Throwable th) {
        }
        zf = true;
        return loadedWebpBitmapFactory;
    }

    private static byte[] bk(String value) {
        try {
            return value.getBytes(HTTP.ASCII);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("ASCII not found!", uee);
        }
    }

    private static boolean iM() {
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (VERSION.SDK_INT == 17) {
            byte[] decodedBytes = Base64.decode(zg, 0);
            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length, opts);
            if (!(opts.outHeight == 1 && opts.outWidth == 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean e(byte[] imageHeaderBytes, int offset, int headerSize) {
        if (i(imageHeaderBytes, offset)) {
            return zb;
        }
        if (j(imageHeaderBytes, offset)) {
            return zc;
        }
        if (!f(imageHeaderBytes, offset, headerSize) || h(imageHeaderBytes, offset)) {
            return false;
        }
        return zc;
    }

    public static boolean h(byte[] imageHeaderBytes, int offset) {
        boolean isVp8x = b(imageHeaderBytes, offset + 12, zl);
        boolean hasAnimationBit;
        if ((imageHeaderBytes[offset + 20] & 2) == 2) {
            hasAnimationBit = true;
        } else {
            hasAnimationBit = false;
        }
        if (isVp8x && hasAnimationBit) {
            return true;
        }
        return false;
    }

    public static boolean i(byte[] imageHeaderBytes, int offset) {
        return b(imageHeaderBytes, offset + 12, zj);
    }

    public static boolean j(byte[] imageHeaderBytes, int offset) {
        return b(imageHeaderBytes, offset + 12, zk);
    }

    public static boolean f(byte[] imageHeaderBytes, int offset, int headerSize) {
        return headerSize >= 21 && b(imageHeaderBytes, offset + 12, zl);
    }

    public static boolean k(byte[] imageHeaderBytes, int offset) {
        boolean isVp8x = b(imageHeaderBytes, offset + 12, zl);
        boolean hasAlphaBit;
        if ((imageHeaderBytes[offset + 20] & 16) == 16) {
            hasAlphaBit = true;
        } else {
            hasAlphaBit = false;
        }
        if (isVp8x && hasAlphaBit) {
            return true;
        }
        return false;
    }

    public static boolean g(byte[] imageHeaderBytes, int offset, int headerSize) {
        return headerSize >= 20 && b(imageHeaderBytes, offset, zh) && b(imageHeaderBytes, offset + 8, zi);
    }

    private static boolean b(byte[] byteArray, int offset, byte[] pattern) {
        if (pattern == null || byteArray == null || pattern.length + offset > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[i + offset] != pattern[i]) {
                return false;
            }
        }
        return true;
    }
}
