package com.MCWorld;

import com.MCWorld.utils.i;
import com.MCWorld.utils.j;
import com.MCWorld.widget.Constants.ReStartSoftFlag;
import com.MCWorld.widget.h;
import hlx.launch.game.a;
import hlx.ui.localresmgr.cache.b;

public class McApplication extends HTApplication {
    private static ReStartSoftFlag fP = ReStartSoftFlag.MC_RESTART_NORMAL;
    private static ReStartSoftFlag fQ = ReStartSoftFlag.MC_RESTART_NORMAL;

    public static int bA() {
        return fP.Value();
    }

    public static void j(int flag) {
        if (flag == ReStartSoftFlag.MC_RESTART_V0105.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_V0105;
        } else if (flag == ReStartSoftFlag.MC_RESTART_V0111.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_V0111;
        } else if (flag == ReStartSoftFlag.MC_RESTART_V0121.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_V0121;
        } else if (flag == ReStartSoftFlag.MC_RESTART_V0130.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_V0130;
        } else if (flag == ReStartSoftFlag.MC_RESTART_V0140.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_V0140;
        } else if (flag == ReStartSoftFlag.MC_RESTART_BLACK.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_BLACK;
        } else {
            fP = ReStartSoftFlag.MC_RESTART_NORMAL;
        }
    }

    public static int bB() {
        return fQ.Value();
    }

    public void onCreate() {
        super.onCreate();
        if (bS()) {
            h.NV();
            j.Kp();
            b.bq().a(a.Sf());
            b.bq().a(i.Ko());
            b.Us().init();
        }
    }
}
