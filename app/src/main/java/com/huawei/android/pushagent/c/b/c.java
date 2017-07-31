package com.huawei.android.pushagent.c.b;

import com.huawei.android.pushagent.c.a.e;
import java.lang.reflect.InvocationTargetException;

public class c implements a {
    private static c a;

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            if (a == null) {
                a = new c();
            }
            cVar = a;
        }
        return cVar;
    }

    public static Object b() {
        Object obj = null;
        try {
            Class cls = Class.forName("android.telephony.MSimTelephonyManager");
            obj = cls.getDeclaredMethod("getDefault", new Class[0]).invoke(cls, new Object[0]);
        } catch (Exception e) {
            e.e("MutiCardHwImpl", " getDefaultMSimTelephonyManager wrong " + e.toString());
        }
        return obj;
    }

    public String a(int i) {
        String str;
        String str2 = "";
        Class[] clsArr = new Class[]{Integer.TYPE};
        Object[] objArr = new Object[]{Integer.valueOf(i)};
        try {
            Object b = b();
            if (b != null) {
                str = (String) b.getClass().getMethod("getDeviceId", clsArr).invoke(b, objArr);
                return str != null ? "" : str;
            }
        } catch (NoSuchMethodException e) {
            e.e("MutiCardHwImpl", "getDeviceId exception:" + e.toString());
            str = str2;
        } catch (IllegalAccessException e2) {
            e.e("MutiCardHwImpl", "getDeviceId exception:" + e2.toString());
            str = str2;
        } catch (IllegalArgumentException e3) {
            e.e("MutiCardHwImpl", "getDeviceId exception:" + e3.toString());
            str = str2;
        } catch (InvocationTargetException e4) {
            e.e("MutiCardHwImpl", "getDeviceId exception:" + e4.toString());
        }
        str = str2;
        if (str != null) {
        }
    }
}
