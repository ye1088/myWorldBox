package com.MCWorld.framework.base.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import com.MCWorld.data.profile.a;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.http.toolbox.entity.utils.TextUtils;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.SharedPref;
import java.util.UUID;

public class UtilsAndroid {

    public static class Pref extends SharedPref {

        private static class Singleton {
            public static Pref instance = new Pref(AppConfig.getInstance().getAppContext().getSharedPreferences("utils-android-pref", 0));

            private Singleton() {
            }
        }

        public static SharedPref getInstance() {
            return Singleton.instance;
        }

        private Pref(SharedPreferences pref) {
            super(pref);
        }
    }

    public static String fetchDeviceId() {
        String raddomId;
        String duuid = Pref.getInstance().getString("device-uuid");
        if (TextUtils.isEmpty(duuid)) {
            StringBuilder deviceId = new StringBuilder();
            Context context = AppConfig.getInstance().getAppContext();
            if (context == null) {
                raddomId = UUID.randomUUID().toString();
                deviceId.append("[d]");
                deviceId.append(raddomId);
                duuid = deviceId.toString();
                try {
                    Pref.getInstance().putString("device-uuid", duuid);
                } catch (Exception e1) {
                    HLog.error("UtilsAndroid", "context null " + e1.getMessage(), new Object[0]);
                }
                HLog.verbose("UtilsAndroid", "fetchDeviceId setid " + duuid, new Object[0]);
            } else {
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
                        }
                    }
                    if (isWifi || isImei || isSn) {
                        duuid = deviceId.toString();
                        try {
                            Pref.getInstance().putString("device-uuid", duuid);
                        } catch (Exception e) {
                            HLog.error("UtilsAndroid", "caught " + e.getMessage(), new Object[0]);
                        }
                        HLog.verbose("UtilsAndroid", "fetchDeviceId generate succ " + duuid, new Object[0]);
                    } else {
                        raddomId = UUID.randomUUID().toString();
                        deviceId.append("[d]");
                        deviceId.append(raddomId);
                        duuid = deviceId.toString();
                        try {
                            Pref.getInstance().putString("device-uuid", duuid);
                        } catch (Exception e12) {
                            HLog.error("UtilsAndroid", "caught " + e12.getMessage(), new Object[0]);
                        }
                        HLog.verbose("UtilsAndroid", "fetchDeviceId ramdom " + duuid, new Object[0]);
                    }
                } catch (Exception e2) {
                    HLog.error("UtilsAndroid", "fetchDeviceId Error Get DeviceID " + e2.getMessage(), new Object[0]);
                    raddomId = UUID.randomUUID().toString();
                    deviceId.append("[d]");
                    deviceId.append(raddomId);
                    duuid = deviceId.toString();
                    try {
                        Pref.getInstance().putString("device-uuid", duuid);
                    } catch (Exception e122) {
                        HLog.error("UtilsAndroid", "caught " + e122.getMessage(), new Object[0]);
                    }
                    HLog.verbose("UtilsAndroid", "fetchDeviceId setid " + duuid, new Object[0]);
                }
            }
        }
        return duuid;
    }

    public static int fetchVersionCode() {
        int i = 0;
        Context context = AppConfig.getInstance().getAppContext();
        if (context != null) {
            try {
                i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    public static String fetchVersionName() {
        Context context = AppConfig.getInstance().getAppContext();
        String appVersion = "0.0.0";
        if (context == null) {
            return appVersion;
        }
        try {
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }
}
