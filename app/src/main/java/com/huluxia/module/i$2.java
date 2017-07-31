package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: JsModule */
class i$2 implements ErrorListener {
    final /* synthetic */ i ato;

    i$2(i this$0) {
        this.ato = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestJSSearch onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 1284, new Object[]{Boolean.valueOf(false), null});
    }
}
