package com.huluxia.module;

import com.huluxia.data.map.e;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$20 implements Listener<String> {
    final /* synthetic */ k atq;

    k$20(k this$0) {
        this.atq = this$0;
    }

    public void onResponse(String response) {
        try {
            e info = (e) Json.parseJsonObject(response, e.class);
            EventNotifyCenter.notifyEvent(n.class, 513, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            HLog.error(this, "requestMapDetail e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 513, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
