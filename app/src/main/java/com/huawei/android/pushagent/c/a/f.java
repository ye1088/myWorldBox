package com.huawei.android.pushagent.c.a;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import java.util.Map.Entry;

public class f {
    public static String a(Context context, String str) {
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                str2 = com.huawei.android.pushagent.c.a.a.f.b(context, new h(context, "pclient_info_v2").b(str));
            } catch (Throwable e) {
                e.c(BLocation.TAG, e.toString(), e);
            }
            if (TextUtils.isEmpty(str2)) {
                e.a(BLocation.TAG, "not exist for:" + str);
            }
        }
        return str2;
    }

    public static String a(Context context, String str, String str2) {
        String str3 = "";
        if (!(TextUtils.isEmpty(str) || TextUtils.isEmpty(str2))) {
            try {
                str3 = com.huawei.android.pushagent.c.a.a.f.b(context, new h(context, str).b(str2 + "_v2"));
            } catch (Throwable e) {
                e.c(BLocation.TAG, e.toString(), e);
            }
            if (TextUtils.isEmpty(str3)) {
                e.a(BLocation.TAG, "not exist for:" + str2);
            }
        }
        return str3;
    }

    public static boolean a(Context context, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return new h(context, str).a(str2 + "_v2", com.huawei.android.pushagent.c.a.a.f.a(context, str3));
    }

    public static void b(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            e.d(BLocation.TAG, "removeUnRegisterToken token is empty");
            return;
        }
        try {
            h hVar = new h(context, "pclient_unRegist_info_v2");
            for (Entry key : hVar.b().entrySet()) {
                String str2 = (String) key.getKey();
                if (str.equals(com.huawei.android.pushagent.c.a.a.f.b(context, str2))) {
                    hVar.f(str2);
                    return;
                }
            }
        } catch (Throwable e) {
            e.c(BLocation.TAG, e.toString(), e);
        }
    }

    public static boolean b(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new h(context, "pclient_info_v2").a(str, com.huawei.android.pushagent.c.a.a.f.a(context, str2));
    }

    public static boolean c(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return new h(context, "pclient_unRegist_info_v2").a(com.huawei.android.pushagent.c.a.a.f.a(context, str), str2);
    }
}
