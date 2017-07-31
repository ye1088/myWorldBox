package com.huawei.android.pushselfshow.richpush.tools;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.utils.b.a;
import com.huawei.android.pushselfshow.utils.b.b;
import java.io.File;

public class d {
    public static String a(Context context, String str) {
        e.a("PushSelfShowLog", "download richpush file successed ,try to unzip file,file path is " + str);
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.startsWith(b.b(context))) {
            String str2 = "";
            try {
                str2 = str.substring(0, str.lastIndexOf(File.separator));
                new a(str, str2 + File.separator).a();
                File file = new File(str2 + "/" + "index.html");
                if (file.exists()) {
                    e.a("PushSelfShowLog", "unzip success ,so delete src zip file");
                    File file2 = new File(str);
                    if (file2.exists()) {
                        com.huawei.android.pushselfshow.utils.a.a(file2);
                    }
                    return file.getAbsolutePath();
                }
                e.a("PushSelfShowLog", "unzip fail ,don't exist index.html");
                com.huawei.android.pushselfshow.utils.a.a(new File(str2));
                return null;
            } catch (IndexOutOfBoundsException e) {
                e.d("PushSelfShowLog", e.toString());
                return "";
            }
        }
        e.a("PushSelfShowLog", "localfile dose not startsWith PushService directory");
        return "";
    }

    public String a(Context context, String str, int i, String str2) {
        String a;
        String str3;
        Exception exception;
        int i2;
        try {
            a = new b().a(context, str, str2);
            if (a != null) {
                try {
                    if (a.length() > 0) {
                        return a;
                    }
                } catch (Exception e) {
                    Exception exception2 = e;
                    str3 = a;
                    exception = exception2;
                    e.a("PushSelfShowLog", "download err" + exception.toString());
                    a = str3;
                    if (i <= 0) {
                        i = 1;
                    }
                    i2 = i - 1;
                    if (i2 > 0) {
                    }
                }
            }
            e.a("PushSelfShowLog", "download failed");
        } catch (Exception e2) {
            exception = e2;
            str3 = null;
            e.a("PushSelfShowLog", "download err" + exception.toString());
            a = str3;
            if (i <= 0) {
                i = 1;
            }
            i2 = i - 1;
            if (i2 > 0) {
            }
        }
        if (i <= 0) {
            i = 1;
        }
        i2 = i - 1;
        return (i2 > 0 || a(context, str, i2, str2) == null) ? null : a;
    }
}
