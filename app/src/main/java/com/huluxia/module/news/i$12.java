package com.huluxia.module.news;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

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
