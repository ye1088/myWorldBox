package com.MCWorld.module.news;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: NewsModule */
class i$8 implements ErrorListener {
    final /* synthetic */ i aCh;

    i$8(i this$0) {
        this.aCh = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 1025, new Object[]{Boolean.valueOf(false), null});
    }
}
