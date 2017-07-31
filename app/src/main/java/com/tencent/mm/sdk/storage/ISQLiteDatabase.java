package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;

public interface ISQLiteDatabase {
    int delete(String str, String str2, String[] strArr);

    boolean execSQL(String str, String str2);

    long insert(String str, String str2, ContentValues contentValues);

    Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5);

    Cursor rawQuery(String str, String[] strArr);

    long replace(String str, String str2, ContentValues contentValues);

    int update(String str, ContentValues contentValues, String str2, String[] strArr);
}
