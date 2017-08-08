package com.hianalytics.android.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DeflaterOutputStream;
import u.aly.bi;

public final class a {
    static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static boolean b = true;
    private static Long dO = Long.valueOf(30);
    private static Long dP = Long.valueOf(86400);
    private static Long dQ = Long.valueOf(1000);
    private static Long dR = Long.valueOf(1800);
    private static HandlerThread dS;
    private static HandlerThread dT;
    private static int g = Integer.MAX_VALUE;
    private static Handler j;
    private static Handler k;

    static {
        HandlerThread handlerThread = new HandlerThread("HiAnalytics_messageThread");
        dS = handlerThread;
        handlerThread.start();
        handlerThread = new HandlerThread("HiAnalytics_sessionThread");
        dT = handlerThread;
        handlerThread.start();
    }

    public static String[] F(Context context) {
        String[] strArr = new String[]{"Unknown", "Unknown"};
        if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            strArr[0] = "Unknown";
            return strArr;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            strArr[0] = "Unknown";
            return strArr;
        } else if (connectivityManager.getNetworkInfo(1).getState() == State.CONNECTED) {
            strArr[0] = bi.d;
            return strArr;
        } else {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
            if (networkInfo.getState() != State.CONNECTED) {
                return strArr;
            }
            strArr[0] = bi.c;
            strArr[1] = networkInfo.getSubtypeName();
            return strArr;
        }
    }

    public static long a(String str) {
        long j = 0;
        try {
            Date parse = new SimpleDateFormat("yyyyMMddHHmmss").parse(str);
            if (parse != null) {
                j = parse.getTime();
            }
        } catch (ParseException e) {
            e.toString();
        }
        return j / 1000;
    }

    public static String a(Context context) {
        String str = "";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Object obj = applicationInfo.metaData.get("APPKEY");
                if (obj != null) {
                    str = obj.toString();
                }
            }
        } catch (Exception e) {
        }
        return (str == null || str.trim().length() == 0) ? context.getPackageName() : str;
    }

    public static void a(int i) {
        g = i;
    }

    public static void a(Long l) {
        dO = l;
    }

    public static void a(boolean z) {
        b = z;
    }

    public static boolean a(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    public static byte[] a(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream;
        DeflaterOutputStream deflaterOutputStream;
        Exception e;
        Throwable th;
        byte[] bArr2 = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
                try {
                    deflaterOutputStream.write(bArr);
                    deflaterOutputStream.close();
                    bArr2 = byteArrayOutputStream.toByteArray();
                    try {
                        deflaterOutputStream.close();
                        byteArrayOutputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (Exception e3) {
                    e = e3;
                    try {
                        e.printStackTrace();
                        if (deflaterOutputStream != null) {
                            try {
                                deflaterOutputStream.close();
                                byteArrayOutputStream.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        return bArr2;
                    } catch (Throwable th2) {
                        th = th2;
                        if (deflaterOutputStream != null) {
                            try {
                                deflaterOutputStream.close();
                                byteArrayOutputStream.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            } catch (Exception e4) {
                e = e4;
                deflaterOutputStream = bArr2;
                e.printStackTrace();
                if (deflaterOutputStream != null) {
                    deflaterOutputStream.close();
                    byteArrayOutputStream.close();
                }
                return bArr2;
            } catch (Throwable th3) {
                deflaterOutputStream = bArr2;
                th = th3;
                if (deflaterOutputStream != null) {
                    deflaterOutputStream.close();
                    byteArrayOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            deflaterOutputStream = bArr2;
            byteArrayOutputStream = bArr2;
            e.printStackTrace();
            if (deflaterOutputStream != null) {
                deflaterOutputStream.close();
                byteArrayOutputStream.close();
            }
            return bArr2;
        } catch (Throwable th32) {
            deflaterOutputStream = bArr2;
            byteArrayOutputStream = bArr2;
            th = th32;
            if (deflaterOutputStream != null) {
                deflaterOutputStream.close();
                byteArrayOutputStream.close();
            }
            throw th;
        }
        return bArr2;
    }

    public static String b(Context context) {
        String str = "Unknown";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                Object obj = applicationInfo.metaData.get("CHANNEL");
                if (obj != null) {
                    str = obj.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String b(String str) {
        return (str == null || str.equals("")) ? "000000000000000" : str;
    }

    public static String b(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            stringBuilder.append(a[(b & b.bpd) >> 4]).append(a[b & 15]);
        }
        return stringBuilder.toString();
    }

    public static void b(Long l) {
        dP = l;
    }

    public static Long bl() {
        return dO;
    }

    public static Long bm() {
        return dP;
    }

    public static Long bn() {
        return dR;
    }

    public static Handler bo() {
        if (j == null) {
            Looper looper = dS.getLooper();
            if (looper == null) {
                return null;
            }
            j = new Handler(looper);
        }
        return j;
    }

    public static Handler bp() {
        if (k == null) {
            Looper looper = dT.getLooper();
            if (looper == null) {
                return null;
            }
            k = new Handler(looper);
        }
        return k;
    }

    public static void c(Long l) {
        dQ = l;
    }

    public static int d() {
        return g;
    }

    public static void d(Long l) {
        dR = l;
    }

    public static boolean d(Context context) {
        if (dQ.longValue() < 0) {
            return false;
        }
        String packageName = context.getPackageName();
        return new File(new StringBuilder("/data/data/").append(packageName).append("/shared_prefs/").append(new StringBuilder("hianalytics_state_").append(packageName).append(".xml").toString()).toString()).length() > dQ.longValue();
    }

    public static String e(Context context) {
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            return "unknown";
        }
    }

    public static boolean e() {
        return b;
    }

    public static boolean f(Context context) {
        SharedPreferences a = c.a(context, "flag");
        String str = Build.DISPLAY;
        String string = a.getString("rom_version", "");
        "currentRom=" + str + ",lastRom=" + string;
        return "".equals(string) || !string.equals(str);
    }

    public static void h() {
    }

    public static String i() {
        String str = "http://data.hicloud.com:8089/sdkv1";
        "URL = " + str;
        return str;
    }
}
