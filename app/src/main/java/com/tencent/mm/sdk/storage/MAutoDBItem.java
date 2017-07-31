package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.mm.sdk.platformtools.Log;
import com.tencent.mm.sdk.platformtools.Util;
import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class MAutoDBItem extends IAutoDBItem {
    public void convertFrom(Cursor cursor) {
        int i = 0;
        String[] columnNames = cursor.getColumnNames();
        if (columnNames == null) {
            Log.e("MicroMsg.SDK.MAutoDBItem", "convertFrom: get column names failed");
            return;
        }
        int i2;
        HashMap hashMap = new HashMap();
        for (i2 = 0; i2 < columnNames.length; i2++) {
            hashMap.put(columnNames[i2], Integer.valueOf(i2));
        }
        Field[] fieldArr = getDBInfo().fields;
        int length = fieldArr.length;
        while (i < length) {
            Field field = fieldArr[i];
            String colName = IAutoDBItem.getColName(field);
            if (!Util.isNullOrNil(colName)) {
                i2 = Util.nullAs((Integer) hashMap.get(colName), -1);
                if (i2 >= 0) {
                    try {
                        CursorFieldHelper.setter(field.getType()).invoke(field, this, cursor, i2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            i++;
        }
        i2 = Util.nullAs((Integer) hashMap.get("rowid"), -1);
        if (i2 >= 0) {
            this.systemRowid = cursor.getLong(i2);
        }
    }

    public ContentValues convertTo() {
        ContentValues contentValues = new ContentValues();
        for (Field field : getDBInfo().fields) {
            try {
                CursorFieldHelper.getter(field.getType()).invoke(field, this, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.systemRowid > 0) {
            contentValues.put("rowid", Long.valueOf(this.systemRowid));
        }
        return contentValues;
    }
}
