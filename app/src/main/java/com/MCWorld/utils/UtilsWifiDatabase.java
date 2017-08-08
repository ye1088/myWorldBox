package com.MCWorld.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class UtilsWifiDatabase extends SQLiteOpenHelper {
    private static final String bnA = "ssid";
    private static final String bnB = "psdtype";
    private static final String bnC = "password";
    private static final String bnD = "submit";
    private static final String bnE = "lasttime";
    private static UtilsWifiDatabase bnF = null;
    private static final String bnI = "http://wifi.huluxia.net/wifi/saveBatch";
    private static final int bnx = 1;
    private static final String bny = "hlx_wifi.db";
    private static final String bnz = "confinfo";
    private static final String gA = "hlxsystem";
    private Map<String, a> bnG = null;
    private b bnH = new b(this, null);

    public static UtilsWifiDatabase MK() {
        return bnF;
    }

    public static UtilsWifiDatabase by(Context context) {
        if (bnF != null) {
            return bnF;
        }
        bnF = new UtilsWifiDatabase(context, bny, null, 1);
        bnF.ML();
        return bnF;
    }

    public UtilsWifiDatabase(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        b(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
    }

    public void f(ByteBuffer buff) {
        int nMaxCnt = buff.getInt();
        if (nMaxCnt >= 512) {
            nMaxCnt = 512;
        }
        int n = 0;
        while (n < nMaxCnt && buff.getInt() == n) {
            a info = new a();
            info.bnK = aw.e(buff);
            info.bnL = aw.e(buff);
            info.password = aw.e(buff);
            if (info.bnL.length() != 0) {
                a(info);
            }
            n++;
        }
    }

    private a gA(String ssid) {
        if (this.bnG == null) {
            ML();
        }
        if (this.bnG == null) {
            return null;
        }
        return (a) this.bnG.get(ssid);
    }

    private Map<String, a> ML() {
        if (this.bnG != null) {
            return this.bnG;
        }
        this.bnG = new HashMap();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM confinfo", null);
        while (c.moveToNext()) {
            a info = new a();
            info.bnK = c.getString(c.getColumnIndex(bnA));
            info.bnL = c.getString(c.getColumnIndex(bnB));
            info.password = c.getString(c.getColumnIndex(bnC));
            info.bnJ = (long) c.getInt(c.getColumnIndex(bnD));
            this.bnG.put(info.bnK, info);
        }
        c.close();
        db.close();
        return this.bnG;
    }

    private void a(a info) {
        if (info.bnK != null && info.bnK.length() != 0 && info.bnL != null && info.bnL.length() != 0) {
            a old = gA(info.bnK);
            if (old == null || old.password.length() <= 0) {
                this.bnG.put(info.bnK, info);
                if (info.password.length() != 0) {
                    ContentValues values = new ContentValues();
                    values.put(bnA, info.bnK);
                    values.put(bnB, info.bnL);
                    values.put(bnC, info.password);
                    values.put(bnD, Long.valueOf(info.bnJ));
                    SQLiteDatabase db = getWritableDatabase();
                    db.insert(bnz, null, values);
                    db.close();
                    return;
                }
                return;
            }
            a(info, old);
        }
    }

    private void a(a info, a old) {
        if (info.password != null && info.password.length() != 0) {
            boolean isChangeInfo = true;
            if (old.bnL.equals(info.bnL) && old.password.equals(info.password)) {
                if (info.bnJ > 0) {
                    old.bnJ = info.bnJ;
                }
                isChangeInfo = false;
            }
            if (isChangeInfo) {
                old.bnJ = 0;
                old.bnL = info.bnL;
                old.password = info.password;
            }
            ContentValues values = new ContentValues();
            values.put(bnB, old.bnL);
            values.put(bnC, old.password);
            values.put(bnD, Long.valueOf(old.bnJ));
            SQLiteDatabase db = getWritableDatabase();
            db.update(bnz, values, "ssid=?", new String[]{old.bnK});
            db.close();
        }
    }

    private void b(SQLiteDatabase db) {
        db.execSQL((((("create table if not exists confinfo (" + "ssid varchar(64),  ") + "psdtype varchar(10),  ") + "password varchar(64),  ") + "submit Integer,      ") + "lasttime Integer )     ");
    }

    public void MM() {
        if (!this.bnH.MN()) {
            this.bnH.MO();
        }
    }
}
