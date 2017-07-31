package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: SkinModule */
class x$3 implements Listener<String> {
    final /* synthetic */ x axw;

    x$3(x this$0) {
        this.axw = this$0;
    }

    public void onResponse(String response) {
        try {
            HLog.verbose(this, "requestSkinDownCount response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.avI, null);
        } catch (Exception e) {
            HLog.error(this, "requestSkinDownCount e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.avI, null);
        }
    }
}
