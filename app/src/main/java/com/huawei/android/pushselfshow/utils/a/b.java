package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.huawei.android.pushagent.c.a.e;

public class b extends SQLiteOpenHelper {
    private static b a = null;
    private static b b = null;

    private b(Context context) {
        super(context, "push.db", null, 1);
        e.a("PushSelfShowLog", "DBHelper instance, version is 1");
    }

    private b(Context context, String str) {
        super(context, str, null, 1);
        e.a("PushSelfShowLog", "DBHelper instance, version is 1");
    }

    public static synchronized b a(Context context) {
        b bVar;
        synchronized (b.class) {
            if (a != null) {
                bVar = a;
            } else {
                a = new b(context);
                bVar = a;
            }
        }
        return bVar;
    }

    public static synchronized b a(Context context, String str) {
        b bVar;
        synchronized (b.class) {
            if (b != null) {
                bVar = b;
            } else {
                b = new b(context, str);
                bVar = b;
            }
        }
        return bVar;
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        e.a("PushSelfShowLog", "updateVersionFrom0To1");
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("token", " ".getBytes("UTF-8"));
            sQLiteDatabase.update("pushmsg", contentValues, null, null);
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private boolean a(SQLiteDatabase sQLiteDatabase, String str) {
        Throwable e;
        if (sQLiteDatabase == null) {
            return false;
        }
        Cursor query;
        try {
            query = sQLiteDatabase.query("sqlite_master", null, "(tbl_name='" + str + "')", null, null, null, null);
            if (query != null) {
                try {
                    query.moveToFirst();
                    boolean z = query.getCount() > 0;
                    if (query != null) {
                        query.close();
                    }
                    return z;
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.c("PushSelfShowLog", e.toString(), e);
                        if (query != null) {
                            return false;
                        }
                        query.close();
                        return false;
                    } catch (Throwable th) {
                        e = th;
                        if (query != null) {
                            query.close();
                        }
                        throw e;
                    }
                }
            } else if (query == null) {
                return false;
            } else {
                query.close();
                return false;
            }
        } catch (Exception e3) {
            e = e3;
            query = null;
            e.c("PushSelfShowLog", e.toString(), e);
            if (query != null) {
                return false;
            }
            query.close();
            return false;
        } catch (Throwable th2) {
            e = th2;
            query = null;
            if (query != null) {
                query.close();
            }
            throw e;
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        e.a("PushSelfShowLog", "onCreate");
        if (a(sQLiteDatabase, "pushmsg")) {
            e.a("PushSelfShowLog", "old table is exist");
            onUpgrade(sQLiteDatabase, 0, 1);
            return;
        }
        try {
            sQLiteDatabase.execSQL("create table notify(url  TEXT  PRIMARY KEY , bmp  BLOB );");
            sQLiteDatabase.execSQL("create table pushmsg( _id INTEGER PRIMARY KEY AUTOINCREMENT, url  TEXT  , token  BLOB ,msg  BLOB );");
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        e.a("PushSelfShowLog", "onDowngrade,oldVersion:" + i + ",newVersion:" + i2);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        e.a("PushSelfShowLog", "onUpgrade,oldVersion:" + i + ",newVersion:" + i2);
        if (i == 0) {
            a(sQLiteDatabase);
        }
    }
}
