package com.huluxia.module;

import com.huluxia.data.map.e;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: JsModule */
class i$8 implements Listener<String> {
    final /* synthetic */ i ato;

    i$8(i this$0) {
        this.ato = this$0;
    }

    public void onResponse(String response) {
        try {
            e info = (e) Json.parseJsonObject(response, e.class);
            EventNotifyCenter.notifyEvent(n.class, 1282, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            HLog.error(this, "requestJsDetail e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 1282, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
