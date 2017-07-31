package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import java.io.File;

public class d {
    private static d a = new d();

    private d() {
    }

    private SQLiteDatabase a(String str) {
        File file = new File(str);
        if (file.exists()) {
            return SQLiteDatabase.openDatabase(str, null, 0);
        }
        file = file.getParentFile();
        if (!(file == null || file.exists() || !file.mkdirs())) {
            e.e(BLocation.TAG, "datafiledir.mkdirs true");
        }
        return SQLiteDatabase.openOrCreateDatabase(str, null);
    }

    public static d a() {
        return a;
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.close();
    }

    public Cursor a(String str, String str2, String str3) {
        SQLiteDatabase a = a(str);
        if (a == null) {
            return null;
        }
        Cursor query = a.query(str2, null, str3, null, null, null, null);
        query.moveToFirst();
        a(a);
        return query;
    }

    public Cursor a(String str, String str2, String[] strArr) {
        SQLiteDatabase a = a(str);
        if (a == null) {
            return null;
        }
        Cursor rawQuery = a.rawQuery(str2, strArr);
        rawQuery.moveToFirst();
        a(a);
        return rawQuery;
    }

    public void a(Context context, String str, String str2) {
        SQLiteDatabase a = a(str);
        if (a != null) {
            a.execSQL(str2);
            a(a);
        }
    }

    public void a(Context context, String str, String str2, ContentValues contentValues) {
        SQLiteDatabase a = a(str);
        if (a != null) {
            a.insert(str2, null, contentValues);
            a(a);
        }
    }

    public void a(String str, String str2, String str3, String[] strArr) {
        SQLiteDatabase a = a(str);
        if (a != null) {
            a.delete(str2, str3, strArr);
            a(a);
        }
    }

    public boolean a(String str, String str2) {
        Cursor a = a(str, "sqlite_master", "(tbl_name='" + str2 + "')");
        if (a == null) {
            e.a(BLocation.TAG, "cursor is null.");
            return false;
        }
        int count = a.getCount();
        a.close();
        return count > 0;
    }
}
