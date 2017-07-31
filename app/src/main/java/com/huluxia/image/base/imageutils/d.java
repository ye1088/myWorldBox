package com.huluxia.image.base.imageutils;

import com.huluxia.framework.base.log.HLog;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: TiffUtil */
class d {
    private static final Class<?> tF = d.class;
    public static final int xG = 1296891946;
    public static final int xH = 1229531648;
    public static final int xI = 274;
    public static final int xJ = 3;

    /* compiled from: TiffUtil */
    private static class a {
        boolean xK;
        int xL;
        int xM;

        private a() {
        }
    }

    d() {
    }

    public static int bv(int orientation) {
        switch (orientation) {
            case 0:
            case 1:
                return 0;
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                HLog.info(tF, "Unsupported orientation", new Object[0]);
                return 0;
        }
    }

    public static int c(InputStream is, int length) throws IOException {
        a tiffHeader = new a();
        length = a(is, length, tiffHeader);
        int toSkip = tiffHeader.xM - 8;
        if (length == 0 || toSkip > length) {
            return 0;
        }
        is.skip((long) toSkip);
        return b(is, a(is, length - toSkip, tiffHeader.xK, xI), tiffHeader.xK);
    }

    private static int a(InputStream is, int length, a tiffHeader) throws IOException {
        if (length <= 8) {
            return 0;
        }
        tiffHeader.xL = c.a(is, 4, false);
        length -= 4;
        if (tiffHeader.xL == xH || tiffHeader.xL == xG) {
            tiffHeader.xK = tiffHeader.xL == xH;
            tiffHeader.xM = c.a(is, 4, tiffHeader.xK);
            length -= 4;
            if (tiffHeader.xM >= 8 && tiffHeader.xM - 8 <= length) {
                return length;
            }
            HLog.error(tF, "Invalid offset", new Object[0]);
            return 0;
        }
        HLog.error(tF, "Invalid TIFF header", new Object[0]);
        return 0;
    }

    private static int a(InputStream is, int length, boolean isLittleEndian, int tagToFind) throws IOException {
        if (length < 14) {
            return 0;
        }
        length -= 2;
        int numEntries = c.a(is, 2, isLittleEndian);
        while (true) {
            int numEntries2 = numEntries - 1;
            if (numEntries <= 0 || length < 12) {
                return 0;
            }
            length -= 2;
            if (c.a(is, 2, isLittleEndian) == tagToFind) {
                return length;
            }
            is.skip(10);
            length -= 10;
            numEntries = numEntries2;
        }
    }

    private static int b(InputStream is, int length, boolean isLittleEndian) throws IOException {
        if (length < 10 || c.a(is, 2, isLittleEndian) != 3 || c.a(is, 4, isLittleEndian) != 1) {
            return 0;
        }
        int value = c.a(is, 2, isLittleEndian);
        int padding = c.a(is, 2, isLittleEndian);
        return value;
    }
}
