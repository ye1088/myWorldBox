package com.tencent.smtt.sdk.b;

import android.os.Bundle;
import android.text.TextUtils;

import com.j256.ormlite.stmt.query.SimpleComparison;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class c {
    private static char[] a = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private Bundle b = new Bundle();

    public static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            int i3 = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(a[i3 >>> 2]);
                stringBuffer.append(a[(i3 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i4 = i2 + 1;
            i2 = bArr[i2] & 255;
            if (i4 == length) {
                stringBuffer.append(a[i3 >>> 2]);
                stringBuffer.append(a[((i3 & 3) << 4) | ((i2 & b.bpd) >>> 4)]);
                stringBuffer.append(a[(i2 & 15) << 2]);
                stringBuffer.append(SimpleComparison.EQUAL_TO_OPERATION);
                break;
            }
            i = i4 + 1;
            i4 = bArr[i4] & 255;
            stringBuffer.append(a[i3 >>> 2]);
            stringBuffer.append(a[((i3 & 3) << 4) | ((i2 & b.bpd) >>> 4)]);
            stringBuffer.append(a[((i2 & 15) << 2) | ((i4 & 192) >>> 6)]);
            stringBuffer.append(a[i4 & 63]);
        }
        return stringBuffer.toString();
    }

    private String b(String str) {
        try {
            return a(a.a("24Xdf8j6".getBytes("utf-8"), str.getBytes("utf-8"), 1));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public c a(String str, String str2) {
        this.b.putCharSequence(str, str2);
        return this;
    }

    public String a(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str2 : this.b.keySet()) {
            Object string = this.b.getString(str2);
            if (!TextUtils.isEmpty(string)) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append('&');
                }
                stringBuilder.append(str2).append('=').append(URLEncoder.encode(string));
            }
        }
        return str + "?p=" + URLEncoder.encode(b(stringBuilder.toString()));
    }
}
