package com.huluxia.module;

import com.huluxia.data.server.a;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: ServerModule */
class v$3 implements Listener<String> {
    final /* synthetic */ v axu;

    v$3(v this$0) {
        this.axu = this$0;
    }

    public void onResponse(String response) {
        try {
            a info = (a) Json.parseJsonObject(response, a.class);
            EventNotifyCenter.notifyEvent(n.class, 260, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            HLog.error(this, "requestMapRanking e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 260, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
