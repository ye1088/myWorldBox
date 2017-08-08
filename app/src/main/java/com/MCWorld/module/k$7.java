package com.MCWorld.module;

import com.MCWorld.data.banner.a;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$7 implements Listener<String> {
    final /* synthetic */ k atq;

    k$7(k this$0) {
        this.atq = this$0;
    }

    public void onResponse(String response) {
        try {
            ((a) Json.parseJsonObject(response, a.class)).setUrl();
            EventNotifyCenter.notifyEvent(n.class, 529, new Object[]{info});
        } catch (Exception e) {
            HLog.error(this, "requestBannerInfo e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 529, null);
        }
    }
}
