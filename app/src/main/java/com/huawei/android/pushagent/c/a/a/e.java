package com.huawei.android.pushagent.c.a.a;

import android.text.TextUtils;

public class e {
    private static String a(char c, int i) {
        StringBuffer stringBuffer = new StringBuffer(i);
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    public static String a(Object obj) {
        return a(String.valueOf(obj));
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.length() < 2) {
            return str;
        }
        try {
            int ceil = (int) Math.ceil(((double) (str.length() * 25)) / 100.0d);
            int ceil2 = (int) Math.ceil(((double) (str.length() * 50)) / 100.0d);
            return str.substring(0, ceil) + a('*', ceil2) + str.substring(ceil + ceil2);
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}
