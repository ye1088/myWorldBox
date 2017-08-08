package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: JsModule */
class i$6 implements Listener<String> {
    final /* synthetic */ i ato;

    i$6(i this$0) {
        this.ato = this$0;
    }

    public void onResponse(String response) {
        try {
            HLog.error(this, "requestJsDownloadCount response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 514, null);
        } catch (Exception e) {
            HLog.error(this, "requestJsDownloadCount e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 514, null);
        }
    }
}
