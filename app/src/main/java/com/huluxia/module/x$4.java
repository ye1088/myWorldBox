package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: SkinModule */
class x$4 implements ErrorListener {
    final /* synthetic */ x axw;

    x$4(x this$0) {
        this.axw = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestSkinDownCount onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, n.avI, null);
    }
}
