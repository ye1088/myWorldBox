package com.tencent.mm.sdk.plugin;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.mm.sdk.storage.IAutoDBItem;
import com.tencent.mm.sdk.storage.IAutoDBItem.MAutoDBInfo;
import java.lang.reflect.Field;

public abstract class BaseProfile extends IAutoDBItem {
    public static final String COL_ALIAS = "alias";
    public static final String COL_AVATAR = "avatar";
    public static final String COL_BINDEMAIL = "bindemail";
    public static final String COL_BINDMOBILE = "bindmobile";
    public static final String COL_BINDQQ = "bindqq";
    public static final String COL_CITY = "city";
    public static final String COL_NICKNAME = "nickname";
    public static final String COL_PROVINCE = "province";
    public static final String COL_SIGNATURE = "signature";
    public static final String COL_USERNAME = "username";
    public static final String COL_WEIBO = "weibo";
    public static final String[] INDEX_CREATE = new String[0];
    public static final String TABLE_NAME = "Profile";
    public String field_alias;
    public String field_avatar;
    public String field_bindemail;
    public String field_bindmobile;
    public long field_bindqq;
    public String field_city;
    public String field_nickname;
    public String field_province;
    public String field_signature;
    public String field_username;
    public String field_weibo;

    public static MAutoDBInfo initAutoDBInfo(Class<?> cls) {
        MAutoDBInfo mAutoDBInfo = new MAutoDBInfo();
        mAutoDBInfo.fields = new Field[11];
        mAutoDBInfo.columns = new String[12];
        StringBuilder stringBuilder = new StringBuilder();
        mAutoDBInfo.columns[0] = "username";
        mAutoDBInfo.colsMap.put("username", "TEXT");
        stringBuilder.append(" username TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[1] = COL_BINDQQ;
        mAutoDBInfo.colsMap.put(COL_BINDQQ, "LONG");
        stringBuilder.append(" bindqq LONG");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[2] = COL_BINDMOBILE;
        mAutoDBInfo.colsMap.put(COL_BINDMOBILE, "TEXT");
        stringBuilder.append(" bindmobile TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[3] = COL_BINDEMAIL;
        mAutoDBInfo.colsMap.put(COL_BINDEMAIL, "TEXT");
        stringBuilder.append(" bindemail TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[4] = COL_ALIAS;
        mAutoDBInfo.colsMap.put(COL_ALIAS, "TEXT");
        stringBuilder.append(" alias TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[5] = COL_NICKNAME;
        mAutoDBInfo.colsMap.put(COL_NICKNAME, "TEXT");
        stringBuilder.append(" nickname TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[6] = COL_SIGNATURE;
        mAutoDBInfo.colsMap.put(COL_SIGNATURE, "TEXT");
        stringBuilder.append(" signature TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[7] = COL_PROVINCE;
        mAutoDBInfo.colsMap.put(COL_PROVINCE, "TEXT");
        stringBuilder.append(" province TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[8] = COL_CITY;
        mAutoDBInfo.colsMap.put(COL_CITY, "TEXT");
        stringBuilder.append(" city TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[9] = COL_WEIBO;
        mAutoDBInfo.colsMap.put(COL_WEIBO, "TEXT");
        stringBuilder.append(" weibo TEXT");
        stringBuilder.append(", ");
        mAutoDBInfo.columns[10] = COL_AVATAR;
        mAutoDBInfo.colsMap.put(COL_AVATAR, "TEXT");
        stringBuilder.append(" avatar TEXT");
        mAutoDBInfo.columns[11] = "rowid";
        mAutoDBInfo.sql = stringBuilder.toString();
        return mAutoDBInfo;
    }

    public void convertFrom(Cursor cursor) {
        int columnIndex = cursor.getColumnIndex("username");
        if (columnIndex >= 0) {
            this.field_username = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_BINDQQ);
        if (columnIndex >= 0) {
            this.field_bindqq = cursor.getLong(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_BINDMOBILE);
        if (columnIndex >= 0) {
            this.field_bindmobile = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_BINDEMAIL);
        if (columnIndex >= 0) {
            this.field_bindemail = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_ALIAS);
        if (columnIndex >= 0) {
            this.field_alias = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_NICKNAME);
        if (columnIndex >= 0) {
            this.field_nickname = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_SIGNATURE);
        if (columnIndex >= 0) {
            this.field_signature = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_PROVINCE);
        if (columnIndex >= 0) {
            this.field_province = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_CITY);
        if (columnIndex >= 0) {
            this.field_city = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_WEIBO);
        if (columnIndex >= 0) {
            this.field_weibo = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex(COL_AVATAR);
        if (columnIndex >= 0) {
            this.field_avatar = cursor.getString(columnIndex);
        }
        columnIndex = cursor.getColumnIndex("rowid");
        if (columnIndex >= 0) {
            this.systemRowid = cursor.getLong(columnIndex);
        }
    }

    public ContentValues convertTo() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", this.field_username);
        contentValues.put(COL_BINDQQ, Long.valueOf(this.field_bindqq));
        contentValues.put(COL_BINDMOBILE, this.field_bindmobile);
        contentValues.put(COL_BINDEMAIL, this.field_bindemail);
        contentValues.put(COL_ALIAS, this.field_alias);
        contentValues.put(COL_NICKNAME, this.field_nickname);
        contentValues.put(COL_SIGNATURE, this.field_signature);
        contentValues.put(COL_PROVINCE, this.field_province);
        contentValues.put(COL_CITY, this.field_city);
        contentValues.put(COL_WEIBO, this.field_weibo);
        contentValues.put(COL_AVATAR, this.field_avatar);
        if (this.systemRowid > 0) {
            contentValues.put("rowid", Long.valueOf(this.systemRowid));
        }
        return contentValues;
    }

    public void reset() {
    }
}
