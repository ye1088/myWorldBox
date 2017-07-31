package com.huawei.android.pushagent.c.b;

import com.huawei.android.pushagent.c.a.e;
import java.lang.reflect.InvocationTargetException;

public class d implements a {
    private static d a;

    private d() {
    }

    public static synchronized d a() {
        d dVar;
        synchronized (d.class) {
            if (a == null) {
                a = new d();
            }
            dVar = a;
        }
        return dVar;
    }

    private static Object b() {
        Object obj = null;
        try {
            Class cls = Class.forName("com.mediatek.telephony.TelephonyManagerEx");
            obj = cls.getDeclaredMethod("getDefault", new Class[0]).invoke(cls, new Object[0]);
        } catch (Exception e) {
            e.e("mutiCardMTKImpl", " getDefaultTelephonyManagerEx wrong " + e.toString());
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
            e.e("mutiCardMTKImpl", "getDeviceId exception:" + e.toString());
            str = str2;
        } catch (IllegalAccessException e2) {
            e.e("mutiCardMTKImpl", "getDeviceId exception:" + e2.toString());
            str = str2;
        } catch (IllegalArgumentException e3) {
            e.e("mutiCardMTKImpl", "getDeviceId exception:" + e3.toString());
            str = str2;
        } catch (InvocationTargetException e4) {
            e.e("mutiCardMTKImpl", "getDeviceId exception:" + e4.toString());
        }
        str = str2;
        if (str != null) {
        }
    }
}
