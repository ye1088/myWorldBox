package com.integralblue.httpresponsecache.compat;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public final class Strings {
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] UPPER_CASE_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static final boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static final String construct(byte[] data, int offset, int byteCount, Charset charset) {
        try {
            return new String(data, offset, byteCount, charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] getBytes(String string, Charset charset) {
        try {
            return string.getBytes(charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String bytesToHexString(byte[] bytes, boolean upperCase) {
        char[] digits = upperCase ? UPPER_CASE_DIGITS : DIGITS;
        char[] buf = new char[(bytes.length * 2)];
        int c = 0;
        for (byte b : bytes) {
            int i = c + 1;
            buf[c] = digits[(b >> 4) & 15];
            c = i + 1;
            buf[i] = digits[b & 15];
        }
        return new String(buf);
    }
}
