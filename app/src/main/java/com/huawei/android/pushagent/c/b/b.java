package com.huawei.android.pushagent.c.b;

import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.b.a.a;
import java.lang.reflect.Field;

public class b {
    private static a a = a.MODE_SUPPORT_UNKNOWN;
    private static a b;

    public static synchronized a a() {
        a aVar;
        synchronized (b.class) {
            b();
            if (a == a.MODE_SUPPORT_MTK_GEMINI) {
                b = d.a();
            } else {
                b = c.a();
            }
            aVar = b;
        }
        return aVar;
    }

    private static synchronized void a(a aVar) {
        synchronized (b.class) {
            a = aVar;
        }
    }

    public static synchronized boolean b() {
        boolean z;
        boolean z2 = true;
        synchronized (b.class) {
            z = false;
            if (a == a.MODE_SUPPORT_UNKNOWN) {
                try {
                    if (d()) {
                        a(a.MODE_SUPPORT_MTK_GEMINI);
                    } else if (c()) {
                        a(a.MODE_SUPPORT_HW_GEMINI);
                    } else {
                        a(a.MODE_NOT_SUPPORT_GEMINI);
                        z2 = false;
                    }
                    z = z2;
                } catch (Exception e) {
                    e.d("mutiCardFactory", " " + e.toString());
                } catch (Error e2) {
                    e.d("mutiCardFactory", "" + e2.toString());
                }
            } else if (a == a.MODE_SUPPORT_HW_GEMINI || a == a.MODE_SUPPORT_MTK_GEMINI) {
                z = true;
            }
        }
        return z;
    }

    public static boolean c() {
        boolean z = false;
        try {
            Object b = c.b();
            z = b != null ? ((Boolean) b.getClass().getMethod("isMultiSimEnabled", new Class[0]).invoke(b, new Object[0])).booleanValue() : false;
        } catch (Exception e) {
            e.d("mutiCardFactory", "MSimTelephonyManager.getDefault().isMultiSimEnabled()?" + e.toString());
        } catch (Error e2) {
            e.d("mutiCardFactory", "MSimTelephonyManager.getDefault().isMultiSimEnabled()" + e2.toString());
        }
        e.b("mutiCardFactory", "isHwGeminiSupport1 " + z);
        return z;
    }

    private static boolean d() {
        boolean z = false;
        try {
            Field declaredField = Class.forName("com.mediatek.common.featureoption.FeatureOption").getDeclaredField("MTK_GEMINI_SUPPORT");
            declaredField.setAccessible(true);
            z = declaredField.getBoolean(null);
        } catch (Exception e) {
            e.d("mutiCardFactory", "FeatureOption.MTK_GEMINI_SUPPORT" + e.toString());
        } catch (Error e2) {
            e.d("mutiCardFactory", "FeatureOption.MTK_GEMINI_SUPPORT" + e2.toString());
        }
        e.b("mutiCardFactory", "isMtkGeminiSupport " + z);
        return z;
    }
}
