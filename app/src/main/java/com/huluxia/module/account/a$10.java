package com.huluxia.module.account;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: AccountModule */
class a$10 implements ErrorListener {
    final /* synthetic */ a aBH;

    a$10(a this$0) {
        this.aBH = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "login onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEventUiThread(h.class, h.Nd, new Object[]{null, "请求失败，网络问题"});
    }
}
