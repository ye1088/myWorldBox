package com.huluxia.module.profile;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: ProfileModule */
class g$11 implements ErrorListener {
    final /* synthetic */ g aCs;

    g$11(g this$0) {
        this.aCs = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEventUiThread(h.class, h.arp, new Object[]{Boolean.valueOf(false), "举报失败，网络问题"});
    }
}
