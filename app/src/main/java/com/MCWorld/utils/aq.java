package com.MCWorld.utils;

import com.MCWorld.McApplication;
import com.MCWorld.widget.Constants.ReStartSoftFlag;
import com.xiaomi.mipush.sdk.MiPushClient;
import hlx.data.localstore.a;

/* compiled from: UtilsResVerCheck */
public class aq {
    public static int MB() {
        return McApplication.bB();
    }

    public static String gk(String netVer) {
        if (netVer == null || netVer.isEmpty()) {
            return MC();
        }
        String ver = "";
        if (netVer.contains(a.bJr)) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0105.Value())});
        }
        if (netVer.contains(a.bJs)) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0111.Value())});
        }
        if (netVer.contains(a.bJt)) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0121.Value())});
        }
        if (netVer.contains("0.13")) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0130.Value())});
        }
        if (netVer.contains("0.14")) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0140.Value())});
        }
        if (netVer.contains(a.bJy)) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0150.Value())});
        }
        if (ver.endsWith(MiPushClient.ACCEPT_TIME_SEPARATOR)) {
            return ver.substring(0, ver.length() - 1);
        }
        return MC();
    }

    public static String at(String netVer, String subVersion) {
        if (netVer == null || netVer.isEmpty()) {
            return MC();
        }
        String ver = "";
        if (netVer.contains(a.bJr)) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0105.Value())});
        }
        if (netVer.contains(a.bJs)) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0111.Value())});
        }
        if (netVer.contains(a.bJt)) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0121.Value())});
        }
        if (netVer.contains("0.13")) {
            if (subVersion == null || !subVersion.equals("0")) {
                if (subVersion != null && subVersion.contains("0.13.0")) {
                    ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0130.Value())});
                }
                if (subVersion != null && subVersion.contains("0.13.1")) {
                    ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0131.Value())});
                }
            } else {
                ver = (ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0130.Value())})) + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0131.Value())});
            }
        }
        if (netVer.contains("0.14")) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0140.Value())});
        }
        if (netVer.contains(a.bJy)) {
            ver = ver + String.format("%d,", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0150.Value())});
        }
        if (ver.endsWith(MiPushClient.ACCEPT_TIME_SEPARATOR)) {
            return ver.substring(0, ver.length() - 1);
        }
        return MC();
    }

    public static String MC() {
        return String.format("%d,%d,%d,%d,%d,%d", new Object[]{Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0105.Value()), Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0111.Value()), Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0121.Value()), Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0130.Value()), Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0131.Value()), Integer.valueOf(ReStartSoftFlag.MC_RESTART_V0140.Value())});
    }

    public static boolean S(String ver, int intVer) {
        String myver = String.format("%d", new Object[]{Integer.valueOf(intVer)});
        if (ver == null || ver.length() == 0 || ver.contains(myver)) {
            return true;
        }
        return false;
    }
}
