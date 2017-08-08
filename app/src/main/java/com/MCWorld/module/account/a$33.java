package com.MCWorld.module.account;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: AccountModule */
class a$33 implements ErrorListener {
    final /* synthetic */ a aBH;

    a$33(a this$0) {
        this.aBH = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "bindQQinfo onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEventUiThread(h.class, h.ark, new Object[]{Boolean.valueOf(false), null, "请求失败，网络问题"});
    }
}
