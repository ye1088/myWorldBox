package com.huluxia.module.news;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: NewsModule */
class i$3 implements ErrorListener {
    final /* synthetic */ i aCh;
    final /* synthetic */ boolean aCi;

    i$3(i this$0, boolean z) {
        this.aCh = this$0;
        this.aCi = z;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 1028, new Object[]{Boolean.valueOf(false), Boolean.valueOf(this.aCi), "访问错误"});
    }
}
