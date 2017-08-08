package com.MCWorld.framework.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;

public class UtilsNetwork {
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            NetworkInfo mNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkStrictlyAvailable(Context c) {
        if (c == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable() && ni.isConnected()) {
            return true;
        }
        String info;
        if (ni != null) {
            info = "network type = " + ni.getType() + ", " + (ni.isAvailable() ? "available" : "inavailable") + ", " + (ni.isConnected() ? "" : "not") + " connected";
        } else {
            info = "no active network";
        }
        Log.i("network", info);
        return false;
    }

    public static String getMACAddress(String interfaceName) {
        try {
            for (NetworkInterface intf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (interfaceName != null) {
                    if (intf.getName().equalsIgnoreCase(interfaceName)) {
                    }
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) {
                    return "";
                }
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++) {
                    buf.append(String.format("%02X:", new Object[]{Byte.valueOf(mac[idx])}));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            for (NetworkInterface intf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress addr : Collections.list(intf.getInetAddresses())) {
                    if (!addr.isLoopbackAddress()) {
                        boolean isIPv4;
                        String sAddr = addr.getHostAddress();
                        if (sAddr.indexOf(58) < 0) {
                            isIPv4 = true;
                        } else {
                            isIPv4 = false;
                        }
                        if (useIPv4) {
                            if (isIPv4) {
                                return sAddr;
                            }
                        } else if (!isIPv4) {
                            int delim = sAddr.indexOf(37);
                            return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static int networkSubType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.getSubtype();
            }
        }
        return 0;
    }

    public static String getNetworkType(Context context) {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return strNetworkType;
        }
        if (networkInfo.getType() == 1) {
            return "WIFI";
        }
        if (networkInfo.getType() != 0) {
            return strNetworkType;
        }
        String _strSubTypeName = networkInfo.getSubtypeName();
        switch (networkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                    return "3G";
                }
                return _strSubTypeName;
        }
    }
}
