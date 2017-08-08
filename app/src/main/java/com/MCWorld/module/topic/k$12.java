package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$12 implements ErrorListener {
    final /* synthetic */ String aBQ;
    final /* synthetic */ long aCL;
    final /* synthetic */ long aCM;
    final /* synthetic */ k aCN;

    k$12(k this$0, String str, long j, long j2) {
        this.aCN = this$0;
        this.aBQ = str;
        this.aCL = j;
        this.aCM = j2;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arf, new Object[]{Boolean.valueOf(false), this.aBQ, null, Long.valueOf(this.aCL), Long.valueOf(this.aCM)});
    }
}
