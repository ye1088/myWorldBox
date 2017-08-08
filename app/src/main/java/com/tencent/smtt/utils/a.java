package com.tencent.smtt.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class a {
    public static String a = "";
    public static String b = "";
    public static String c = "";
    public static String d = "";
    public static String e = "";

    public static String a() {
        String a;
        Throwable th;
        Throwable th2;
        BufferedReader bufferedReader = null;
        if (!TextUtils.isEmpty(c)) {
            return c;
        }
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(Runtime.getRuntime().exec("getprop ro.product.cpu.abi").getInputStream());
            try {
                BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader);
                try {
                    a = bufferedReader2.readLine().contains("x86") ? a("i686") : a(System.getProperty("os.arch"));
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e) {
                        }
                    }
                    if (inputStreamReader == null) {
                        return a;
                    }
                    try {
                        inputStreamReader.close();
                        return a;
                    } catch (IOException e2) {
                        return a;
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    throw th2;
                }
            } catch (Throwable th22) {
                th = th22;
                a = a(System.getProperty("os.arch"));
                th.printStackTrace();
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    return a;
                }
                inputStreamReader.close();
                return a;
            }
        } catch (Throwable th4) {
            th22 = th4;
            inputStreamReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            throw th22;
        }
    }

    public static String a(Context context) {
        String str = null;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            return str;
        }
    }

    public static String a(Context context, String str) {
        String str2 = null;
        try {
            try {
                str2 = String.valueOf(Integer.toHexString(Integer.parseInt(String.valueOf(context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get(str)))));
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
        return str2;
    }

    private static String a(String str) {
        return str == null ? "" : str;
    }

    public static int b(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            return i;
        }
    }

    public static String c(Context context) {
        String str = "";
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        try {
            return ((TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe)).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String d(Context context) {
        String str = "";
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        try {
            return ((TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe)).getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String e(Context context) {
        String str = "";
        if (!TextUtils.isEmpty(d)) {
            return d;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            WifiInfo connectionInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();
            return connectionInfo == null ? "" : connectionInfo.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String f(Context context) {
        if (!TextUtils.isEmpty(e)) {
            return e;
        }
        try {
            e = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return e;
    }
}
