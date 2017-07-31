package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$10 implements ErrorListener {
    final /* synthetic */ k atq;

    k$10(k this$0) {
        this.atq = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestResCount onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 518, null);
    }
}
