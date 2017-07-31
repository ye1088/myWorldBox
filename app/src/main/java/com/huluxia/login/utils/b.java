package com.huluxia.login.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.huluxia.data.profile.a;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsMD5;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* compiled from: UtilsAndroid */
public class b {
    private static String Ny;
    private static String versionName;

    public static String getDeviceId() {
        if (Ny == null) {
            Ny = qd();
        }
        String encodeID = "0";
        try {
            return URLEncoder.encode(Ny, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            HLog.error("UtilsAndroid", "UnsupportedEncodingException  " + e.getMessage(), new Object[0]);
            return "0";
        } catch (Exception e2) {
            HLog.error("UtilsAndroid", "Exception  " + e2.getMessage(), new Object[0]);
            return "0";
        }
    }

    public static String getVersionName() {
        if (versionName == null) {
            versionName = fetchVersionName();
        }
        return versionName;
    }

    public static String fetchDeviceId() {
        Context context = AppConfig.getInstance().getAppContext();
        StringBuilder deviceId = new StringBuilder();
        String duuid;
        try {
            String wifiMac = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (wifiMac != null) {
                wifiMac = wifiMac.trim();
                if (wifiMac.length() > 0) {
                    deviceId.append("wifi");
                    deviceId.append(wifiMac);
                    Log.d("[DeviceID Wifi]", deviceId.toString());
                    return UtilsMD5.MD5Code(deviceId.toString());
                }
            }
            TelephonyManager tm = (TelephonyManager) context.getSystemService(a.qe);
            String imei = tm.getDeviceId();
            if (imei != null) {
                imei = imei.trim();
                if (imei.length() > 0) {
                    deviceId.append("imei");
                    deviceId.append(imei);
                    Log.d("[DeviceID IMEI]", deviceId.toString());
                    return UtilsMD5.MD5Code(deviceId.toString());
                }
            }
            String sn = tm.getSimSerialNumber();
            if (sn != null) {
                sn = sn.trim();
                if (sn.length() > 0) {
                    deviceId.append("sn");
                    deviceId.append(sn);
                    Log.d("[DeviceID SN]", deviceId.toString());
                    return UtilsMD5.MD5Code(deviceId.toString());
                }
            }
            duuid = a.pW().qb();
            deviceId.append("duuid");
            deviceId.append(duuid);
            Log.d("[DeviceID UUID]", deviceId.toString());
            return UtilsMD5.MD5Code(deviceId.toString());
        } catch (Exception e) {
            Log.e("[Error Get DeviceID]", e.getMessage());
            duuid = a.pW().qb();
            deviceId.append("duuid");
            deviceId.append(duuid);
            Log.d("[Using DeviceID UUID]", deviceId.toString());
            return UtilsMD5.MD5Code(deviceId.toString());
        }
    }

    public static String qd() {
        String duuid;
        Context context = AppConfig.getInstance().getAppContext();
        StringBuilder deviceId = new StringBuilder();
        boolean isWifi = false;
        boolean isImei = false;
        boolean isSn = false;
        try {
            String wifiMac = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (wifiMac != null) {
                wifiMac = wifiMac.trim();
                if (wifiMac.length() > 0) {
                    isWifi = true;
                    deviceId.append("[w]");
                    deviceId.append(wifiMac);
                    Log.d("[DeviceID Wifi]", deviceId.toString());
                }
            }
            TelephonyManager tm = (TelephonyManager) context.getSystemService(a.qe);
            String imei = tm.getDeviceId();
            if (imei != null) {
                imei = imei.trim();
                if (imei.length() > 0) {
                    if (isWifi) {
                        deviceId.append("-");
                    }
                    isImei = true;
                    deviceId.append("[i]");
                    deviceId.append(imei);
                    Log.d("[DeviceID IMEI]", deviceId.toString());
                }
            }
            String sn = tm.getSimSerialNumber();
            if (sn != null) {
                sn = sn.trim();
                if (sn.length() > 0) {
                    if (isWifi || isImei) {
                        deviceId.append("-");
                    }
                    isSn = true;
                    deviceId.append("[s]");
                    deviceId.append(sn);
                    Log.d("[DeviceID SN]", deviceId.toString());
                }
            }
            if (isWifi || isImei || isSn) {
                return deviceId.toString();
            }
            duuid = a.pW().qc();
            deviceId.append("[d]");
            deviceId.append(duuid);
            Log.d("[DeviceID UUID]", deviceId.toString());
            return deviceId.toString();
        } catch (Exception e) {
            Log.e("[Error Get DeviceID]", e.getMessage());
            duuid = a.pW().qc();
            deviceId.append("[d]");
            deviceId.append(duuid);
            Log.d("[Using DeviceID UUID]", deviceId.toString());
            return deviceId.toString();
        }
    }

    @SuppressLint({"NewApi"})
    public static void bV(String szContent) {
        Context context = AppConfig.getInstance().getAppContext();
        if (VERSION.SDK_INT >= 11) {
            ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", szContent));
        } else {
            ((android.text.ClipboardManager) context.getSystemService("clipboard")).setText(szContent);
        }
    }

    public static String getVersion() {
        return VERSION.RELEASE;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String fetchVersionName() {
        String appVersion = "0.0.0";
        try {
            return AppConfig.getInstance().getAppContext().getPackageManager().getPackageInfo(AppConfig.getInstance().getAppContext().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return appVersion;
        }
    }

    public static String qe() {
        return ((TelephonyManager) AppConfig.getInstance().getAppContext().getSystemService(a.qe)).getDeviceId();
    }
}
