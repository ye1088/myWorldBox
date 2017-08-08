package com.MCWorld.controller.resource.handler.segments.impl;

import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.handler.segments.c;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsApkPackage;

/* compiled from: ApkHandler */
public class a extends c {
    public a(ResTaskInfo info) {
        super(info);
    }

    public String getSuffix() {
        return ".apk";
    }

    public void aj(final String path) {
        String signature;
        if (((ResTaskInfo) getInfo()).nb) {
            signature = UtilsApkPackage.getApkSignatureChar(AppConfig.getInstance().getAppContext(), path);
        } else {
            signature = null;
        }
        AppConfig.getInstance().getUiHandler().post(new Runnable(this) {
            final /* synthetic */ a oE;

            public void run() {
                if (((ResTaskInfo) this.oE.getInfo()).na) {
                    UtilsApkPackage.runInstallApp(AppConfig.getInstance().getAppContext(), path);
                }
                EventNotifyCenter.notifyEvent(com.MCWorld.controller.c.class, 265, new Object[]{((ResTaskInfo) this.oE.getInfo()).url, path, signature});
            }
        });
    }
}
