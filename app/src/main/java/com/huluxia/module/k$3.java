package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$3 implements Listener<String> {
    final /* synthetic */ k atq;

    k$3(k this$0) {
        this.atq = this$0;
    }

    public void onResponse(String response) {
        try {
            EventNotifyCenter.notifyEvent(n.class, 514, null);
        } catch (Exception e) {
            HLog.error(this, "requestMapDownloadCount e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 514, null);
        }
    }
}
