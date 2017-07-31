package com.tencent.stat.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class StatPreferences {
    private static SharedPreferences defaultPerferences = null;

    static SharedPreferences getInstance(Context context) {
        if (defaultPerferences == null) {
            defaultPerferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return defaultPerferences;
    }

    public static long getLong(Context context, String str, long j) {
        return getInstance(context).getLong("" + str, j);
    }

    public static String getString(Context context, String str, String str2) {
        return getInstance(context).getString("" + str, str2);
    }

    public static void putLong(Context context, String str, long j) {
        String str2 = "" + str;
        Editor edit = getInstance(context).edit();
        edit.putLong(str2, j);
        edit.commit();
    }

    public static void putString(Context context, String str, String str2) {
        String str3 = "" + str;
        Editor edit = getInstance(context).edit();
        edit.putString(str3, str2);
        edit.commit();
    }
}
