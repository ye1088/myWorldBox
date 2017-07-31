package com.tencent.mm.algorithm;

public final class TypeTransform {
    private TypeTransform() {
    }

    private static byte[] a(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr2[i] = bArr[(length - 1) - i];
        }
        return bArr2;
    }

    public static int byteArrayHLToInt(byte[] bArr) {
        return byteArrayHLToInt(bArr, 0);
    }

    public static int byteArrayHLToInt(byte[] bArr, int i) {
        return ((((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16)) | ((bArr[i + 2] & 255) << 8)) | ((bArr[i + 3] & 255) << 0);
    }

    public static long byteArrayHLToLong(byte[] bArr) {
        return byteArrayHLToLong(bArr, 0);
    }

    public static long byteArrayHLToLong(byte[] bArr, int i) {
        return ((((((((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i + 1]) & 255) << 48)) | ((((long) bArr[i + 2]) & 255) << 40)) | ((((long) bArr[i + 3]) & 255) << 32)) | ((((long) bArr[i + 4]) & 255) << 24)) | ((((long) bArr[i + 5]) & 255) << 16)) | ((((long) bArr[i + 6]) & 255) << 8)) | ((((long) bArr[i + 7]) & 255) << null);
    }

    public static int byteArrayLHToInt(byte[] bArr) {
        return byteArrayLHToInt(bArr, 0);
    }

    public static int byteArrayLHToInt(byte[] bArr, int i) {
        return ((((bArr[i + 3] & 255) << 24) | ((bArr[i + 2] & 255) << 16)) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i] & 255) << 0);
    }

    public static long byteArrayLHToLong(byte[] bArr) {
        return byteArrayLHToLong(bArr, 0);
    }

    public static long byteArrayLHToLong(byte[] bArr, int i) {
        return ((((((((((long) bArr[i + 7]) & 255) << 56) | ((((long) bArr[i + 6]) & 255) << 48)) | ((((long) bArr[5]) & 255) << 40)) | ((((long) bArr[i + 4]) & 255) << 32)) | ((((long) bArr[i + 3]) & 255) << 24)) | ((((long) bArr[i + 2]) & 255) << 16)) | ((((long) bArr[i + 1]) & 255) << 8)) | ((((long) bArr[i]) & 255) << null);
    }

    public static byte[] intToByteArrayHL(int i) {
        return a(intToByteArrayLH(i));
    }

    public static byte[] intToByteArrayLH(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = (byte) ((i >> (i2 * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] longToByteArrayHL(long j) {
        return a(longToByteArrayLH(j));
    }

    public static byte[] longToByteArrayLH(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[i] = (byte) ((int) (j >> (i * 8)));
        }
        return bArr;
    }
}
