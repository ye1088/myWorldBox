package com.tencent.mm.algorithm;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;

public class Base64 {
    private static final char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[] b;

    static {
        int[] iArr = new int[256];
        b = iArr;
        Arrays.fill(iArr, -1);
        int length = a.length;
        for (int i = 0; i < length; i++) {
            b[a[i]] = i;
        }
        b[61] = 0;
    }

    public static final byte[] decode(String str) {
        int length = str != null ? str.length() : 0;
        if (length == 0) {
            return new byte[0];
        }
        int i;
        int i2 = 0;
        for (i = 0; i < length; i++) {
            if (b[str.charAt(i)] < 0) {
                i2++;
            }
        }
        if ((length - i2) % 4 != 0) {
            return null;
        }
        i = length;
        int i3 = 0;
        while (i > 1) {
            i--;
            if (b[str.charAt(i)] > 0) {
                break;
            } else if (str.charAt(i) == '=') {
                i3++;
            }
        }
        int i4 = (((length - i2) * 6) >> 3) - i3;
        byte[] bArr = new byte[i4];
        int i5 = 0;
        i2 = 0;
        while (i5 < i4) {
            i = 0;
            int i6 = i2;
            i2 = 0;
            while (i2 < 4) {
                length = i6 + 1;
                i6 = b[str.charAt(i6)];
                if (i6 >= 0) {
                    i |= i6 << (18 - (i2 * 6));
                } else {
                    i2--;
                }
                i2++;
                i6 = length;
            }
            i2 = i5 + 1;
            bArr[i5] = (byte) (i >> 16);
            if (i2 < i4) {
                length = i2 + 1;
                bArr[i2] = (byte) (i >> 8);
                if (length < i4) {
                    i2 = length + 1;
                    bArr[length] = (byte) i;
                } else {
                    i2 = length;
                }
            }
            i5 = i2;
            i2 = i6;
        }
        return bArr;
    }

    public static final byte[] decode(byte[] bArr) {
        int i;
        int i2;
        int i3 = 0;
        for (byte b : bArr) {
            if (b[b & 255] < 0) {
                i3++;
            }
        }
        if ((i2 - i3) % 4 != 0) {
            return null;
        }
        i = i2;
        int i4 = 0;
        while (i > 1) {
            i--;
            if (b[bArr[i] & 255] > 0) {
                break;
            } else if (bArr[i] == HttpConstants.EQUALS) {
                i4++;
            }
        }
        int i5 = (((i2 - i3) * 6) >> 3) - i4;
        byte[] bArr2 = new byte[i5];
        int i6 = 0;
        i3 = 0;
        while (i6 < i5) {
            i = 0;
            int i7 = i3;
            i3 = 0;
            while (i3 < 4) {
                i2 = i7 + 1;
                i7 = b[bArr[i7] & 255];
                if (i7 >= 0) {
                    i |= i7 << (18 - (i3 * 6));
                } else {
                    i3--;
                }
                i3++;
                i7 = i2;
            }
            i3 = i6 + 1;
            bArr2[i6] = (byte) (i >> 16);
            if (i3 < i5) {
                i2 = i3 + 1;
                bArr2[i3] = (byte) (i >> 8);
                if (i2 < i5) {
                    i3 = i2 + 1;
                    bArr2[i2] = (byte) i;
                } else {
                    i3 = i2;
                }
            }
            i6 = i3;
            i3 = i7;
        }
        return bArr2;
    }

