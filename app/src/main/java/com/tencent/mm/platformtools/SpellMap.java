package com.tencent.mm.platformtools;

import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.UnsupportedEncodingException;

public class SpellMap {
    private static int a(char c) {
        if (c <= '') {
            return c;
        }
        try {
            byte[] bytes = String.valueOf(c).getBytes("GBK");
            return (bytes == null || bytes.length > 2 || bytes.length <= 0) ? 0 : bytes.length == 1 ? bytes[0] : bytes.length == 2 ? ((bytes[0] + 256) << 16) + (bytes[1] + 256) : 0;
        } catch (UnsupportedEncodingException e) {
            return 0;
        }
    }

    public static String getSpell(char c) {
        int a = a(c);
        if (a < 65536) {
            return String.valueOf(c);
        }
        int i = a >> 16;
        a &= 255;
        String spellGetJni = (i < 129 || i > 253) ? null : (a < 63 || a > 254) ? null : spellGetJni(i - 129, a - 63);
        if (spellGetJni == null) {
            return null;
        }
        String[] split = spellGetJni.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
        return (split == null || split.length < 2) ? spellGetJni : split[0];
    }

    public static native String spellGetJni(int i, int i2);
}
