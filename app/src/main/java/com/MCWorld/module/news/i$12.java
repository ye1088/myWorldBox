package com.MCWorld.module.news;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: NewsModule */
class i$12 implements ErrorListener {
    final /* synthetic */ i aCh;
    final /* synthetic */ String aCk;

    i$12(i this$0, String str) {
        this.aCh = this$0;
        this.aCk = str;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 1027, new Object[]{Boolean.valueOf(false), null, this.aCk});
    }
}
