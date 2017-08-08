package com.MCWorld.utils;

/* compiled from: UtilsFormat */
public class ac {
    public static String bs(long count) {
        String result = "";
        if (count < 10000) {
            return String.valueOf(count);
        }
        if (count < 100000000) {
            return String.format("%d万", new Object[]{Long.valueOf(count / 10000)});
        }
        if (count - ((count / 100000000) * 100000000) > 10000000) {
            return String.format("%d.%d亿", new Object[]{Long.valueOf(count / 100000000), Long.valueOf((count - ((count / 100000000) * 100000000)) / 10000000)});
        }
        return String.format("%d亿", new Object[]{Long.valueOf(count / 100000000)});
    }
}
