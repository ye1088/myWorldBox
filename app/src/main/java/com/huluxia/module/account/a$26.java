package com.huluxia.module.account;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: AccountModule */
class a$26 implements ErrorListener {
    final /* synthetic */ a aBH;

    a$26(a this$0) {
        this.aBH = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestVoiceVeirify onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEventUiThread(h.class, h.arI, new Object[]{Boolean.valueOf(false), null});
    }
}
