package com.MCWorld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.MCWorld.widget.Constants.AppType;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.tools.ant.util.DateUtils;

@SuppressLint({"SimpleDateFormat"})
/* compiled from: GlobalConfigApp */
public class g {
    public static final boolean fc = true;
    public static final int fe = AppType.MCTOOL.Value();
    private static SharedPreferences ff = null;
    private static String fg = "create_icon";
    private static boolean fh = false;
    private static String fi = "float_ok";
    private static final String fj = "day_";
    private static final String fk = "root_";

    public static void M(Context c) {
        ff = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static boolean bx() {
        if (ff.contains(fg)) {
            return true;
        }
        ff.edit().putBoolean(fg, true).commit();
        return false;
    }

    public static boolean by() {
        if (fh) {
            return true;
        }
        return ff.contains(fi);
    }

    public static void bz() {
        if (!fh && !ff.contains(fi)) {
            fh = true;
            ff.edit().putBoolean(fi, true).commit();
        }
    }

    public static void v(String typeName) {
        ff.edit().putString(fj + typeName, new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN).format(new Date())).commit();
    }

    public static boolean w(String typeName) {
        if (HTApplication.DEBUG) {
            return false;
        }
        String strLastDay = ff.getString(fj + typeName, "");
        if (strLastDay.length() != 0) {
            return new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN).format(new Date()).equals(strLastDay);
        }
        return false;
    }

    public static void g(String typeName, String packName) {
        ff.edit().putString(fj + typeName, packName).commit();
    }

    public static String x(String typeName) {
        String strPackName = ff.getString(fk + typeName, "");
        if (strPackName.length() == 0) {
            return null;
        }
        return strPackName;
    }

    public static void a(String typeName, Boolean fixed) {
        ff.edit().putBoolean(typeName, fixed.booleanValue()).commit();
    }

    public static Boolean y(String typeName) {
        return Boolean.valueOf(ff.getBoolean(typeName, false));
    }
}
