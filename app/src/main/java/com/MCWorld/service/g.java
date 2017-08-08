package com.MCWorld.service;

import com.MCWorld.data.c;
import com.MCWorld.framework.base.widget.UtilsAndroid;
import com.MCWorld.utils.ah;

/* compiled from: HlxPushState */
public class g {
    private static g aDv = null;
    private boolean aDr = false;
    private String aDs = null;
    private boolean aDt = false;
    private boolean aDu = false;

    public boolean Ew() {
        return this.aDr;
    }

    public void ck(boolean xingeRegistered) {
        this.aDr = xingeRegistered;
    }

    public String Ex() {
        return this.aDs;
    }

    public void ee(String cloudId) {
        this.aDs = cloudId;
    }

    public boolean Ey() {
        String deviceCode = UtilsAndroid.fetchDeviceId();
        if (deviceCode == null) {
            return false;
        }
        c info = ah.KZ().Ma();
        if (info == null || !deviceCode.equals(info.devicecode)) {
            return false;
        }
        return true;
    }

    public boolean ef(String cloudId) {
        String deviceCode = UtilsAndroid.fetchDeviceId();
        if (deviceCode == null || cloudId == null) {
            return false;
        }
        if (ah.KZ().Mc()) {
            c info = ah.KZ().Ma();
            if (info != null && deviceCode.equals(info.devicecode) && cloudId.equals(info.cloudid)) {
                return true;
            }
            return false;
        }
        ah.KZ().Mb();
        ah.KZ().de(true);
        return false;
    }

    public void d(boolean deviceBind, String cloudId) {
        String deviceCode = UtilsAndroid.fetchDeviceId();
        if (!deviceBind || deviceCode == null || cloudId == null) {
            ah.KZ().Mb();
            return;
        }
        ah.KZ().a(new c(deviceCode, cloudId));
    }

    public boolean Ez() {
        return this.aDu;
    }

    public void cl(boolean userBind) {
        this.aDu = userBind;
    }

    public static synchronized g EA() {
        g gVar;
        synchronized (g.class) {
            if (aDv == null) {
                aDv = new g();
            }
            gVar = aDv;
        }
        return gVar;
    }

    public boolean EB() {
        return this.aDt;
    }

    public void cm(boolean deviceBinding) {
        this.aDt = deviceBinding;
    }
}
