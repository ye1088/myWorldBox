package com.integralblue.httpresponsecache.compat.libcore.io;

import com.integralblue.httpresponsecache.compat.Charsets;
import com.integralblue.httpresponsecache.compat.Strings;
import com.integralblue.httpresponsecache.compat.libcore.util.EmptyArray;
import io.netty.handler.codec.http.HttpConstants;
import org.apache.tools.tar.TarConstants;

public final class Base64 {
    private static final byte[] map = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, TarConstants.LF_GNUTYPE_SPARSE, (byte) 84, (byte) 85, (byte) 86, (byte) 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, (byte) 121, (byte) 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, (byte) 56, (byte) 57, (byte) 43, (byte) 47};

    private Base64() {
    }

    public static byte[] decode(byte[] in) {
        return decode(in, in.length);
    }

    public static byte[] decode(byte[] in, int len) {
        int length = (len / 4) * 3;
        if (length == 0) {
            return EmptyArray.BYTE;
        }
        int outIndex;
        byte[] result;
        byte[] out = new byte[length];
        int pad = 0;
        while (true) {
            byte chr = in[len - 1];
            if (!(chr == (byte) 10 || chr == (byte) 13 || chr == (byte) 32 || chr == (byte) 9)) {
                if (chr != HttpConstants.EQUALS) {
                    break;
                }
                pad++;
            }
            len--;
        }
        int inIndex = 0;
        int quantum = 0;
        int i = 0;
        int outIndex2 = 0;
        while (i < len) {
            chr = in[i];
            if (chr == (byte) 10 || chr == (byte) 13 || chr == (byte) 32) {
                outIndex = outIndex2;
            } else if (chr == (byte) 9) {
                outIndex = outIndex2;
            } else {
                int bits;
                if (chr >= (byte) 65 && chr <= (byte) 90) {
                    bits = chr - 65;
                } else if (chr >= (byte) 97 && chr <= (byte) 122) {
                    bits = chr - 71;
                } else if (chr >= TarConstants.LF_NORMAL && chr <= (byte) 57) {
                    bits = chr + 4;
                } else if (chr == (byte) 43) {
                    bits = 62;
                } else if (chr != (byte) 47) {
                    return null;
                } else {
                    bits = 63;
                }
                quantum = (quantum << 6) | ((byte) bits);
                if (inIndex % 4 == 3) {
                    outIndex = outIndex2 + 1;
                    out[outIndex2] = (byte) (quantum >> 16);
                    outIndex2 = outIndex + 1;
                    out[outIndex] = (byte) (quantum >> 8);
                    outIndex = outIndex2 + 1;
                    out[outIndex2] = (byte) quantum;
                } else {
                    outIndex = outIndex2;
                }
                inIndex++;
            }
            i++;
            outIndex2 = outIndex;
        }
        if (pad > 0) {
            quantum <<= pad * 6;
            outIndex = outIndex2 + 1;
            out[outIndex2] = (byte) (quantum >> 16);
            if (pad == 1) {
                outIndex2 = outIndex + 1;
                out[outIndex] = (byte) (quantum >> 8);
            }
            result = new byte[outIndex];
            System.arraycopy(out, 0, result, 0, outIndex);
            return result;
        }
        outIndex = outIndex2;
        result = new byte[outIndex];
        System.arraycopy(out, 0, result, 0, outIndex);
        return result;
    }

    public static String encode(byte[] in) {
        int i;
        byte[] out = new byte[(((in.length + 2) * 4) / 3)];
        int end = in.length - (in.length % 3);
        int index = 0;
        for (int i2 = 0; i2 < end; i2 += 3) {
            i = index + 1;
            out[index] = map[(in[i2] & 255) >> 2];
            index = i + 1;
            out[i] = map[((in[i2] & 3) << 4) | ((in[i2 + 1] & 255) >> 4)];
            i = index + 1;
            out[index] = map[((in[i2 + 1] & 15) << 2) | ((in[i2 + 2] & 255) >> 6)];
            index = i + 1;
            out[i] = map[in[i2 + 2] & 63];
        }
        switch (in.length % 3) {
            case 1:
                i = index + 1;
                out[index] = map[(in[end] & 255) >> 2];
                index = i + 1;
                out[i] = map[(in[end] & 3) << 4];
                i = index + 1;
                out[index] = HttpConstants.EQUALS;
                index = i + 1;
                out[i] = HttpConstants.EQUALS;
                i = index;
                break;
            case 2:
                i = index + 1;
                out[index] = map[(in[end] & 255) >> 2];
                index = i + 1;
                out[i] = map[((in[end] & 3) << 4) | ((in[end + 1] & 255) >> 4)];
                i = index + 1;
                out[index] = map[(in[end + 1] & 15) << 2];
                index = i + 1;
                out[i] = HttpConstants.EQUALS;
                break;
        }
        i = index;
        return Strings.construct(out, 0, i, Charsets.US_ASCII);
    }
}
