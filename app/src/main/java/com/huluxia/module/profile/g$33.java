package com.huluxia.module.profile;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: ProfileModule */
class g$33 implements ErrorListener {
    final /* synthetic */ String aBQ;
    final /* synthetic */ long aCj;
    final /* synthetic */ g aCs;

    g$33(g this$0, String str, long j) {
        this.aCs = this$0;
        this.aBQ = str;
        this.aCj = j;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arr, new Object[]{Boolean.valueOf(false), this.aBQ, null, Long.valueOf(this.aCj)});
    }
}
