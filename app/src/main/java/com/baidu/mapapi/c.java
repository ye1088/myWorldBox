package com.baidu.mapapi;

public class c {
    private static char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte[] b = new byte[256];

    static {
        int i;
        for (i = 0; i < 256; i++) {
            b[i] = (byte) -1;
        }
        for (i = 65; i <= 90; i++) {
            b[i] = (byte) (i - 65);
        }
        for (i = 97; i <= 122; i++) {
            b[i] = (byte) ((i + 26) - 97);
        }
        for (i = 48; i <= 57; i++) {
            b[i] = (byte) ((i + 52) - 48);
        }
        b[43] = (byte) 62;
        b[47] = (byte) 63;
    }

    public static char[] a(char[] cArr) {
        char[] cArr2 = new char[(((cArr.length + 2) / 3) * 4)];
        int i = 0;
        int i2 = 0;
        while (i2 < cArr.length) {
            Object obj;
            Object obj2;
            int i3 = (cArr[i2] & 255) << 8;
            if (i2 + 1 < cArr.length) {
                i3 |= cArr[i2 + 1] & 255;
                obj = 1;
            } else {
                obj = null;
            }
            i3 <<= 8;
            if (i2 + 2 < cArr.length) {
                i3 |= cArr[i2 + 2] & 255;
                obj2 = 1;
            } else {
                obj2 = null;
            }
            cArr2[i + 3] = a[obj2 != null ? i3 & 63 : 64];
            int i4 = i3 >> 6;
            cArr2[i + 2] = a[obj != null ? i4 & 63 : 64];
            i3 = i4 >> 6;
            cArr2[i + 1] = a[i3 & 63];
            cArr2[i + 0] = a[(i3 >> 6) & 63];
            i2 += 3;
            i += 4;
        }
        return cArr2;
    }

    public static char[] b(char[] cArr) {
        int i = 0;
        int length = ((cArr.length + 3) / 4) * 3;
        if (cArr.length > 0 && cArr[cArr.length - 1] == '=') {
            length--;
        }
        if (cArr.length > 1 && cArr[cArr.length - 2] == '=') {
            length--;
        }
        char[] cArr2 = new char[length];
        int i2 = 0;
        int i3 = 0;
        for (char c : cArr) {
            byte b = b[c & 255];
            if (b >= (byte) 0) {
                int i4 = i2 << 6;
                i2 = i3 + 6;
                i3 = i4 | b;
                if (i2 >= 8) {
                    int i5 = i2 - 8;
                    i2 = i + 1;
                    cArr2[i] = (char) ((i3 >> i5) & 255);
                    i = i2;
                    i2 = i3;
                    i3 = i5;
                } else {
                    int i6 = i3;
                    i3 = i2;
                    i2 = i6;
                }
            }
        }
        if (i == cArr2.length) {
            return cArr2;
        }
        throw new Error("miscalculated data length!");
    }
}
