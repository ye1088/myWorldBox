package com.tencent.stat.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.MCWorld.data.profile.a;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

public class StatCommonHelper {
    private static String appkey = null;
    private static String deviceModel = null;
    private static StatLogger logger = null;
    private static String macId = null;
    private static Random random = null;
    private static String userId = null;

    static class RootCmd {
        private static int systemRootState = -1;

        public static boolean isRootSystem() {
            if (systemRootState == 1) {
                return true;
            }
            if (systemRootState == 0) {
                return false;
            }
            String[] strArr = new String[]{"/bin", "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
            int i = 0;
            while (i < strArr.length) {
                try {
                    File file = new File(strArr[i] + "su");
                    if (file == null || !file.exists()) {
                        i++;
                    } else {
                        systemRootState = 1;
                        return true;
                    }
                } catch (Exception e) {
                }
            }
            systemRootState = 0;
            return false;
        }
    }

    public static boolean checkPermission(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    public static boolean checkPhoneState(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }

    public static Long convertStringToLong(String str, String str2, int i, int i2, Long l) {
        if (str == null || str2 == null) {
            return l;
        }
        if (str2.equalsIgnoreCase(".") || str2.equalsIgnoreCase("|")) {
            str2 = "\\" + str2;
        }
        String[] split = str.split(str2);
        if (split.length != i2) {
            return l;
        }
        try {
            Long valueOf = Long.valueOf(0);
            int i3 = 0;
            while (i3 < split.length) {
                Long valueOf2 = Long.valueOf(((long) i) * (valueOf.longValue() + Long.valueOf(split[i3]).longValue()));
                i3++;
                valueOf = valueOf2;
            }
            return valueOf;
        } catch (NumberFormatException e) {
            return l;
        }
    }

    public static String decode(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(RC4.decrypt(StatBase64.decode(str.getBytes("UTF-8"), 0)), "UTF-8");
        } catch (Object th) {
            logger.e(th);
            return str;
        }
    }