    public static final byte[] decode(char[] cArr) {
        int length = cArr != null ? cArr.length : 0;
        if (length == 0) {
            return new byte[0];
        }
        int i;
        int i2 = 0;
        for (i = 0; i < length; i++) {
            if (b[cArr[i]] < 0) {
                i2++;
            }
        }
        if ((length - i2) % 4 != 0) {
            return null;
        }
        i = length;
        int i3 = 0;
        while (i > 1) {
            i--;
            if (b[cArr[i]] > 0) {
                break;
            } else if (cArr[i] == '=') {
                i3++;
            }
        }
        int i4 = (((length - i2) * 6) >> 3) - i3;
        byte[] bArr = new byte[i4];
        int i5 = 0;
        i2 = 0;
        while (i5 < i4) {
            i = 0;
            int i6 = i2;
            i2 = 0;
            while (i2 < 4) {
                length = i6 + 1;
                i6 = b[cArr[i6]];
                if (i6 >= 0) {
                    i |= i6 << (18 - (i2 * 6));
                } else {
                    i2--;
                }
                i2++;
                i6 = length;
            }
            i2 = i5 + 1;
            bArr[i5] = (byte) (i >> 16);
            if (i2 < i4) {
                length = i2 + 1;
                bArr[i2] = (byte) (i >> 8);
                if (length < i4) {
                    i2 = length + 1;
                    bArr[length] = (byte) i;
                } else {
                    i2 = length;
                }
            }
            i5 = i2;
            i2 = i6;
        }
        return bArr;
    }

    public static final byte[] decodeFast(String str) {
        int i = 0;
        int length = str.length();
        if (length == 0) {
            return new byte[0];
        }
        int i2 = length - 1;
        int i3 = 0;
        while (i3 < i2 && b[str.charAt(i3) & 255] < 0) {
            i3++;
        }
        int i4 = i2;
        while (i4 > 0 && b[str.charAt(i4) & 255] < 0) {
            i4--;
        }
        int i5 = str.charAt(i4) == '=' ? str.charAt(i4 + -1) == '=' ? 2 : 1 : 0;
        int i6 = (i4 - i3) + 1;
        if (length > 76) {
            i2 = (str.charAt(76) == StringUtil.CARRIAGE_RETURN ? i6 / 78 : 0) << 1;
        } else {
            i2 = 0;
        }
        int i7 = (((i6 - i2) * 6) >> 3) - i5;
        byte[] bArr = new byte[i7];
        int i8 = (i7 / 3) * 3;
        length = 0;
        int i9 = 0;
        while (i9 < i8) {
            int i10 = i3 + 1;
            int i11 = i10 + 1;
            i3 = (b[str.charAt(i3)] << 18) | (b[str.charAt(i10)] << 12);
            i10 = i11 + 1;
            int i12 = (b[str.charAt(i11)] << 6) | i3;
            i3 = i10 + 1;
            i12 |= b[str.charAt(i10)];
            i10 = i9 + 1;
            bArr[i9] = (byte) (i12 >> 16);
            i11 = i10 + 1;
            bArr[i10] = (byte) (i12 >> 8);
            i9 = i11 + 1;
            bArr[i11] = (byte) i12;
            if (i2 > 0) {
                length++;
                if (length == 19) {
                    i3 += 2;
                    length = 0;
                }
            }
        }
        if (i9 < i7) {
            i3 = 0;
            for (i2 = i3; i2 <= i4 - i5; i2++) {
                i++;
                i3 = (b[str.charAt(i2)] << (18 - (i * 6))) | i3;
            }
            i2 = 16;
            i = i9;
            while (i < i7) {
                length = i + 1;
                bArr[i] = (byte) (i3 >> i2);
                i2 -= 8;
                i = length;
            }
        }
        return bArr;
    }

