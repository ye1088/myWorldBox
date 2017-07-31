package com.huluxia.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.huluxia.DownloadDialog;
import com.huluxia.HTApplication;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.mcinterface.e;
import com.huluxia.module.n;
import com.huluxia.r;
import com.huluxia.r.a;
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
                    r.ck().K(a.lz);
                } else {
                    hlx.ui.recommendapp.statisticsrecord.a.VI().b(packageName, true, true);
                }
                if (packageName.equals("com.mojang.minecraftpe")) {
                    String tmpVersionName = g.c(HTApplication.getAppContext().getPackageManager(), "com.mojang.minecraftpe");
                    if (tmpVersionName == null) {
                        return;
                    }
                    if (tmpVersionName.equals(e.ahL)) {
                        if (hlx.recorddata.a.TW()) {
                            c.Sg().mG(7);
                            EventNotifyCenter.notifyEvent(n.class, n.awQ, new Object[0]);
                        }
                    } else if (tmpVersionName.equals(e.ahM)) {
                        if (hlx.recorddata.a.TV()) {
                            c.Sg().mG(8);
                            EventNotifyCenter.notifyEvent(n.class, n.awQ, new Object[0]);
                        }
                    } else if (tmpVersionName.equals(e.ahK) && hlx.recorddata.a.TX()) {
                        c.Sg().mG(5);
                        EventNotifyCenter.notifyEvent(n.class, n.awQ, new Object[0]);
                    }
                }
            }
        }
    }
}
