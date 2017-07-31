package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;
import com.huluxia.module.w;

/* compiled from: TopicModule2 */
class k$3 implements Listener<String> {
    final /* synthetic */ k aCN;

    k$3(k this$0) {
        this.aCN = this$0;
    }

    public void onResponse(String response) {
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEventUiThread(h.class, h.arM, new Object[]{Boolean.valueOf(false), info});
                return;
            }
            EventNotifyCenter.notifyEventUiThread(h.class, h.arM, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.arM, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
