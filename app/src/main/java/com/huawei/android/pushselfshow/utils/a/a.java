package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import com.baidu.android.pushservice.PushConstants;
import com.huawei.android.pushagent.c.a.e;
import java.io.File;

public class a {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList a(android.content.Context r6, java.lang.String r7) {
        /*
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = "hwpushApp.db";
        r1 = c(r6, r1);	 Catch:{ Exception -> 0x0060 }
        r2 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x0060 }
        if (r2 == 0) goto L_0x001c;
    L_0x0012:
        r1 = "PushSelfShowLog";
        r2 = "database is null,can't queryAppinfo";
        com.huawei.android.pushagent.c.a.e.a(r1, r2);	 Catch:{ Exception -> 0x0060 }
    L_0x001b:
        return r0;
    L_0x001c:
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0060 }
        r3.<init>();	 Catch:{ Exception -> 0x0060 }
        r4 = "dbName path is ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0060 }
        r3 = r3.append(r1);	 Catch:{ Exception -> 0x0060 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0060 }
        com.huawei.android.pushagent.c.a.e.a(r2, r3);	 Catch:{ Exception -> 0x0060 }
        r2 = com.huawei.android.pushselfshow.utils.a.d.a();	 Catch:{ Exception -> 0x0060 }
        r3 = "openmarket";
        r2 = r2.a(r1, r3);	 Catch:{ Exception -> 0x0060 }
        if (r2 == 0) goto L_0x001b;
    L_0x0043:
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0060 }
        r3 = 0;
        r2[r3] = r7;	 Catch:{ Exception -> 0x0060 }
        r3 = "select * from openmarket where package = ?;";
        r4 = com.huawei.android.pushselfshow.utils.a.d.a();	 Catch:{ Exception -> 0x0060 }
        r2 = r4.a(r1, r3, r2);	 Catch:{ Exception -> 0x0060 }
        if (r2 != 0) goto L_0x006b;
    L_0x0056:
        r1 = "PushSelfShowLog";
        r2 = "cursor is null.";
        com.huawei.android.pushagent.c.a.e.a(r1, r2);	 Catch:{ Exception -> 0x0060 }
        goto L_0x001b;
    L_0x0060:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "queryAppinfo error";
        com.huawei.android.pushagent.c.a.e.d(r2, r3, r1);
        goto L_0x001b;
    L_0x006b:
        r1 = r2.getCount();	 Catch:{ Exception -> 0x00bb }
        if (r1 <= 0) goto L_0x00aa;
    L_0x0071:
        r1 = "msgid";
        r1 = r2.getColumnIndex(r1);	 Catch:{ Exception -> 0x00bb }
        r1 = r2.getString(r1);	 Catch:{ Exception -> 0x00bb }
        r0.add(r1);	 Catch:{ Exception -> 0x00bb }
        r3 = "TAG";
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bb }
        r4.<init>();	 Catch:{ Exception -> 0x00bb }
        r5 = "msgid and packageName is  ";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x00bb }
        r1 = r4.append(r1);	 Catch:{ Exception -> 0x00bb }
        r4 = ",";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x00bb }
        r1 = r1.append(r7);	 Catch:{ Exception -> 0x00bb }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00bb }
        com.huawei.android.pushagent.c.a.e.a(r3, r1);	 Catch:{ Exception -> 0x00bb }
        r1 = r2.moveToNext();	 Catch:{ Exception -> 0x00bb }
        if (r1 != 0) goto L_0x0071;
    L_0x00aa:
        r2.close();	 Catch:{ Exception -> 0x00af }
        goto L_0x001b;
    L_0x00af:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "cursor.close() ";
        com.huawei.android.pushagent.c.a.e.d(r2, r3, r1);	 Catch:{ Exception -> 0x0060 }
        goto L_0x001b;
    L_0x00bb:
        r1 = move-exception;
        r3 = "TAG";
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00eb }
        r4.<init>();	 Catch:{ all -> 0x00eb }
        r5 = "queryAppinfo error ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x00eb }
        r5 = r1.toString();	 Catch:{ all -> 0x00eb }
        r4 = r4.append(r5);	 Catch:{ all -> 0x00eb }
        r4 = r4.toString();	 Catch:{ all -> 0x00eb }
        com.huawei.android.pushagent.c.a.e.c(r3, r4, r1);	 Catch:{ all -> 0x00eb }
        r2.close();	 Catch:{ Exception -> 0x00df }
        goto L_0x001b;
    L_0x00df:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "cursor.close() ";
        com.huawei.android.pushagent.c.a.e.d(r2, r3, r1);	 Catch:{ Exception -> 0x0060 }
        goto L_0x001b;
    L_0x00eb:
        r1 = move-exception;
        r2.close();	 Catch:{ Exception -> 0x00f0 }
    L_0x00ef:
        throw r1;	 Catch:{ Exception -> 0x0060 }
    L_0x00f0:
        r2 = move-exception;
        r3 = "PushSelfShowLog";
        r4 = "cursor.close() ";
        com.huawei.android.pushagent.c.a.e.d(r3, r4, r2);	 Catch:{ Exception -> 0x0060 }
        goto L_0x00ef;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.a.a(android.content.Context, java.lang.String):java.util.ArrayList");
    }

    public static void a(Context context, String str, String str2) {
        try {
            if (!context.getDatabasePath("hwpushApp.db").exists()) {
                context.openOrCreateDatabase("hwpushApp.db", 0, null);
            }
            String c = c(context, "hwpushApp.db");
            if (TextUtils.isEmpty(c)) {
                e.d("PushSelfShowLog", "database is null,can't insert appinfo into db");
                return;
            }
            e.a("PushSelfShowLog", "dbName path is " + c);
            if (!d.a().a(c, "openmarket")) {
                d.a().a(context, c, "create table openmarket(    _id INTEGER PRIMARY KEY AUTOINCREMENT,     msgid  TEXT,    package TEXT);");
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(PushConstants.EXTRA_MSGID, str);
            contentValues.put(com.umeng.analytics.onlineconfig.a.b, str2);
            d.a().a(context, c, "openmarket", contentValues);
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "insertAppinfo error", e);
        }
    }

    public static void b(Context context, String str) {
        try {
            String c = c(context, "hwpushApp.db");
            if (TextUtils.isEmpty(c)) {
                e.d("PushSelfShowLog", "database is null,can't delete appinfo");
                return;
            }
            e.a("PushSelfShowLog", "dbName path is " + c);
            if (d.a().a(c, "openmarket")) {
                d.a().a(c, "openmarket", "package = ?", new String[]{str});
            }
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "Delete Appinfo error", e);
        }
    }

    private static String c(Context context, String str) {
        String str2 = "";
        if (context == null) {
            return str2;
        }
        File databasePath = context.getDatabasePath("hwpushApp.db");
        return databasePath.exists() ? databasePath.getAbsolutePath() : str2;
    }
}
