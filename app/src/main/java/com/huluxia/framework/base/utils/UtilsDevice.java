package com.huluxia.framework.base.utils;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.huluxia.data.profile.a;

public class UtilsDevice {
    public static final String UNKNOWN = "unknown";

    public static String getCarrierName(Context context) {
        if (context == null) {
            return "unknown";
        }
        String carrierName = "unknown";
        TelephonyManager localTelephonyManager = (TelephonyManager) context.getSystemService(a.qe);
        if (localTelephonyManager == null) {
            return carrierName;
        }
        carrierName = localTelephonyManager.getNetworkOperatorName();
        return carrierName != null ? carrierName : carrierName;
    }

    public static boolean isEmulator(Context context) {
        if (context == null || Secure.getString(context.getContentResolver(), "android_id") == null) {
            return false;
        }
        return true;
    }

    public static String hardwareId(Context context) {
        if (context == null) {
            return "unknown";
        }
        return Secure.getString(context.getContentResolver(), "android_id");
    }
}