    public static final byte[] decodeFast(byte[] bArr) {
        int i = 0;
        int length = bArr.length;
        if (length == 0) {
            return new byte[0];
        }
        int i2 = length - 1;
        int i3 = 0;
        while (i3 < i2 && b[bArr[i3] & 255] < 0) {
            i3++;
        }
        int i4 = i2;
        while (i4 > 0 && b[bArr[i4] & 255] < 0) {
            i4--;
        }
        int i5 = bArr[i4] == HttpConstants.EQUALS ? bArr[i4 + -1] == HttpConstants.EQUALS ? 2 : 1 : 0;
        int i6 = (i4 - i3) + 1;
        if (length > 76) {
            i2 = (bArr[76] == (byte) 13 ? i6 / 78 : 0) << 1;
        } else {
            i2 = 0;
        }
        int i7 = (((i6 - i2) * 6) >> 3) - i5;
        byte[] bArr2 = new byte[i7];
        int i8 = (i7 / 3) * 3;
        length = 0;
        int i9 = 0;
        while (i9 < i8) {
            int i10 = i3 + 1;
            int i11 = i10 + 1;
            i3 = (b[bArr[i3]] << 18) | (b[bArr[i10]] << 12);
            i10 = i11 + 1;
            int i12 = (b[bArr[i11]] << 6) | i3;
            i3 = i10 + 1;
            i12 |= b[bArr[i10]];
            i10 = i9 + 1;
            bArr2[i9] = (byte) (i12 >> 16);
            i11 = i10 + 1;
            bArr2[i10] = (byte) (i12 >> 8);
            i9 = i11 + 1;
            bArr2[i11] = (byte) i12;
            if (i2 > 0) {
                length++;
                if (length == 19) {
                    i3 += 2;
                    length = 0;
                }
            }
        }
        if (i9 < i7) {
            i3 = 0;
            for (i2 = i3; i2 <= i4 - i5; i2++) {
                i++;
                i3 = (b[bArr[i2]] << (18 - (i * 6))) | i3;
            }
            i2 = 16;
            i = i9;
            while (i < i7) {
                length = i + 1;
                bArr2[i] = (byte) (i3 >> i2);
                i2 -= 8;
                i = length;
            }
        }
        return bArr2;
    }

    public static final byte[] decodeFast(char[] cArr) {
        int i = 0;
        int length = cArr.length;
        if (length == 0) {
            return new byte[0];
        }
        int i2 = length - 1;
        int i3 = 0;
        while (i3 < i2 && b[cArr[i3]] < 0) {
            i3++;
        }
        int i4 = i2;
        while (i4 > 0 && b[cArr[i4]] < 0) {
            i4--;
        }
        int i5 = cArr[i4] == '=' ? cArr[i4 + -1] == '=' ? 2 : 1 : 0;
        int i6 = (i4 - i3) + 1;
        if (length > 76) {
            i2 = (cArr[76] == StringUtil.CARRIAGE_RETURN ? i6 / 78 : 0) << 1;
        } else {
            i2 = 0;
        }
        int i7 = (((i6 - i2) * 6) >> 3) - i5;
        byte[] bArr = new byte[i7];
        int i8 = (i7 / 3) * 3;
        length = 0;
        int i9 = 0;
        while (i9 < i8) {
            int i10 = i3 + 1;
            int i11 = i10 + 1;
            i3 = (b[cArr[i3]] << 18) | (b[cArr[i10]] << 12);
            i10 = i11 + 1;
            int i12 = (b[cArr[i11]] << 6) | i3;
            i3 = i10 + 1;
            i12 |= b[cArr[i10]];
            i10 = i9 + 1;
            bArr[i9] = (byte) (i12 >> 16);
            i11 = i10 + 1;
            bArr[i10] = (byte) (i12 >> 8);
            i9 = i11 + 1;
            bArr[i11] = (byte) i12;
            if (i2 > 0) {
                length++;
                if (length == 19) {
                    i3 += 2;
                    length = 0;
                }
            }
        }
        if (i9 < i7) {
            i3 = 0;
            for (i2 = i3; i2 <= i4 - i5; i2++) {
                i++;
                i3 = (b[cArr[i2]] << (18 - (i * 6))) | i3;
            }
            i2 = 16;
            i = i9;
            while (i < i7) {
                length = i + 1;
                bArr[i] = (byte) (i3 >> i2);
                i2 -= 8;
                i = length;
            }
        }
        return bArr;
    }

