package com.huluxia.module.news;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: NewsModule */
class i$5 implements ErrorListener {
    final /* synthetic */ i aCh;
    final /* synthetic */ long aCj;

    i$5(i this$0, long j) {
        this.aCh = this$0;
        this.aCj = j;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.asH, new Object[]{Boolean.valueOf(false), Long.valueOf(this.aCj), null});
    }
}
