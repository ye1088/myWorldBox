package com.tencent.smtt.utils;

import android.os.Build.VERSION;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;

public class m {
    public static Object a(Class<?> cls, String str, Class<?>[] clsArr, Object... objArr) {
        Object obj = null;
        try {
            Method method = cls.getMethod(str, clsArr);
            method.setAccessible(true);
            obj = method.invoke(null, objArr);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return obj;
    }

    public static Object a(Object obj, String str) {
        return a(obj, str, null, new Object[0]);
    }

    public static Object a(Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        if (obj == null) {
            return null;
        }
        try {
            Class cls = obj.getClass();
            Method method = VERSION.SDK_INT > 10 ? cls.getMethod(str, clsArr) : cls.getDeclaredMethod(str, clsArr);
            method.setAccessible(true);
            if (objArr.length == 0) {
                objArr = null;
            }
            return method.invoke(obj, objArr);
        } catch (Throwable th) {
            if (th.getCause() != null && th.getCause().toString().contains("AuthenticationFail")) {
                return new String("AuthenticationFail");
            }
            if (str != null && (str.equalsIgnoreCase("canLoadX5Core") || str.equalsIgnoreCase("initTesRuntimeEnvironment"))) {
                return null;
            }
            Writer stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            Log.e("ReflectionUtils", "invokeInstance -- exceptions:" + stringWriter.toString());
            return null;
        }
    }

    public static Object a(String str, String str2) {
        Object obj = null;
        try {
            obj = Class.forName(str).getMethod(str2, new Class[0]).invoke(null, new Object[0]);
        } catch (Throwable th) {
        }
        return obj;
    }

    public static Method a(Object obj, String str, Class<?>... clsArr) {
        Class cls = obj.getClass();
        while (cls != Object.class) {
            if (cls == null) {
                return null;
            }
            try {
                return cls.getDeclaredMethod(str, clsArr);
            } catch (Exception e) {
                cls = cls.getSuperclass();
            }
        }
        return null;
    }
}
