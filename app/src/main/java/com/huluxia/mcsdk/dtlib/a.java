package com.huluxia.mcsdk.dtlib;

import java.util.Random;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Undefined;

/* compiled from: DTDataTypeEx */
public class a {
    public static long aq(Object paramObject) {
        if (paramObject instanceof NativeJavaObject) {
            return ((Long) ((NativeJavaObject) paramObject).unwrap()).longValue();
        }
        if (paramObject instanceof Number) {
            return ((Number) paramObject).longValue();
        }
        return paramObject instanceof Undefined ? 0 : 0;
    }

    public static int ar(Object paramObject) {
        if (paramObject instanceof NativeJavaObject) {
            return (int) ((Long) ((NativeJavaObject) paramObject).unwrap()).longValue();
        }
        if (paramObject instanceof Number) {
            return (int) ((Number) paramObject).longValue();
        }
        return paramObject instanceof Undefined ? 0 : 0;
    }

    public static String ib(int in_val) {
        return Integer.toString(in_val);
    }

    public static int ds(String in_str) {
        return Integer.valueOf(in_str).intValue();
    }

    public static String iE(int length) {
        try {
            StringBuffer buffer = new StringBuffer("123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
            StringBuffer sb = new StringBuffer();
            Random random = new Random();
            int range = buffer.length();
            for (int i = 0; i < length; i++) {
                sb.append(buffer.charAt(random.nextInt(range)));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
