package com.huluxia.module.profile;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: ProfileModule */
class g$39 implements ErrorListener {
    final /* synthetic */ g aCs;
    final /* synthetic */ long axx;

    g$39(g this$0, long j) {
        this.aCs = this$0;
        this.axx = j;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.ara, new Object[]{Boolean.valueOf(false), null, Long.valueOf(this.axx)});
    }
}
