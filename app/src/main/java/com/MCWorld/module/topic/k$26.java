package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$26 implements ErrorListener {
    final /* synthetic */ k aCN;
    final /* synthetic */ boolean aCO;
    final /* synthetic */ Object aCP;
    final /* synthetic */ String val$tag;

    k$26(k this$0, String str, boolean z, Object obj) {
        this.aCN = this$0;
        this.val$tag = str;
        this.aCO = z;
        this.aCP = obj;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arH, new Object[]{null, this.val$tag, Boolean.valueOf(this.aCO), this.aCP});
    }
}
