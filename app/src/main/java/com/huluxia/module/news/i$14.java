package com.huluxia.module.news;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: NewsModule */
class i$14 implements ErrorListener {
    final /* synthetic */ i aCh;

    i$14(i this$0) {
        this.aCh = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.asF, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false), "访问失败"});
    }
}
