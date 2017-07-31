package com.huluxia.module.profile;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: ProfileModule */
class g$34 implements ErrorListener {
    final /* synthetic */ g aCs;
    final /* synthetic */ String aCy;

    g$34(g this$0, String str) {
        this.aCs = this$0;
        this.aCy = str;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.aqZ, new Object[]{Boolean.valueOf(false), this.aCy, "修改昵称失败\n网络问题"});
    }
}
