package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: TopicModule2 */
class k$18 implements ErrorListener {
    final /* synthetic */ k aCN;
    final /* synthetic */ Object rB;

    k$18(k this$0, Object obj) {
        this.aCN = this$0;
        this.rB = obj;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEventUiThread(h.class, 772, new Object[]{Boolean.valueOf(false), null, this.rB});
    }
}
