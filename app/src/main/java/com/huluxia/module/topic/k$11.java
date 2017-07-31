package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;

/* compiled from: TopicModule2 */
class k$11 implements ErrorListener {
    final /* synthetic */ k aCN;

    k$11(k this$0) {
        this.aCN = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error("TopicModule2", "subscribeZone error response " + error, new Object[0]);
    }
}