    public static byte[] deocdeGZipContent(byte[] bArr) throws IOException {
        GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(bArr));
        byte[] bArr2 = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length * 2);
        while (true) {
            int read = gZIPInputStream.read(bArr2);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr2, 0, read);
        }
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(StatBase64.encode(RC4.encrypt(str.getBytes("UTF-8")), 0), "UTF-8");
        } catch (Object th) {
            logger.e(th);
            return str;
        }
    }

    public static String getActivityName(Context context) {
        return context == null ? null : context.getClass().getName();
    }

    public static String getAppKey(Context context) {
        if (appkey != null) {
            return appkey;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("TA_APPKEY");
                if (string != null) {
                    appkey = string;
                    return string;
                }
                logger.w("Could not read APPKEY meta-data from AndroidManifest.xml");
            }
        } catch (Exception e) {
            logger.w("Could not read APPKEY meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String getAppVersion(Context context) {
        Exception e;
        String str = "";
        String str2;
        try {
            str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str2 != null) {
                try {
                    if (str2.length() != 0) {
                        return str2;
                    }
                } catch (Exception e2) {
                    e = e2;
                    logger.e(e);
                    return str2;
                }
            }
            return "unknown";
        } catch (Exception e3) {
            Exception exception = e3;
            str2 = str;
            e = exception;
            logger.e(e);
            return str2;
        }
    }

    public static String getCurAppVersion(Context context) {
        Exception e;
        String str = "";
        String str2;
        try {
            str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str2 != null) {
                return str2;
            }
            try {
                return "";
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            str2 = str;
            e = exception;
            logger.e(e);
            return str2;
        }
    }

    public static String getDateFormat(long j) {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(j));
    }

    public static String getDeviceID(Context context) {
        if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
            String str = "";
            if (checkPhoneState(context)) {
                str = ((TelephonyManager) context.getSystemService(a.qe)).getDeviceId();
            }
            if (str != null) {
                return str;
            }
            logger.error((Object) "deviceId is null");
            return null;
        }
        logger.e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
        return null;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static String getExternalStorageInfo(Context context) {
        try {
            if (!checkPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                logger.warn("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
                return null;
            } else if (!Environment.getExternalStorageState().equals("mounted")) {
                return null;
            } else {
                String path = Environment.getExternalStorageDirectory().getPath();
                if (path == null) {
                    return null;
                }
                StatFs statFs = new StatFs(path);
                long blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000;
                return String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + "/" + String.valueOf(blockCount);
            }
        } catch (Throwable th) {
            return null;
        }
    }

    public static HttpHost getHttpProxy(Context context) {
        if (context == null) {
            return null;
        }
        if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            return null;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getTypeName() != null && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                return null;
            }
            String extraInfo = activeNetworkInfo.getExtraInfo();
            if (extraInfo == null) {
                return null;
            }
            if (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap")) {
                return new HttpHost("10.0.0.172", 80);
            }
            if (extraInfo.equals("ctwap")) {
                return new HttpHost("10.0.0.200", 80);
            }
            return null;
        } catch (Exception e) {
            logger.e(e);
        }
    }

    public static String getInstallChannel(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Object obj = applicationInfo.metaData.get("InstallChannel");
                if (obj != null) {
                    return obj.toString();
                }
                logger.e((Object) "Could not read InstallChannel meta-data from AndroidManifest.xml");
            }
        } catch (Exception e) {
            logger.e((Object) "Could not read InstallChannel meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String getLinkedWay(Context context) {
        if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                String typeName = activeNetworkInfo.getTypeName();
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (typeName != null) {
                    return typeName.equalsIgnoreCase("WIFI") ? "WIFI" : typeName.equalsIgnoreCase("MOBILE") ? extraInfo == null ? "MOBILE" : extraInfo : extraInfo == null ? typeName : extraInfo;
                }
            }
        } else {
            logger.e((Object) "can not get the permission of android.permission.ACCESS_WIFI_STATE");
        }
        return null;
    }

    public static StatLogger getLogger() {
        if (logger == null) {
            logger = new StatLogger("MtaSDK");
            logger.setDebugEnable(false);
        }
        return logger;
    }

    public static String getMacId(Context context) {
        if (macId == null || "" == macId) {
            macId = getWifiMacAddress(context);
        }
        return macId;
    }

    public static int getNextSessionID() {
        return getRandom().nextInt(Integer.MAX_VALUE);
    }

    private static Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

    public static long getSDKLongVersion(String str) {
        return convertStringToLong(str, ".", 100, 3, Long.valueOf(0)).longValue();
    }

    public static String getSimOperator(Context context) {
        if (!checkPermission(context, "android.permission.READ_PHONE_STATE")) {
            logger.e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
            return null;
        } else if (!checkPhoneState(context)) {
            return null;
        } else {
            String simOperator;
            try {
                simOperator = ((TelephonyManager) context.getSystemService(a.qe)).getSimOperator();
            } catch (Exception e) {
                logger.e(e);
                simOperator = null;
            }
            return simOperator;
        }
    }

    public static Integer getTelephonyNetworkType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(a.qe);
        return telephonyManager != null ? Integer.valueOf(telephonyManager.getNetworkType()) : null;
    }

    public static long getTomorrowStartMilliseconds() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance.getTimeInMillis() + 86400000;
    }

    public static String getUserID(Context context) {
        if (userId != null && userId.trim().length() != 0) {
            return userId;
        }
        userId = getDeviceID(context);
        if (userId == null || userId.trim().length() == 0) {
            userId = Integer.toString(getRandom().nextInt(Integer.MAX_VALUE));
        }
        return userId;
    }

    public static String getWifiMacAddress(Context context) {
        if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                return wifiManager == null ? "" : wifiManager.getConnectionInfo().getMacAddress();
            } catch (Exception e) {
                logger.e(e);
                return "";
            }
        }
        logger.e((Object) "Could not get permission of android.permission.ACCESS_WIFI_STATE");
        return "";
    }

    public static int hasRootAccess(Context context) {
        return RootCmd.isRootSystem() ? 1 : 0;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (checkPermission(context, "android.permission.INTERNET")) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                return true;
            }
            logger.w("Network error");
            return false;
        }
        logger.warn("can not get the permisson of android.permission.INTERNET");
        return false;
    }

    public static boolean isWiFiActive(Context context) {
        if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo == null) {
                return false;
            }
            int i = 0;
            while (i < allNetworkInfo.length) {
                if (allNetworkInfo[i].getTypeName().equalsIgnoreCase("WIFI") && allNetworkInfo[i].isConnected()) {
                    return true;
                }
                i++;
            }
            return false;
        }
        logger.warn("can not get the permission of android.permission.ACCESS_WIFI_STATE");
        return false;
    }

    public static boolean isWifiNet(Context context) {
        if (checkPermission(context, "android.permission.INTERNET")) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI");
        } else {
            logger.warn("can not get the permisson of android.permission.INTERNET");
            return false;
        }
    }

    public static void jsonPut(JSONObject jSONObject, String str, String str2) throws JSONException {
        if (str2 != null && str2.length() > 0) {
            jSONObject.put(str, str2);
        }
    }

    public static String md5sum(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            logger.e(e);
            return "0";
        }
    }
}
