package com.huawei.android.pushselfshow.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import com.huawei.android.pushagent.c.a.a.g;
import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.a.e;
import com.MCWorld.module.h;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.tools.ant.taskdefs.optional.clearcase.ClearCase;

public class a {
    private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static Typeface b = null;

    public static int a(int i, int i2) {
        e.a("PushSelfShowLog", "enter ctrlSockets(cmd:" + i + " param:" + i2 + ")");
        try {
            return ((Integer) Class.forName("dalvik.system.Zygote").getMethod("ctrlSockets", new Class[]{Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)})).intValue();
        } catch (NoSuchMethodException e) {
            e.d("PushSelfShowLog", "NoSuchMethodException:" + e);
            return -2;
        } catch (ClassNotFoundException e2) {
            e.d("PushSelfShowLog", "ClassNotFoundException:" + e2);
            return -2;
        } catch (IllegalAccessException e3) {
            e.d("PushSelfShowLog", "IllegalAccessException:" + e3);
            return -2;
        } catch (InvocationTargetException e4) {
            e.d("PushSelfShowLog", "InvocationTargetException:" + e4);
            return -2;
        } catch (RuntimeException e5) {
            e.d("PushSelfShowLog", "RuntimeException:" + e5);
            return -2;
        } catch (Exception e6) {
            e.d("PushSelfShowLog", "Exception:" + e6);
            return -2;
        }
    }

    public static int a(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static long a() {
        return System.currentTimeMillis();
    }

    public static long a(Context context) {
        e.a("PushSelfShowLog", "enter getVersion()");
        long a;
        try {
            List queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(new Intent("com.huawei.android.push.intent.REGISTER").setPackage(context.getPackageName()), h.arA);
            if (queryBroadcastReceivers == null || queryBroadcastReceivers.size() == 0) {
                return -1000;
            }
            a = a((ResolveInfo) queryBroadcastReceivers.get(0), "CS_cloud_version");
            e.a("PushSelfShowLog", "get the version is :" + a);
            return a;
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
            a = -1000;
        }
    }

    public static long a(ResolveInfo resolveInfo, String str) {
        long j = -1;
        if (resolveInfo != null) {
            try {
                j = Long.parseLong(b(resolveInfo, str));
            } catch (NumberFormatException e) {
                e.b("PushSelfShowLog", str + " is not set in " + a(resolveInfo));
            }
        }
        return j;
    }

    public static Intent a(Context context, String str) {
        Intent intent = null;
        try {
            intent = context.getPackageManager().getLaunchIntentForPackage(str);
        } catch (Throwable e) {
            e.b("PushSelfShowLog", e.toString(), e);
        }
        return intent;
    }

    public static Boolean a(Context context, String str, Intent intent) {
        try {
            List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            if (queryIntentActivities != null && queryIntentActivities.size() > 0) {
                int size = queryIntentActivities.size();
                int i = 0;
                while (i < size) {
                    if (((ResolveInfo) queryIntentActivities.get(i)).activityInfo != null && str.equals(((ResolveInfo) queryIntentActivities.get(i)).activityInfo.applicationInfo.packageName)) {
                        return Boolean.valueOf(true);
                    }
                    i++;
                }
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
        return Boolean.valueOf(false);
    }

    public static String a(Context context, String str, String str2) {
        try {
            if (context.getResources().getConfiguration().locale.getLanguage().endsWith("zh")) {
                return str;
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "getStringByLanguage failed ", e);
        }
        return str2;
    }

    public static String a(ResolveInfo resolveInfo) {
        return resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.packageName : resolveInfo.activityInfo.packageName;
    }

    public static String a(String str) {
        String str2 = "";
        String str3 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDf5raDExuuXbsVNCWl48yuB89W\rfNOuuhPuS2Mptii/0UorpzypBkNTTGt11E7aorCc1lFwlB+4KDMIpFyQsdChSk+A\rt9UfhFKa95uiDpMe5rMfU+DAhoXGER6WQ2qGtrHmBWVv33i3lc76u9IgEfYuLwC6\r1mhQDHzAKPiViY6oeQIDAQAB\r";
        try {
            str2 = a(g.a(str.getBytes("UTF-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDf5raDExuuXbsVNCWl48yuB89W\rfNOuuhPuS2Mptii/0UorpzypBkNTTGt11E7aorCc1lFwlB+4KDMIpFyQsdChSk+A\rt9UfhFKa95uiDpMe5rMfU+DAhoXGER6WQ2qGtrHmBWVv33i3lc76u9IgEfYuLwC6\r1mhQDHzAKPiViY6oeQIDAQAB\r"));
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "encrypter error ", e);
        }
        return str2;
    }

    public static String a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length);
        for (int i = 0; i < bArr.length; i++) {
            stringBuilder.append(a[(bArr[i] >>> 4) & 15]);
            stringBuilder.append(a[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }

    public static void a(Context context, Intent intent, long j) {
        try {
            e.a("PushSelfShowLog", "enter setAPDelayAlarm(intent:" + intent.toURI() + " interval:" + j + "ms, context:" + context);
            ((AlarmManager) context.getSystemService("alarm")).set(0, System.currentTimeMillis() + j, PendingIntent.getBroadcast(context, new SecureRandom().nextInt(), intent, 0));
        } catch (Throwable e) {
            e.a("PushSelfShowLog", "set DelayAlarm error", e);
        }
    }

    public static synchronized void a(Context context, TextView textView) {
        synchronized (a.class) {
            if (context == null || textView == null) {
                e.b("PushSelfShowLog", "context is null or textView is null");
            } else if (b.a() >= 9 && e()) {
                if (b == null) {
                    try {
                        b = Typeface.create("chnfzxh", 0);
                    } catch (Exception e) {
                        e.d("PushSelfShowLog", e.toString());
                    }
                }
                if (b != null) {
                    e.a("PushSelfShowLog", "setTypeFaceEx success");
                    textView.setTypeface(b);
                }
            }
        }
    }

    public static void a(Context context, String str, com.huawei.android.pushselfshow.b.a aVar) {
        if (aVar != null) {
            a(context, str, aVar.l, aVar.o);
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        new Thread(new b(context, str2, str, str3)).start();
    }

    public static void a(File file) {
        if (file != null) {
            e.a("PushSelfShowLog", "delete file " + file.getAbsolutePath());
            File file2 = new File(file.getAbsolutePath() + System.currentTimeMillis());
            if (!file.renameTo(file2)) {
                return;
            }
            if (!(file2.isFile() && file2.delete()) && file2.isDirectory()) {
                File[] listFiles = file2.listFiles();
                if (listFiles != null && listFiles.length != 0) {
                    for (File a : listFiles) {
                        a(a);
                    }
                    if (!file2.delete()) {
                        e.a("PushSelfShowLog", "delete file unsuccess");
                    }
                } else if (!file2.delete()) {
                    e.a("PushSelfShowLog", "delete file failed");
                }
            }
        }
    }

    public static void a(File file, File file2) {
        BufferedInputStream bufferedInputStream;
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream;
        Throwable e;
        FileOutputStream fileOutputStream2;
        BufferedInputStream bufferedInputStream2;
        FileInputStream fileInputStream;
        BufferedOutputStream bufferedOutputStream2 = null;
        FileInputStream fileInputStream2;
        try {
            fileInputStream2 = new FileInputStream(file);
            try {
                bufferedInputStream = new BufferedInputStream(fileInputStream2);
                try {
                    fileOutputStream = new FileOutputStream(file2);
                    try {
                        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        try {
                            byte[] bArr = new byte[5120];
                            while (true) {
                                int read = bufferedInputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                bufferedOutputStream.write(bArr, 0, read);
                            }
                            if (bufferedInputStream != null) {
                                try {
                                    bufferedInputStream.close();
                                } catch (Throwable e2) {
                                    e.d("PushSelfShowLog", "inBuff.close() ", e2);
                                }
                            }
                            if (bufferedOutputStream != null) {
                                try {
                                    bufferedOutputStream.flush();
                                } catch (Throwable e22) {
                                    e.c("PushSelfShowLog", e22.toString(), e22);
                                }
                                try {
                                    bufferedOutputStream.close();
                                } catch (Throwable e222) {
                                    e.c("PushSelfShowLog", e222.toString(), e222);
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable e2222) {
                                    e.d("PushSelfShowLog", "output.close() ", e2222);
                                }
                            }
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (Throwable e22222) {
                                    e.d("PushSelfShowLog", "input.close() ", e22222);
                                }
                            }
                        } catch (IOException e3) {
                            e22222 = e3;
                            fileOutputStream2 = fileOutputStream;
                            bufferedInputStream2 = bufferedInputStream;
                            fileInputStream = fileInputStream2;
                            try {
                                e.d("PushSelfShowLog", "copyFile ", e22222);
                                if (bufferedInputStream2 != null) {
                                    try {
                                        bufferedInputStream2.close();
                                    } catch (Throwable e222222) {
                                        e.d("PushSelfShowLog", "inBuff.close() ", e222222);
                                    }
                                }
                                if (bufferedOutputStream != null) {
                                    try {
                                        bufferedOutputStream.flush();
                                    } catch (Throwable e2222222) {
                                        e.c("PushSelfShowLog", e2222222.toString(), e2222222);
                                    }
                                    try {
                                        bufferedOutputStream.close();
                                    } catch (Throwable e22222222) {
                                        e.c("PushSelfShowLog", e22222222.toString(), e22222222);
                                    }
                                }
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (Throwable e222222222) {
                                        e.d("PushSelfShowLog", "output.close() ", e222222222);
                                    }
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Throwable e2222222222) {
                                        e.d("PushSelfShowLog", "input.close() ", e2222222222);
                                    }
                                }
                            } catch (Throwable th) {
                                e2222222222 = th;
                                fileInputStream2 = fileInputStream;
                                bufferedInputStream = bufferedInputStream2;
                                fileOutputStream = fileOutputStream2;
                                bufferedOutputStream2 = bufferedOutputStream;
                                if (bufferedInputStream != null) {
                                    try {
                                        bufferedInputStream.close();
                                    } catch (Throwable e4) {
                                        e.d("PushSelfShowLog", "inBuff.close() ", e4);
                                    }
                                }
                                if (bufferedOutputStream2 != null) {
                                    try {
                                        bufferedOutputStream2.flush();
                                    } catch (Throwable e42) {
                                        e.c("PushSelfShowLog", e42.toString(), e42);
                                    }
                                    try {
                                        bufferedOutputStream2.close();
                                    } catch (Throwable e422) {
                                        e.c("PushSelfShowLog", e422.toString(), e422);
                                    }
                                }
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (Throwable e4222) {
                                        e.d("PushSelfShowLog", "output.close() ", e4222);
                                    }
                                }
                                if (fileInputStream2 != null) {
                                    try {
                                        fileInputStream2.close();
                                    } catch (Throwable e42222) {
                                        e.d("PushSelfShowLog", "input.close() ", e42222);
                                    }
                                }
                                throw e2222222222;
                            }
                        } catch (Throwable th2) {
                            e2222222222 = th2;
                            bufferedOutputStream2 = bufferedOutputStream;
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                            }
                            if (bufferedOutputStream2 != null) {
                                bufferedOutputStream2.flush();
                                bufferedOutputStream2.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            throw e2222222222;
                        }
                    } catch (IOException e5) {
                        e2222222222 = e5;
                        bufferedOutputStream = null;
                        fileOutputStream2 = fileOutputStream;
                        bufferedInputStream2 = bufferedInputStream;
                        fileInputStream = fileInputStream2;
                        e.d("PushSelfShowLog", "copyFile ", e2222222222);
                        if (bufferedInputStream2 != null) {
                            bufferedInputStream2.close();
                        }
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.flush();
                            bufferedOutputStream.close();
                        }
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (Throwable th3) {
                        e2222222222 = th3;
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                        }
                        if (bufferedOutputStream2 != null) {
                            bufferedOutputStream2.flush();
                            bufferedOutputStream2.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        throw e2222222222;
                    }
                } catch (IOException e6) {
                    e2222222222 = e6;
                    bufferedOutputStream = null;
                    bufferedInputStream2 = bufferedInputStream;
                    fileInputStream = fileInputStream2;
                    e.d("PushSelfShowLog", "copyFile ", e2222222222);
                    if (bufferedInputStream2 != null) {
                        bufferedInputStream2.close();
                    }
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                    }
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (Throwable th4) {
                    e2222222222 = th4;
                    fileOutputStream = null;
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (bufferedOutputStream2 != null) {
                        bufferedOutputStream2.flush();
                        bufferedOutputStream2.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    throw e2222222222;
                }
            } catch (IOException e7) {
                e2222222222 = e7;
                bufferedOutputStream = null;
                bufferedInputStream2 = null;
                fileInputStream = fileInputStream2;
                e.d("PushSelfShowLog", "copyFile ", e2222222222);
                if (bufferedInputStream2 != null) {
                    bufferedInputStream2.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Throwable th5) {
                e2222222222 = th5;
                fileOutputStream = null;
                bufferedInputStream = null;
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream2 != null) {
                    bufferedOutputStream2.flush();
                    bufferedOutputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                throw e2222222222;
            }
        } catch (IOException e8) {
            e2222222222 = e8;
            bufferedOutputStream = null;
            bufferedInputStream2 = null;
            fileInputStream = null;
            e.d("PushSelfShowLog", "copyFile ", e2222222222);
            if (bufferedInputStream2 != null) {
                bufferedInputStream2.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (Throwable th6) {
            e2222222222 = th6;
            fileOutputStream = null;
            bufferedInputStream = null;
            fileInputStream2 = null;
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (bufferedOutputStream2 != null) {
                bufferedOutputStream2.flush();
                bufferedOutputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw e2222222222;
        }
    }

    public static boolean a(String str, String str2) {
        try {
            boolean mkdirs = new File(str2).mkdirs();
            e.a("PushSelfShowLog", "urlSrc is %s ,urlDest is %s,urlDest is already exist?%s ", str, str2, Boolean.valueOf(mkdirs));
            File[] listFiles = new File(str).listFiles();
            if (listFiles == null) {
                return true;
            }
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    a(listFiles[i], new File(str2 + File.separator + listFiles[i].getName()));
                }
                if (listFiles[i].isDirectory()) {
                    b(str + File.separator + listFiles[i].getName(), str2 + File.separator + listFiles[i].getName());
                }
            }
            return true;
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "fileCopy error ", e);
            return false;
        }
    }

    public static long b(String str) {
        if (str == null) {
            str = "";
        }
        try {
            Date date = new Date();
            int hours = (date.getHours() * 2) + (date.getMinutes() / 30);
            String concat = str.concat(str);
            e.a("PushSelfShowLog", "startIndex is %s ,and ap is %s ,length is %s", Integer.valueOf(hours), concat, Integer.valueOf(concat.length()));
            int i = hours;
            while (i < concat.length()) {
                if (concat.charAt(i) != '0') {
                    long minutes = ((long) (((i - hours) * 30) - (date.getMinutes() % 30))) * 60000;
                    e.a("PushSelfShowLog", "startIndex is %s i is %s delay %s", Integer.valueOf(hours), Integer.valueOf(i), Long.valueOf(minutes));
                    return minutes >= 0 ? minutes : 0;
                } else {
                    i++;
                }
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "error ", e);
        }
        return 0;
    }

    public static String b(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe);
        return telephonyManager != null ? telephonyManager.getDeviceId() : "";
    }

    private static String b(ResolveInfo resolveInfo, String str) {
        Bundle bundle = resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.metaData : resolveInfo.activityInfo.metaData;
        return bundle == null ? null : bundle.getString(str);
    }

    public static void b(Context context, String str, String str2) {
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        Throwable e;
        InputStream inputStream2;
        FileOutputStream fileOutputStream2 = null;
        try {
            if (new File(str2).exists()) {
                fileOutputStream = null;
                inputStream = null;
            } else {
                inputStream = context.getAssets().open(str);
                try {
                    fileOutputStream = new FileOutputStream(str2);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                    } catch (IOException e2) {
                        e = e2;
                        inputStream2 = inputStream;
                        try {
                            e.d("PushSelfShowLog", "copyAsset ", e);
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable e3) {
                                    e.d("PushSelfShowLog", "fos.close() ", e3);
                                }
                            }
                            if (inputStream2 == null) {
                                try {
                                    inputStream2.close();
                                } catch (Throwable e32) {
                                    e.d("PushSelfShowLog", "is.close() ", e32);
                                    return;
                                }
                            }
                        } catch (Throwable th) {
                            e32 = th;
                            inputStream = inputStream2;
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (Throwable e4) {
                                    e.d("PushSelfShowLog", "fos.close() ", e4);
                                }
                            }
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Throwable e42) {
                                    e.d("PushSelfShowLog", "is.close() ", e42);
                                }
                            }
                            throw e32;
                        }
                    } catch (Throwable th2) {
                        e32 = th2;
                        fileOutputStream2 = fileOutputStream;
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        throw e32;
                    }
                } catch (IOException e5) {
                    e32 = e5;
                    fileOutputStream = null;
                    inputStream2 = inputStream;
                    e.d("PushSelfShowLog", "copyAsset ", e32);
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (inputStream2 == null) {
                        inputStream2.close();
                    }
                } catch (Throwable th3) {
                    e32 = th3;
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    throw e32;
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Throwable e322) {
                    e.d("PushSelfShowLog", "fos.close() ", e322);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e3222) {
                    e.d("PushSelfShowLog", "is.close() ", e3222);
                }
            }
        } catch (IOException e6) {
            e3222 = e6;
            fileOutputStream = null;
            e.d("PushSelfShowLog", "copyAsset ", e3222);
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (inputStream2 == null) {
                inputStream2.close();
            }
        } catch (Throwable th4) {
            e3222 = th4;
            inputStream = null;
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw e3222;
        }
    }

    public static void b(File file) {
        e.a("PushSelfShowLog", "delete file before ");
        if (file != null && file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                long currentTimeMillis = System.currentTimeMillis();
                for (File file2 : listFiles) {
                    try {
                        if (currentTimeMillis - file2.lastModified() > 86400000) {
                            e.e("PushSelfShowLog", "delete file before " + file2.getAbsolutePath());
                            a(file2);
                        }
                    } catch (Exception e) {
                        e.e("PushSelfShowLog", e.toString());
                    }
                }
            }
        }
    }

    private static void b(String str, String str2) throws IOException {
        if (new File(str2).mkdirs()) {
            e.e("PushSelfShowLog", ClearCase.COMMAND_MKDIR);
        }
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    a(listFiles[i], new File(new File(str2).getAbsolutePath() + File.separator + listFiles[i].getName()));
                }
                if (listFiles[i].isDirectory()) {
                    b(str + "/" + listFiles[i].getName(), str2 + "/" + listFiles[i].getName());
                }
            }
        }
    }

    public static boolean b() {
        return VERSION.SDK_INT >= 11;
    }

    public static boolean b(Context context, String str) {
        if (context == null || str == null || "".equals(str)) {
            return false;
        }
        try {
            if (context.getPackageManager().getApplicationInfo(str, 8192) == null) {
                return false;
            }
            e.a("PushSelfShowLog", str + " is installed");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String c(Context context, String str) {
        String str2;
        Throwable e;
        String str3 = "";
        try {
            str2 = "";
            str2 = ((!Environment.getExternalStorageState().equals("mounted") ? context.getFilesDir().getPath() : Environment.getExternalStorageDirectory().getPath()) + File.separator + "PushService") + File.separator + str;
            try {
                e.a("PushSelfShowLog", "dbPath is " + str2);
            } catch (Exception e2) {
                e = e2;
                e.d("PushSelfShowLog", "getDbPath error", e);
                return str2;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            str2 = str3;
            e = th;
            e.d("PushSelfShowLog", "getDbPath error", e);
            return str2;
        }
        return str2;
    }

    public static ArrayList c(Context context) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id="));
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (!(queryIntentActivities == null || queryIntentActivities.size() == 0)) {
            int size = queryIntentActivities.size();
            for (int i = 0; i < size; i++) {
                if (((ResolveInfo) queryIntentActivities.get(i)).activityInfo != null) {
                    arrayList.add(((ResolveInfo) queryIntentActivities.get(i)).activityInfo.applicationInfo.packageName);
                }
            }
        }
        return arrayList;
    }

    public static boolean c() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean d() {
        return b.a() >= 9;
    }

    public static boolean d(Context context) {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setPackage("com.android.email");
        intent.setData(Uri.fromParts("mailto", "xxxx@xxxx.com", null));
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        return (queryIntentActivities == null || queryIntentActivities.size() == 0) ? false : true;
    }

    private static boolean e() {
        return "zh".equals(Locale.getDefault().getLanguage());
    }

    public static boolean e(Context context) {
        return "com.huawei.android.pushagent".equals(context.getPackageName());
    }

    public static boolean f(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo("com.huawei.android.pushagent", 128) != null;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean g(Context context) {
        Cursor query;
        Throwable e;
        try {
            query = context.getContentResolver().query(com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a.a, null, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        int i = query.getInt(query.getColumnIndex("isSupport"));
                        e.a("PushSelfShowLog", "isExistProvider:" + i);
                        boolean z = 1 == i;
                        if (query == null) {
                            return z;
                        }
                        query.close();
                        return z;
                    }
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.a("PushSelfShowLog", e.toString(), e);
                        if (query != null) {
                            query.close();
                        }
                        return false;
                    } catch (Throwable th) {
                        e = th;
                        if (query != null) {
                            query.close();
                        }
                        throw e;
                    }
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e3) {
            e = e3;
            query = null;
            e.a("PushSelfShowLog", e.toString(), e);
            if (query != null) {
                query.close();
            }
            return false;
        } catch (Throwable th2) {
            e = th2;
            query = null;
            if (query != null) {
                query.close();
            }
            throw e;
        }
        return false;
    }

    public static int h(Context context) {
        if (context == null) {
            return 3;
        }
        return (VERSION.SDK_INT < 16 || context.getResources().getIdentifier("androidhwext:style/Theme.Emui", null, null) == 0) ? 3 : 0;
    }

    public static int i(Context context) {
        try {
            Class cls = Class.forName("com.huawei.android.immersion.ImmersionStyle");
            int intValue = ((Integer) cls.getDeclaredMethod("getPrimaryColor", new Class[]{Context.class}).invoke(cls, new Object[]{context})).intValue();
            e.b("PushSelfShowLog", "colorPrimary:" + intValue);
            return intValue;
        } catch (ClassNotFoundException e) {
            e.d("PushSelfShowLog", "ImmersionStyle ClassNotFoundException");
        } catch (Throwable e2) {
            e.c("PushSelfShowLog", e2.toString(), e2);
        } catch (Throwable e22) {
            e.c("PushSelfShowLog", e22.toString(), e22);
        } catch (Throwable e222) {
            e.c("PushSelfShowLog", e222.toString(), e222);
        } catch (Throwable e2222) {
            e.c("PushSelfShowLog", e2222.toString(), e2222);
        } catch (Throwable e22222) {
            e.c("PushSelfShowLog", e22222.toString(), e22222);
        }
        return 0;
    }

    public static int j(Context context) {
        int intValue;
        Throwable e;
        Throwable th;
        try {
            Class cls = Class.forName("com.huawei.android.immersion.ImmersionStyle");
            intValue = ((Integer) cls.getDeclaredMethod("getPrimaryColor", new Class[]{Context.class}).invoke(cls, new Object[]{context})).intValue();
            intValue = ((Integer) cls.getDeclaredMethod("getSuggestionForgroundColorStyle", new Class[]{Integer.TYPE}).invoke(cls, new Object[]{Integer.valueOf(intValue)})).intValue();
            try {
                e.b("PushSelfShowLog", "getSuggestionForgroundColorStyle:" + intValue);
            } catch (ClassNotFoundException e2) {
                e.d("PushSelfShowLog", "ImmersionStyle ClassNotFoundException");
                return intValue;
            } catch (SecurityException e3) {
                e = e3;
                e.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            } catch (NoSuchMethodException e4) {
                e = e4;
                e.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            } catch (IllegalArgumentException e5) {
                e = e5;
                e.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            } catch (IllegalAccessException e6) {
                e = e6;
                e.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            } catch (InvocationTargetException e7) {
                e = e7;
                e.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            }
        } catch (ClassNotFoundException e8) {
            intValue = -1;
            e.d("PushSelfShowLog", "ImmersionStyle ClassNotFoundException");
            return intValue;
        } catch (Throwable e9) {
            th = e9;
            intValue = -1;
            e = th;
            e.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        } catch (Throwable e92) {
            th = e92;
            intValue = -1;
            e = th;
            e.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        } catch (Throwable e922) {
            th = e922;
            intValue = -1;
            e = th;
            e.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        } catch (Throwable e9222) {
            th = e9222;
            intValue = -1;
            e = th;
            e.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        } catch (Throwable e92222) {
            th = e92222;
            intValue = -1;
            e = th;
            e.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        }
        return intValue;
    }

    private static boolean l(Context context) {
        boolean z = true;
        int i = -1;
        if (context == null) {
            return false;
        }
        try {
            i = Secure.getInt(context.getContentResolver(), "user_experience_involved", -1);
            e.a("PushSelfShowLog", "settingMainSwitch:" + i);
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
        if (i != 1) {
            z = false;
        }
        return z;
    }
}
