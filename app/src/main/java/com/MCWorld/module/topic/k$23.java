package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$23 implements ErrorListener {
    final /* synthetic */ String aBQ;
    final /* synthetic */ k aCN;

    k$23(k this$0, String str) {
        this.aCN = this$0;
        this.aBQ = str;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.ars, new Object[]{Boolean.valueOf(false), this.aBQ, null});
    }
}
