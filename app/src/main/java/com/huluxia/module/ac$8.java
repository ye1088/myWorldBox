package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: WoodModule */
class ac$8 implements ErrorListener {
    final /* synthetic */ ac aBD;

    ac$8(ac this$0) {
        this.aBD = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestWoodSearch onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, n.avQ, new Object[]{Boolean.valueOf(false), null});
    }
}
