package com.tencent.smtt.sdk;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

class w {
    private static w a = null;
    private static Context b = null;

    private w() {
    }

    static w a(Context context) {
        b = context.getApplicationContext();
        if (a == null) {
            a = new w();
        }
        return a;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Properties c() {
        /*
        r5 = this;
        r1 = 0;
        r3 = r5.a_isRightVersion();	 Catch:{ Exception -> 0x0022, all -> 0x0035 }
        r2 = new java.util.Properties;	 Catch:{ Exception -> 0x0022, all -> 0x0035 }
        r2.<init>();	 Catch:{ Exception -> 0x0022, all -> 0x0035 }
        if (r3 == 0) goto L_0x0054;
    L_0x000c:
        if (r2 == 0) goto L_0x0054;
    L_0x000e:
        r0 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x0049, all -> 0x0035 }
        r0.<init>(r3);	 Catch:{ Exception -> 0x0049, all -> 0x0035 }
        r2.load(r0);	 Catch:{ Exception -> 0x004f, all -> 0x0041 }
    L_0x0016:
        if (r0 == 0) goto L_0x001b;
    L_0x0018:
        r0.close();	 Catch:{ IOException -> 0x001d }
    L_0x001b:
        r0 = r2;
    L_0x001c:
        return r0;
    L_0x001d:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001b;
    L_0x0022:
        r0 = move-exception;
        r2 = r1;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x0027:
        r1.printStackTrace();	 Catch:{ all -> 0x0046 }
        if (r2 == 0) goto L_0x001c;
    L_0x002c:
        r2.close();	 Catch:{ IOException -> 0x0030 }
        goto L_0x001c;
    L_0x0030:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x001c;
    L_0x0035:
        r0 = move-exception;
    L_0x0036:
        if (r1 == 0) goto L_0x003b;
    L_0x0038:
        r1.close();	 Catch:{ IOException -> 0x003c }
    L_0x003b:
        throw r0;
    L_0x003c:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x003b;
    L_0x0041:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x0036;
    L_0x0046:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0036;
    L_0x0049:
        r0 = move-exception;
        r4 = r0;
        r0 = r2;
        r2 = r1;
        r1 = r4;
        goto L_0x0027;
    L_0x004f:
        r1 = move-exception;
        r4 = r2;
        r2 = r0;
        r0 = r4;
        goto L_0x0027;
    L_0x0054:
        r0 = r1;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.w.c():java.util.Properties");
    }

    File a() {
        aa.a();
        File file = new File(aa.g(b), "tbscoreinstall.txt");
        if (file == null || file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    int b() {
        Properties c = c();
        return (c == null || c.getProperty("install_status") == null) ? -1 : Integer.parseInt(c.getProperty("install_status"));
    }
}
