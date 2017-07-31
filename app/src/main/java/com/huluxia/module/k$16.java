package com.huluxia.module;

import com.huluxia.data.map.f;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$16 implements Listener<String> {
    final /* synthetic */ k atq;

    k$16(k this$0) {
        this.atq = this$0;
    }

    public void onResponse(String response) {
        try {
            HLog.verbose(this, "requestMapRanking response = " + response, new Object[0]);
            f info = (f) Json.parseJsonObject(response, f.class);
            EventNotifyCenter.notifyEvent(n.class, 515, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            HLog.error(this, "requestMapRanking e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 515, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