    public static final byte[] encodeToByte(byte[] bArr, boolean z) {
        int i = 0;
        int length = bArr != null ? bArr.length : 0;
        if (length == 0) {
            return new byte[0];
        }
        int i2 = (length / 3) * 3;
        int i3 = (((length - 1) / 3) + 1) << 2;
        int i4 = i3 + (z ? ((i3 - 1) / 76) << 1 : 0);
        byte[] bArr2 = new byte[i4];
        int i5 = 0;
        i3 = 0;
        int i6 = 0;
        while (i6 < i2) {
            int i7 = i6 + 1;
            int i8 = i7 + 1;
            i7 = ((bArr[i7] & 255) << 8) | ((bArr[i6] & 255) << 16);
            i6 = i8 + 1;
            i7 |= bArr[i8] & 255;
            i8 = i3 + 1;
            bArr2[i3] = (byte) a[(i7 >>> 18) & 63];
            i3 = i8 + 1;
            bArr2[i8] = (byte) a[(i7 >>> 12) & 63];
            i8 = i3 + 1;
            bArr2[i3] = (byte) a[(i7 >>> 6) & 63];
            i3 = i8 + 1;
            bArr2[i8] = (byte) a[i7 & 63];
            if (z) {
                i5++;
                if (i5 == 19 && i3 < i4 - 2) {
                    i7 = i3 + 1;
                    bArr2[i3] = (byte) 13;
                    i5 = i7 + 1;
                    bArr2[i7] = (byte) 10;
                    i3 = i5;
                    i5 = 0;
                }
            }
        }
        i5 = length - i2;
        if (i5 > 0) {
            i3 = (bArr[i2] & 255) << 10;
            if (i5 == 2) {
                i = (bArr[length - 1] & 255) << 2;
            }
            i |= i3;
            bArr2[i4 - 4] = (byte) a[i >> 12];
            bArr2[i4 - 3] = (byte) a[(i >>> 6) & 63];
            bArr2[i4 - 2] = i5 == 2 ? (byte) a[i & 63] : HttpConstants.EQUALS;
            bArr2[i4 - 1] = HttpConstants.EQUALS;
        }
        return bArr2;
    }

    public static final char[] encodeToChar(byte[] bArr, boolean z) {
        int i = 0;
        int length = bArr != null ? bArr.length : 0;
        if (length == 0) {
            return new char[0];
        }
        int i2 = (length / 3) * 3;
        int i3 = (((length - 1) / 3) + 1) << 2;
        int i4 = i3 + (z ? ((i3 - 1) / 76) << 1 : 0);
        char[] cArr = new char[i4];
        int i5 = 0;
        i3 = 0;
        int i6 = 0;
        while (i6 < i2) {
            int i7 = i6 + 1;
            int i8 = i7 + 1;
            i7 = ((bArr[i7] & 255) << 8) | ((bArr[i6] & 255) << 16);
            i6 = i8 + 1;
            i7 |= bArr[i8] & 255;
            i8 = i3 + 1;
            cArr[i3] = a[(i7 >>> 18) & 63];
            i3 = i8 + 1;
            cArr[i8] = a[(i7 >>> 12) & 63];
            i8 = i3 + 1;
            cArr[i3] = a[(i7 >>> 6) & 63];
            i3 = i8 + 1;
            cArr[i8] = a[i7 & 63];
            if (z) {
                i5++;
                if (i5 == 19 && i3 < i4 - 2) {
                    i7 = i3 + 1;
                    cArr[i3] = StringUtil.CARRIAGE_RETURN;
                    i5 = i7 + 1;
                    cArr[i7] = '\n';
                    i3 = i5;
                    i5 = 0;
                }
            }
        }
        i5 = length - i2;
        if (i5 > 0) {
            i3 = (bArr[i2] & 255) << 10;
            if (i5 == 2) {
                i = (bArr[length - 1] & 255) << 2;
            }
            i |= i3;
            cArr[i4 - 4] = a[i >> 12];
            cArr[i4 - 3] = a[(i >>> 6) & 63];
            cArr[i4 - 2] = i5 == 2 ? a[i & 63] : '=';
            cArr[i4 - 1] = '=';
        }
        return cArr;
    }

    public static final String encodeToString(byte[] bArr, boolean z) {
        return new String(encodeToChar(bArr, z));
    }
}
