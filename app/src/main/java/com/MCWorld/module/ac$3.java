package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: WoodModule */
class ac$3 implements Listener<String> {
    final /* synthetic */ ac aBD;

    ac$3(ac this$0) {
        this.aBD = this$0;
    }

    public void onResponse(String response) {
        try {
            HLog.error(this, "requestWoodDownCount response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.avN, null);
        } catch (Exception e) {
            HLog.error(this, "requestWoodDownCount e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.avN, null);
        }
    }
}
