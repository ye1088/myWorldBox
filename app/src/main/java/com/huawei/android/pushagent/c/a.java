package com.huawei.android.pushagent.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.huawei.android.microkernel.MKService;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.f;
import com.MCWorld.module.h;
import com.MCWorld.video.recorder.b;
import java.io.File;
import java.io.FileDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.tools.ant.taskdefs.optional.ejb.EjbJar.CMPVersion;

public class a {
    private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static int b = -1;

    private static boolean A(Context context) {
        String z = z(context);
        e.a("PushLogAC2705", "signStr hashCode is :" + z.hashCode());
        return -735404696 == z.hashCode();
    }

    public static int a(int i, int i2) {
        e.a("PushLogAC2705", "enter ctrlSockets(cmd:" + i + " param:" + i2 + ")");
        try {
            return ((Integer) Class.forName("dalvik.system.Zygote").getMethod("ctrlSockets", new Class[]{Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)})).intValue();
        } catch (ClassNotFoundException e) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return -2;
        } catch (NoSuchMethodException e2) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return -2;
        } catch (IllegalArgumentException e3) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return -2;
        } catch (IllegalAccessException e4) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return -2;
        } catch (InvocationTargetException e5) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return -2;
        }
    }

    public static int a(Socket socket) throws ClassNotFoundException {
        try {
            return ((Integer) FileDescriptor.class.getMethod("getInt$", new Class[0]).invoke((FileDescriptor) Socket.class.getMethod("getFileDescriptor$", new Class[0]).invoke(socket, new Object[0]), new Object[0])).intValue();
        } catch (NoSuchMethodException e) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return 0;
        } catch (IllegalArgumentException e2) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return 0;
        } catch (IllegalAccessException e3) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return 0;
        } catch (InvocationTargetException e4) {
            e.a("PushLogAC2705", "There is no method of ctrlSockets.");
            return 0;
        }
    }

    public static long a(String str) {
        long j = -1;
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").parse(str);
            if (parse != null) {
                j = parse.getTime();
            }
        } catch (ParseException e) {
            e.d("PushLogAC2705", "ParseException,timeStr:" + str + " e:" + e.toString());
        }
        return j;
    }

    public static ResolveInfo a(Context context, Intent intent) {
        List queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, h.arA);
        return (queryBroadcastReceivers == null || queryBroadcastReceivers.size() <= 0) ? null : (ResolveInfo) queryBroadcastReceivers.get(0);
    }

    public static String a() {
        String replace = UUID.randomUUID().toString().replace("-", "");
        return replace.length() > 15 ? "_" + replace.substring(0, 15) : "_" + "000000000000000".substring(15 - replace.length()) + replace;
    }

    public static String a(byte b) {
        return new String(new char[]{a[(b & b.bpd) >> 4], a[b & 15]});
    }

    public static String a(long j, String str) {
        String str2 = "";
        try {
            str2 = new SimpleDateFormat(str).format(new Date(j));
        } catch (Exception e) {
            e.d("PushLogAC2705", "getTimeString,milliseconds:" + j + " e:" + e.toString());
        }
        return str2;
    }

    public static String a(Context context) {
        Object d = d(context);
        if (!TextUtils.isEmpty(d)) {
            return d;
        }
        String b = b(context);
        f.a(context, "device_info", "deviceId", b);
        return b;
    }

    public static String a(Context context, String str, String str2) {
        String str3 = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, h.arA);
            if (applicationInfo == null || applicationInfo.metaData == null) {
                e.a("PushLogAC2705", "could not read Applicationinfo from AndroidManifest.xml.");
                return str3;
            }
            Object obj = applicationInfo.metaData.get(str2);
            if (obj == null) {
                e.b("PushLogAC2705", "could not read meta-data from AndroidManifest.xml.");
            } else {
                str3 = String.valueOf(obj);
                e.a("PushLogAC2705", "read meta-data from AndroidManifest.xml,name is:" + str2 + ",value is:" + str3);
            }
            return str3;
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return "";
        }
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            cArr[i * 2] = a[(b & b.bpd) >> 4];
            cArr[(i * 2) + 1] = a[b & 15];
        }
        return new String(cArr);
    }

    public static ArrayList a(Context context, String str) {
        List queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(new Intent(str), 0);
        int size = queryBroadcastReceivers == null ? 0 : queryBroadcastReceivers.size();
        ArrayList arrayList = new ArrayList();
        if (size <= 0) {
            return arrayList;
        }
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryBroadcastReceivers.get(i);
            arrayList.add((resolveInfo.activityInfo != null ? resolveInfo.activityInfo : resolveInfo.serviceInfo).packageName);
        }
        return arrayList;
    }

    public static void a(Context context, int i) {
        switch (i) {
            case 1:
                x(context);
                l(context);
                c.a(context).a();
                c.a(context).b();
                com.huawei.android.pushagent.b.b.a.b(context);
                return;
            case 2:
                k(context);
                x(context);
                y(context);
                c.a(context).a();
                c.a(context).b();
                com.huawei.android.pushagent.b.b.a.b(context);
                return;
            case 3:
                x(context);
                y(context);
                c.a(context).a();
                c.a(context).b();
                return;
            default:
                return;
        }
    }

    public static void a(Context context, long j) {
        e.a("PushLogAC2705", "enter wakeSystem");
        ((PowerManager) context.getSystemService("power")).newWakeLock(1, "pushagent").acquire(j);
    }

    public static void a(Context context, boolean z) {
        if (context != null) {
            if (MKService.getAppContext() == null) {
                a(context, "com.huawei.android.pushagent.PushBootReceiver", z);
            } else {
                a(context, "com.huawei.deviceCloud.microKernel.push.PushBootReceiver", z);
            }
        }
    }

    public static boolean a(Context context, String str, boolean z) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        e.a("PushLogAC2705", "setReceiverState:" + context.getPackageName() + ",isEnable:" + z);
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, str), !z ? 2 : 1, 1);
            return true;
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
            return false;
        }
    }

    public static byte[] a(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static byte[] a(long j) {
        byte[] bArr = new byte[8];
        for (int length = bArr.length - 1; length >= 0; length--) {
            bArr[length] = (byte) ((int) j);
            j >>= 8;
        }
        return bArr;
    }

    public static int b(byte[] bArr) {
        return ((((bArr[0] << 24) & 255) | ((bArr[1] << 16) & 255)) | ((bArr[2] << 8) & 255)) | (bArr[3] & 255);
    }

    public static String b(Context context) {
        Object c = c(context);
        return TextUtils.isEmpty(c) ? a() : c;
    }

    public static List b(Context context, String str) {
        return context.getPackageManager().queryBroadcastReceivers(new Intent(str), h.arA);
    }

    public static void b() {
        e.a("PushLogAC2705", "enter powerLow");
        try {
            Class.forName("android.util.LogPower").getMethod("push", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(119)});
        } catch (ClassNotFoundException e) {
            e.a("PushLogAC2705", "need not support LogPower");
        } catch (NoSuchMethodException e2) {
            e.a("PushLogAC2705", "need not support LogPower");
        } catch (IllegalArgumentException e3) {
            e.a("PushLogAC2705", "need not support LogPower");
        } catch (IllegalAccessException e4) {
            e.a("PushLogAC2705", "need not support LogPower");
        } catch (InvocationTargetException e5) {
            e.a("PushLogAC2705", "need not support LogPower");
        }
    }

    private static boolean b(Context context, boolean z) {
        boolean z2 = true;
        e.a("PushLogAC2705", "existFrameworkPush:" + b + ",realCheck:" + z);
        if (z) {
            try {
                if (new File("/system/framework/" + "hwpush.jar").isFile()) {
                    e.a("PushLogAC2705", "push jarFile is exist");
                } else {
                    e.b("PushLogAC2705", "push jarFile is not exist");
                    if (SystemProperties.getBoolean("ro.config.push_enable", "CN".equals(SystemProperties.get("ro.product.locale.region")))) {
                        String str = SystemProperties.get("ro.build.version.emui", "-1");
                        if (TextUtils.isEmpty(str) || !(str.contains(CMPVersion.CMP2_0) || str.contains("2.3"))) {
                            e.a("PushLogAC2705", "can not use framework push");
                            return false;
                        }
                        e.a("PushLogAC2705", "emui is 2.0 or 2.3");
                    } else {
                        e.a("PushLogAC2705", "framework not support push");
                        return false;
                    }
                }
                List queryIntentServices = context.getPackageManager().queryIntentServices(new Intent().setClassName("android", "com.huawei.android.pushagentproxy.PushService"), 128);
                if (queryIntentServices == null || queryIntentServices.size() == 0) {
                    e.b("PushLogAC2705", "framework push not exist, need vote apk or sdk to support pushservice");
                    return false;
                }
                e.b("PushLogAC2705", "framework push exist, use framework push first");
                return true;
            } catch (Exception e) {
                e.d("PushLogAC2705", "get Apk version faild ,Exception e= " + e.toString());
                return false;
            }
        }
        if (1 != b) {
            z2 = false;
        }
        return z2;
    }

    public static byte[] b(int i) {
        return new byte[]{(byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static int c(byte[] bArr) {
        return ((bArr[0] & 255) << 8) | (bArr[1] & 255);
    }

    public static String c() {
        String str = "";
        Class[] clsArr = new Class[]{String.class};
        Object[] objArr = new Object[]{"ro.build.version.emui"};
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            String str2 = (String) cls.getDeclaredMethod("get", clsArr).invoke(cls, objArr);
            e.a("PushLogAC2705", "get EMUI version is:" + str2);
            if (!TextUtils.isEmpty(str2)) {
                return str2;
            }
        } catch (ClassNotFoundException e) {
            e.d("PushLogAC2705", " getEmuiVersion wrong, ClassNotFoundException");
        } catch (LinkageError e2) {
            e.d("PushLogAC2705", " getEmuiVersion wrong, LinkageError");
        } catch (NoSuchMethodException e3) {
            e.d("PushLogAC2705", " getEmuiVersion wrong, NoSuchMethodException");
        } catch (NullPointerException e4) {
            e.d("PushLogAC2705", " getEmuiVersion wrong, NullPointerException");
        } catch (IllegalAccessException e5) {
            e.d("PushLogAC2705", " getEmuiVersion wrong, IllegalAccessException");
        } catch (IllegalArgumentException e6) {
            e.d("PushLogAC2705", " getEmuiVersion wrong, IllegalArgumentException");
        } catch (InvocationTargetException e7) {
            e.d("PushLogAC2705", " getEmuiVersion wrong, InvocationTargetException");
        }
        return str;
    }

    public static String c(Context context) {
        String a;
        String str = "";
        boolean ae = com.huawei.android.pushagent.b.b.a.a(context).ae();
        e.a("PushLogAC2705", "isMultiSimEnabledFromServer:" + ae);
        if (com.huawei.android.pushagent.c.b.b.b() && ae) {
            e.a("PushLogAC2705", "multicard device");
            a = com.huawei.android.pushagent.c.b.b.a().a(0);
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe);
            a = telephonyManager != null ? telephonyManager.getDeviceId() : str;
        }
        if (TextUtils.isEmpty(a) || a.matches("[0]+")) {
            e.b("PushLogAC2705", "get uniqueId from device is empty or all 0");
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (a.length() >= 16) {
            return a.substring(a.length() - 16);
        }
        stringBuffer.append("0").append(a);
        if (stringBuffer.length() < 16) {
            StringBuffer stringBuffer2 = new StringBuffer();
            for (int i = 0; i < 16 - stringBuffer.length(); i++) {
                stringBuffer2.append("0");
            }
            stringBuffer.append(stringBuffer2);
        }
        return stringBuffer.toString();
    }

    public static boolean c(Context context, String str) {
        boolean z = false;
        if (!TextUtils.isEmpty(str)) {
            if (a(context, new Intent("com.huawei.android.push.intent.REGISTRATION").setPackage(str)) != null) {
                z = true;
            }
            e.a("PushLogAC2705", "isPushClient:" + z);
        }
        return z;
    }

    public static long d(byte[] bArr) {
        return (((((((0 | ((((long) bArr[0]) & 255) << 56)) | ((((long) bArr[1]) & 255) << 48)) | ((((long) bArr[2]) & 255) << 40)) | ((((long) bArr[3]) & 255) << 32)) | ((((long) bArr[4]) & 255) << 24)) | ((((long) bArr[5]) & 255) << 16)) | ((((long) bArr[6]) & 255) << 8)) | (((long) bArr[7]) & 255);
    }

    public static String d(Context context) {
        e.a("PushLogAC2705", "enter getImeiFromLocalFile()");
        String a = f.a(context, "device_info", "deviceId");
        if (TextUtils.isEmpty(a) || 16 != a.length()) {
            e.c("PushLogAC2705", "no deviceId in device_info");
        } else {
            e.a("PushLogAC2705", "get imei from localFile success");
        }
        return a;
    }

    public static boolean d(Context context, String str) {
        boolean z = false;
        if (!TextUtils.isEmpty(str)) {
            if (a(context, new Intent("com.huawei.android.push.intent.REGISTER").setPackage(str)) != null) {
                z = true;
            }
            e.a("PushLogAC2705", "hasPushRegister:" + z);
        }
        return z;
    }

    public static boolean e(Context context) {
        String d = d(context);
        e.a("PushLogAC2705", "imei from localfile is " + com.huawei.android.pushagent.c.a.a.e.a(d));
        String c = c(context);
        e.a("PushLogAC2705", "deviceId from device is " + com.huawei.android.pushagent.c.a.a.e.a(c));
        String m = m(context);
        String n = n(context);
        e.a("PushLogAC2705", "mac from localfile is " + com.huawei.android.pushagent.c.a.a.e.a(m));
        e.a("PushLogAC2705", "mac from device is " + com.huawei.android.pushagent.c.a.a.e.a(n));
        if (TextUtils.isEmpty(d) || TextUtils.isEmpty(c)) {
            if (TextUtils.isEmpty(d)) {
                f.a(context, "device_info", "deviceId", b(context));
            }
            if (TextUtils.isEmpty(m) || TextUtils.isEmpty(n)) {
                if (!TextUtils.isEmpty(m) || TextUtils.isEmpty(n)) {
                    return false;
                }
                f.a(context, "device_info", "macAddress", n);
                return false;
            } else if (m.equals(n)) {
                return false;
            } else {
                e.c("PushLogAC2705", "After check mac, it is cloned, need reset files");
                return true;
            }
        } else if (d.equals(c)) {
            return false;
        } else {
            e.c("PushLogAC2705", "After check imei, it is cloned, need reset files");
            return true;
        }
    }

    public static boolean e(Context context, String str) {
        String str2 = context.getCacheDir().getParent() + "/shared_prefs/" + str + ".xml";
        File file = new File(str2);
        if (file.exists() && file.isFile() && file.canWrite()) {
            e.a("PushLogAC2705", "enter deletePrefrence(fileName:" + str + ".xml)");
            return file.delete();
        }
        if (file.exists()) {
            e.d("PushLogAC2705", "delete File:" + str2 + " failed!!");
        }
        return false;
    }

    public static String f(Context context, String str) {
        String a = a(context, str, "CHANNEL");
        if (a == null) {
            return str;
        }
        str = str + "#" + a;
        e.a("PushLogAC2705", "after add downloadChannel, the new packageName is:" + str);
        return str;
    }

    public static void f(Context context) {
        if (e(context)) {
            new Thread(new b(context)).start();
        }
    }

    public static String g(Context context) {
        return String.valueOf(2705);
    }

    public static boolean g(Context context, String str) {
        if (context == null || str == null || "".equals(str)) {
            return false;
        }
        try {
            if (context.getPackageManager().getApplicationInfo(str, 0) == null) {
                return false;
            }
            e.a("PushLogAC2705", str + " is installed");
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static String h(Context context) {
        String simOperator = ((TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe)).getSimOperator();
        if (simOperator == null) {
            return "";
        }
        char[] toCharArray = simOperator.toCharArray();
        int i = 0;
        while (i < toCharArray.length) {
            if (toCharArray[i] < '0' || toCharArray[i] > '9') {
                return simOperator.substring(0, i);
            }
            i++;
        }
        return simOperator;
    }

    public static String i(Context context) {
        return a(context) + "0000000000000000";
    }

    public static String j(Context context) {
        if (context == null) {
            return "";
        }
        String extraInfo;
        String str = "";
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                extraInfo = activeNetworkInfo.getExtraInfo();
                if (extraInfo == null) {
                    extraInfo = "";
                }
                e.a("PushLogAC2705", "push apn is " + extraInfo);
                return extraInfo;
            }
        }
        extraInfo = str;
        if (extraInfo == null) {
            extraInfo = "";
        }
        e.a("PushLogAC2705", "push apn is " + extraInfo);
        return extraInfo;
    }

    public static void k(Context context) {
        try {
            com.huawei.android.pushagent.b.b.a.a(context).c.clear();
            com.huawei.android.pushagent.b.e.b.a(context).a.clear();
            com.huawei.android.pushagent.b.d.a.b(context).a();
        } catch (Exception e) {
            e.d("PushLogAC2705", "clearMemory failed!");
        }
    }

    public static void l(Context context) {
        com.huawei.android.pushagent.c.a.h hVar = new com.huawei.android.pushagent.c.a.h(context, "pclient_info_v2");
        for (String str : hVar.b().keySet()) {
            if (!g(context, str)) {
                hVar.f(str);
            }
        }
    }

    public static String m(Context context) {
        e.a("PushLogAC2705", "enter getMacFromLocalFile()");
        String str = "";
        Object a = f.a(context, "device_info", "macAddress");
        if (TextUtils.isEmpty(a)) {
            e.c("PushLogAC2705", "no macAddress in device_info");
        } else {
            e.a("PushLogAC2705", "get macAddress from LocalFile success");
        }
        return a;
    }

    public static String n(Context context) {
        Throwable e;
        e.a("PushLogAC2705", "enter getMacAddress()");
        String str = "";
        String macAddress;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            WifiInfo connectionInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();
            if (connectionInfo == null) {
                e.c("PushLogAC2705", "info = null");
                return str;
            }
            macAddress = connectionInfo.getMacAddress();
            try {
                if (TextUtils.isEmpty(macAddress)) {
                    e.c("PushLogAC2705", "Mac is empty");
                    return macAddress;
                }
                e.a("PushLogAC2705", "get Mac from device success");
                return macAddress;
            } catch (Exception e2) {
                e = e2;
                e.c("PushLogAC2705", "getLocalMacAddress() exception,e=" + e.toString(), e);
                return macAddress;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            macAddress = str;
            e = th;
            e.c("PushLogAC2705", "getLocalMacAddress() exception,e=" + e.toString(), e);
            return macAddress;
        }
    }

    public static synchronized boolean o(Context context) {
        boolean z = true;
        synchronized (a.class) {
            e.a("PushLogAC2705", "existFrameworkPush:" + b);
            if (-1 == b) {
                if (b(context, true)) {
                    b = 1;
                } else {
                    b = 0;
                }
                if (1 != b) {
                    z = false;
                }
            } else if (1 != b) {
                z = false;
            }
        }
        return z;
    }

    public static void p(Context context) {
        try {
            if ("com.huawei.android.pushagent".equals(context.getPackageName()) && !A(context)) {
                e.d("PushLogAC2705", "signature of apk HwPushService is not test");
                Toast.makeText(context, "Signature of HwPushService is not correct", 1).show();
            }
        } catch (Throwable e) {
            e.a("PushLogAC2705", "checkAPKSignature error", e);
        }
    }

    public static int q(Context context) {
        int c = new com.huawei.android.pushagent.c.a.h(context, "pushConfig").c("NeedMyServiceRun");
        if (c < 0 || c > 2) {
            c = 0;
        }
        e.a("PushLogAC2705", "in file needMyServiceRun is : " + c);
        return c;
    }

    public static String r(Context context) {
        return new com.huawei.android.pushagent.c.a.h(context, "pushConfig").b("votedPackageName");
    }

    private static void v(Context context) {
        e.a("PushLogAC2705", "update deviceInfo File");
        new com.huawei.android.pushagent.c.a.h(context, "device_info").c();
        String b = b(context);
        String n = n(context);
        f.a(context, "device_info", "deviceId", b);
        f.a(context, "device_info", "macAddress", n);
    }

    private static void w(Context context) {
        com.huawei.android.pushagent.c.a.h hVar = new com.huawei.android.pushagent.c.a.h(context, "pclient_request_info");
        com.huawei.android.pushagent.c.a.h hVar2 = new com.huawei.android.pushagent.c.a.h(context, "pclient_info_v2");
        for (String str : hVar2.b().keySet()) {
            if (!TextUtils.isEmpty(str)) {
                hVar.a(str, "true");
                e.a("PushLogAC2705", "pkg : " + str + " need register again");
            }
        }
        hVar2.c();
    }

    private static void x(Context context) {
        e(context, "PushConnectControl");
        e(context, "PushRouteInfo");
        e(context, "RouteInfo");
        e(context, "HeartBeatCfg");
        for (String e : com.huawei.android.pushagent.b.a.a.a(context).a()) {
            e(context, e);
        }
        e(context, "socket_info");
        e(context, "update_remind");
    }

    private static void y(Context context) {
        e(context, "pclient_request_info");
        e(context, "pclient_unRegist_info_v2");
        e(context, "pclient_info_v2");
        e(context, "pclient_info");
    }

    private static String z(Context context) {
        if (context == null) {
            return "";
        }
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
            StringBuffer stringBuffer = new StringBuffer();
            if (signatureArr != null) {
                for (Signature toCharsString : signatureArr) {
                    stringBuffer.append(toCharsString.toCharsString());
                }
                e.a("PushLogAC2705", "Signature len is :" + signatureArr.length);
            }
            return stringBuffer.toString();
        } catch (NameNotFoundException e) {
            e.d("PushLogAC2705", "package with the given name can not be found");
            return "";
        }
    }
}
