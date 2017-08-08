package com.MCWorld.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.MCWorld.DownloadDialog;
import com.MCWorld.HTApplication;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.mcinterface.e;
import com.MCWorld.module.n;
import com.MCWorld.r;
import com.MCWorld.r.a;
import hlx.launch.game.c;
import hlx.utils.g;

public class APKInstallReceiver extends BroadcastReceiver {
    private static final int aCV = 8;

    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String data = intent.getDataString();
            HLog.verbose("TAG", "LSPrint APKInstallReceiver data: %s", data);
            if (data != null && data.length() > 8) {
                String packageName = data.substring(8);
                if (packageName.equals(DownloadDialog.bu().bt())) {
                    r.ck().K_umengEvent(a.lz);
                } else {
                    hlx.ui.recommendapp.statisticsrecord.a.VI().b(packageName, true, true);
                }
                if (packageName.equals("com.mojang.minecraftpe")) {
                    String tmpVersionName = g.c(HTApplication.getAppContext().getPackageManager(), "com.mojang.minecraftpe");
                    if (tmpVersionName == null) {
                        return;
                    }
                    if (tmpVersionName.equals(e.ahL_v01540)) {
                        if (hlx.recorddata.a.TW()) {
                            c.Sg().setStartGameVersion(7);
                            EventNotifyCenter.notifyEvent(n.class, n.awQ, new Object[0]);
                        }
                    } else if (tmpVersionName.equals(e.ahM_v01410)) {
                        if (hlx.recorddata.a.TV()) {
                            c.Sg().setStartGameVersion(8);
                            EventNotifyCenter.notifyEvent(n.class, n.awQ, new Object[0]);
                        }
                    } else if (tmpVersionName.equals(e.ahK_v0141) && hlx.recorddata.a.TX()) {
                        c.Sg().setStartGameVersion(5);
                        EventNotifyCenter.notifyEvent(n.class, n.awQ, new Object[0]);
                    }
                }
            }
        }
    }
}
