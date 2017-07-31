package com.huluxia.module;

import com.huluxia.data.banner.a;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

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
