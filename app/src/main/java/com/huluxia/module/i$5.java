package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: JsModule */
class i$5 implements ErrorListener {
    final /* synthetic */ i ato;

    i$5(i this$0) {
        this.ato = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestJsInputCount onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 1281, null);
    }
}
