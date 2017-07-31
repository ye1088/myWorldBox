package com.huluxia.version;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;

/* compiled from: VersionModule */
class h$6 implements ErrorListener {
    final /* synthetic */ h boB;

    h$6(h this$0) {
        this.boB = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestVersionCount onErrorResponse e = " + error, new Object[0]);
    }
}
