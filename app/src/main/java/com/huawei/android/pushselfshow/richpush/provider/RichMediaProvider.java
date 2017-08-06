package com.huawei.android.pushselfshow.richpush.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.utils.a.b;

public class RichMediaProvider extends ContentProvider {
    private static final UriMatcher b = new UriMatcher(-1);
    b a = null;

    public static class a {
        public static final Uri a = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/support_porvider");
        public static final Uri b = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/insert_bmp");
        public static final Uri c = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/update_bmp");
        public static final Uri d = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/query_bmp");
        public static final Uri e = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/insert_msg");
        public static final Uri f = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/query_msg");
        public static final Uri g = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/delete_msg");
    }

    static {
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "support_porvider", 1);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "insert_bmp", 2);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "update_bmp", 3);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "query_bmp", 4);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "insert_msg", 5);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "query_msg", 6);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "delete_msg", 7);
    }

    private android.net.Uri a(android.database.sqlite.SQLiteDatabase r11, java.lang.String r12, android.content.ContentValues r13, android.net.Uri r14) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a_isRightVersion(android.database.sqlite.SQLiteDatabase, java.lang.String, android.content.ContentValues, android.net.Uri):android.net.Uri. bs: [B:8:0x0036, B:20:0x0060]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r10 = this;
        r8 = 0;
        r0 = "PushSelfShowLog_RichMediaProvider";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "enter insertToDb, table is:";
        r1 = r1.append(r2);
        r1 = r1.append(r12);
        r1 = r1.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r0, r1);
        if (r11 != 0) goto L_0x0028;
    L_0x001d:
        r0 = "PushSelfShowLog_RichMediaProvider";
        r1 = "db is null";
        com.huawei.android.pushagent.c.a_isRightVersion.e.d(r0, r1);
        r0 = r8;
    L_0x0027:
        return r0;
    L_0x0028:
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r11;
        r1 = r12;
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0090, all -> 0x00a6 }
        if (r1 != 0) goto L_0x0049;
    L_0x0036:
        r0 = "PushSelfShowLog_RichMediaProvider";	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        r2 = "cursor is null";	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.d(r0, r2);	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        if (r1 == 0) goto L_0x0044;
    L_0x0041:
        r1.close();
    L_0x0044:
        r11.close();
        r0 = r8;
        goto L_0x0027;
    L_0x0049:
        r0 = r1.getCount();	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        if (r0 >= r2) goto L_0x00c2;	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
    L_0x0051:
        r0 = 0;	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        r2 = r11.insert(r12, r0, r13);	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        r4 = 0;	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        if (r0 <= 0) goto L_0x00c2;	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
    L_0x005c:
        r8 = android.content.ContentUris.withAppendedId(r14, r2);	 Catch:{ Exception -> 0x00b6, all -> 0x00b1 }
        r0 = r10.getContext();	 Catch:{ Exception -> 0x00bc, all -> 0x00b1 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x00bc, all -> 0x00b1 }
        r2 = 0;	 Catch:{ Exception -> 0x00bc, all -> 0x00b1 }
        r0.notifyChange(r8, r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00b1 }
        r0 = r8;
    L_0x006d:
        if (r1 == 0) goto L_0x0072;
    L_0x006f:
        r1.close();
    L_0x0072:
        r11.close();
    L_0x0075:
        r1 = "PushSelfShowLog_RichMediaProvider";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "resultUri is:";
        r2 = r2.append(r3);
        r2 = r2.append(r0);
        r2 = r2.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r2);
        goto L_0x0027;
    L_0x0090:
        r0 = move-exception;
        r1 = r0;
        r0 = r8;
    L_0x0093:
        r2 = "PushSelfShowLog_RichMediaProvider";	 Catch:{ all -> 0x00b3 }
        r3 = r1.toString();	 Catch:{ all -> 0x00b3 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.c(r2, r3, r1);	 Catch:{ all -> 0x00b3 }
        if (r8 == 0) goto L_0x00a2;
    L_0x009f:
        r8.close();
    L_0x00a2:
        r11.close();
        goto L_0x0075;
    L_0x00a6:
        r0 = move-exception;
        r1 = r8;
    L_0x00a8:
        if (r1 == 0) goto L_0x00ad;
    L_0x00aa:
        r1.close();
    L_0x00ad:
        r11.close();
        throw r0;
    L_0x00b1:
        r0 = move-exception;
        goto L_0x00a8;
    L_0x00b3:
        r0 = move-exception;
        r1 = r8;
        goto L_0x00a8;
    L_0x00b6:
        r0 = move-exception;
        r9 = r0;
        r0 = r8;
        r8 = r1;
        r1 = r9;
        goto L_0x0093;
    L_0x00bc:
        r0 = move-exception;
        r9 = r0;
        r0 = r8;
        r8 = r1;
        r1 = r9;
        goto L_0x0093;
    L_0x00c2:
        r0 = r8;
        goto L_0x006d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a_isRightVersion(android.database.sqlite.SQLiteDatabase, java.lang.String, android.content.ContentValues, android.net.Uri):android.net.Uri");
    }

    private boolean a(String str) {
        if (str == null || str.length() == 0 || !str.contains("'")) {
            return false;
        }
        e.d("PushSelfShowLog_RichMediaProvider", str + " can be reject, should check sql");
        return true;
    }

    private boolean a(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        for (String a : strArr) {
            if (a(a)) {
                return true;
            }
        }
        return false;
    }

    public int delete(Uri uri, String str, String[] strArr) {
        int i = 0;
        int match = b.match(uri);
        e.a("PushSelfShowLog_RichMediaProvider", "uri is:" + uri + ",match result: " + match);
        if (this.a != null) {
            switch (match) {
                case 7:
                    SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                    if (writableDatabase == null) {
                        e.d("PushSelfShowLog_RichMediaProvider", "db is null");
                        break;
                    }
                    try {
                        i = writableDatabase.delete("pushmsg", "_id = ?", strArr);
                        getContext().getContentResolver().notifyChange(uri, null);
                        break;
                    } catch (Throwable e) {
                        e.c("PushSelfShowLog_RichMediaProvider", e.toString(), e);
                        break;
                    } finally {
                        writableDatabase.close();
                    }
                default:
                    e.d("PushSelfShowLog_RichMediaProvider", "uri not match!");
                    break;
            }
        }
        e.d("PushSelfShowLog_RichMediaProvider", "dbHelper is null");
        return i;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = b.match(uri);
        e.a("PushSelfShowLog_RichMediaProvider", "uri is:" + uri + ",match result: " + match);
        if (this.a == null) {
            e.d("PushSelfShowLog_RichMediaProvider", "dbHelper is null");
            return null;
        }
        switch (match) {
            case 2:
                return a(this.a.getWritableDatabase(), "notify", contentValues, uri);
            case 5:
                return a(this.a.getWritableDatabase(), "pushmsg", contentValues, uri);
            default:
                e.d("PushSelfShowLog_RichMediaProvider", "uri not match!");
                return null;
        }
    }

    public boolean onCreate() {
        e.a("PushSelfShowLog_RichMediaProvider", "onCreate");
        this.a = b.a(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (a(str) || a(strArr)) {
            e.d("PushSelfShowLog_RichMediaProvider", "in query selection:" + str + " or projection is invalied");
            return null;
        }
        int match = b.match(uri);
        e.a("PushSelfShowLog_RichMediaProvider", "uri is:" + uri + ",match result: " + match);
        if (this.a == null) {
            e.d("PushSelfShowLog_RichMediaProvider", "dbHelper is null");
            return null;
        }
        SQLiteDatabase readableDatabase = this.a.getReadableDatabase();
        if (readableDatabase == null) {
            e.d("PushSelfShowLog_RichMediaProvider", "db is null");
            return null;
        }
        switch (match) {
            case 1:
                Cursor matrixCursor = new MatrixCursor(new String[]{"isSupport"});
                matrixCursor.addRow(new Integer[]{Integer.valueOf(1)});
                return matrixCursor;
            case 4:
                try {
                    return readableDatabase.query("notify", new String[]{"bmp"}, "url = ?", strArr2, null, null, str2, null);
                } catch (Throwable e) {
                    e.c("PushSelfShowLog_RichMediaProvider", e.toString(), e);
                    break;
                }
            case 6:
                try {
                    return readableDatabase.rawQuery("SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp  FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url and pushmsg.url = ? order by pushmsg._id desc limit 1000;", strArr2);
                } catch (Throwable e2) {
                    e.c("PushSelfShowLog_RichMediaProvider", e2.toString(), e2);
                    break;
                }
            default:
                e.d("PushSelfShowLog_RichMediaProvider", "uri not match!");
                break;
        }
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int i = 0;
        int match = b.match(uri);
        e.a("PushSelfShowLog_RichMediaProvider", "uri is:" + uri + ",match result: " + match);
        if (this.a != null) {
            switch (match) {
                case 3:
                    SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                    if (writableDatabase == null) {
                        e.d("PushSelfShowLog_RichMediaProvider", "db is null");
                        break;
                    }
                    try {
                        i = writableDatabase.update("notify", contentValues, "url = ?", strArr);
                        getContext().getContentResolver().notifyChange(uri, null);
                        break;
                    } catch (Throwable e) {
                        e.c("PushSelfShowLog_RichMediaProvider", e.toString(), e);
                        break;
                    } finally {
                        writableDatabase.close();
                    }
                default:
                    e.d("PushSelfShowLog_RichMediaProvider", "uri not match!");
                    break;
            }
        }
        e.d("PushSelfShowLog_RichMediaProvider", "dbHelper is null");
        return i;
    }
}
