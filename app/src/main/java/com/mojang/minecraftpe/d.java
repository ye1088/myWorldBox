package com.mojang.minecraftpe;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/* compiled from: Utils */
public class d {
    public static boolean bID = false;
    protected static Context mContext = null;

    public static void B(File dir) {
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File f : fileList) {
                if (f.isDirectory()) {
                    B(f);
                }
                f.delete();
            }
        }
    }

    public static Field b(Class<?> paramClass, String paramString) {
        if (paramClass == null) {
            return null;
        }
        try {
            return paramClass.getDeclaredField(paramString);
        } catch (NoSuchFieldException e) {
            return b(paramClass.getSuperclass(), paramString);
        }
    }

    public static Set<String> Ro() {
        String str = mB(1).getString("enabledPatches", "");
        if (str.equals("")) {
            return new HashSet();
        }
        return new HashSet(Arrays.asList(str.split(";")));
    }

    public static Set<String> getEnabledScripts() {
        String str = mB(1).getString("enabledScripts", "");
        if (str.equals("")) {
            return new HashSet();
        }
        return new HashSet(Arrays.asList(str.split(";")));
    }

    public static int Rp() {
        return mContext.getResources().getInteger(3);
    }

    public static int Rq() {
        return mContext.getResources().getInteger(2);
    }

    public static SharedPreferences mB(int type) {
        Ru();
        switch (type) {
            case 0:
                return PreferenceManager.getDefaultSharedPreferences(mContext);
            case 1:
                return mContext.getSharedPreferences("mcpelauncherprefs", 0);
            case 2:
                return mContext.getSharedPreferences("safe_mode_counter", 0);
            default:
                return null;
        }
    }

    public static boolean Rr() {
        int i = Rp();
        return i >= 0 && Ro().size() >= i;
    }

    public static boolean Rs() {
        int i = Rq();
        return i >= 0 && getEnabledScripts().size() >= i;
    }

    public static boolean Rt() {
        return mContext.getPackageName().equals("net.zhuoweizhang.mcpelauncher.pro");
    }

    public static boolean isSafeMode() {
        return true;
    }

    public static String join(Collection<?> paramCollection, String paramString) {
        StringBuilder localStringBuilder = new StringBuilder();
        for (Object localObject : paramCollection) {
            localStringBuilder.append(paramString).append(localObject.toString());
        }
        String str = localStringBuilder.toString();
        if (str.length() >= paramString.length()) {
            return str.substring(paramString.length());
        }
        return str;
    }

    protected static void Ru() {
        if (mContext == null) {
            throw new RuntimeException("Tried to work with Utils class without context");
        }
    }

    public static void setContext(Context paramContext) {
        mContext = paramContext;
    }

    public static void Rv() {
        Ru();
        String str1 = mB(0).getString("zz_language_override", "");
        if (str1.length() != 0) {
            String[] arrayOfString = str1.split("_");
            String str2 = arrayOfString[0];
            String str3;
            Resources localResources;
            Configuration localConfiguration;
            DisplayMetrics localDisplayMetrics;
            if (arrayOfString.length > 1) {
                str3 = arrayOfString[1];
                localResources = mContext.getResources();
                localConfiguration = new Configuration(localResources.getConfiguration());
                localDisplayMetrics = localResources.getDisplayMetrics();
                localConfiguration.locale = new Locale(str2, str3);
                localResources.updateConfiguration(localConfiguration, localDisplayMetrics);
            } else {
                str3 = arrayOfString[1];
                localResources = mContext.getResources();
                localConfiguration = new Configuration(localResources.getConfiguration());
                localDisplayMetrics = localResources.getDisplayMetrics();
                localConfiguration.locale = new Locale(str2, str3);
                localResources.updateConfiguration(localConfiguration, localDisplayMetrics);
            }
        }
    }

    public static void bP(Context mContext) {
    }
}
