package com.MCWorld.module.account;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: AccountModule */
class a$21 implements ErrorListener {
    final /* synthetic */ a aBH;
    final /* synthetic */ int aBP;
    final /* synthetic */ String aBQ;

    a$21(a this$0, int i, String str) {
        this.aBH = this$0;
        this.aBP = i;
        this.aBQ = str;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "login onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEventUiThread(h.class, this.aBP, new Object[]{null, this.aBQ});
    }
}
