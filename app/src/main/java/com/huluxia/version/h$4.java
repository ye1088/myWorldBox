package com.huluxia.version;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: VersionModule */
class h$4 implements ErrorListener {
    final /* synthetic */ h boB;
    final /* synthetic */ String val$tag;

    h$4(h this$0, String str) {
        this.boB = this$0;
        this.val$tag = str;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 773, new Object[]{Boolean.valueOf(false), null, this.val$tag});
        HLog.error(this, "requestVersionInfo onErrorResponse e = " + error, new Object[0]);
    }
}
