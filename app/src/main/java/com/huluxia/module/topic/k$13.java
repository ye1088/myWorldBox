package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;
import org.json.JSONObject;

/* compiled from: TopicModule2 */
class k$13 implements Listener<String> {
    final /* synthetic */ k aCN;

    k$13(k this$0) {
        this.aCN = this$0;
    }

    public void onResponse(String category) {
        try {
            if (new JSONObject(category).optInt("status") == 1) {
                EventNotifyCenter.notifyEvent(h.class, h.arA, new Object[]{""});
                return;
            }
            HLog.error("TopicModule2", "subscribeZone error response failed", new Object[0]);
        } catch (Exception e) {
            HLog.error("TopicModule2", "subscribeZone ex " + e, new Object[0]);
        }
    }
}
