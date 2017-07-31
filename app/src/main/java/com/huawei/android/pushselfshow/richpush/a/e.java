package com.huawei.android.pushselfshow.richpush.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import com.huawei.android.pushselfshow.utils.a.b;

public class e implements a {
    private String a;

    public e() {
        this.a = null;
        this.a = null;
    }

    protected e(String str) {
        this.a = null;
        this.a = str;
    }

    private static void a(Context context, SQLiteDatabase sQLiteDatabase, String str, ContentValues contentValues) throws Exception {
        Cursor query;
        Throwable e;
        if (context == null) {
            com.huawei.android.pushagent.c.a.e.d("PushSelfShowLog", "context is null");
        } else if (sQLiteDatabase == null) {
            com.huawei.android.pushagent.c.a.e.d("PushSelfShowLog", "db is null");
        } else if (TextUtils.isEmpty(str)) {
            com.huawei.android.pushagent.c.a.e.d("PushSelfShowLog", "table is null");
        } else {
            try {
                query = sQLiteDatabase.query(str, null, null, null, null, null, null);
                if (query == null) {
                    try {
                        com.huawei.android.pushagent.c.a.e.d("PushSelfShowLog", "cursor is null");
                        if (query != null) {
                            query.close();
                        }
                        sQLiteDatabase.close();
                        return;
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            com.huawei.android.pushagent.c.a.e.c("PushSelfShowLog", e.toString(), e);
                            if (query != null) {
                                query.close();
                            }
                            sQLiteDatabase.close();
                        } catch (Throwable th) {
                            e = th;
                            if (query != null) {
                                query.close();
                            }
                            sQLiteDatabase.close();
                            throw e;
                        }
                    }
                }
                int count = query.getCount();
                com.huawei.android.pushagent.c.a.e.a("PushSelfShowLog", "queryAndInsert, exist rowNumber:" + count);
                if (count < 1000) {
                    sQLiteDatabase.insert(str, null, contentValues);
                } else {
                    com.huawei.android.pushagent.c.a.e.d("PushSelfShowLog", "queryAndInsert failed");
                }
                if (query != null) {
                    query.close();
                }
                sQLiteDatabase.close();
            } catch (Exception e3) {
                e = e3;
                query = null;
                com.huawei.android.pushagent.c.a.e.c("PushSelfShowLog", e.toString(), e);
                if (query != null) {
                    query.close();
                }
                sQLiteDatabase.close();
            } catch (Throwable th2) {
                e = th2;
                query = null;
                if (query != null) {
                    query.close();
                }
                sQLiteDatabase.close();
                throw e;
            }
        }
    }

    public Cursor a(Context context, Uri uri, String str, String[] strArr) throws Exception {
        SQLiteDatabase readableDatabase = a(context).getReadableDatabase();
        if (readableDatabase != null) {
            try {
                return readableDatabase.rawQuery(str, strArr);
            } catch (Throwable e) {
                com.huawei.android.pushagent.c.a.e.c("PushSelfShowLog", e.toString(), e);
            }
        }
        return null;
    }

    b a(Context context) {
        return this.a == null ? b.a(context) : b.a(context, this.a);
    }

    public void a(Context context, Uri uri, String str, ContentValues contentValues) throws Exception {
        a(context, a(context).getWritableDatabase(), str, contentValues);
    }

    public void a(Context context, Uri uri, String str, String str2, String[] strArr) throws Exception {
        SQLiteDatabase writableDatabase = a(context).getWritableDatabase();
        if (writableDatabase != null) {
            try {
                writableDatabase.delete(str, str2, strArr);
            } catch (Throwable e) {
                com.huawei.android.pushagent.c.a.e.c("PushSelfShowLog", e.toString(), e);
            } finally {
                writableDatabase.close();
            }
        }
    }
}
