package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;

/* compiled from: RecommendAppModule */
class q$4 implements ErrorListener {
    q$4() {
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestRecommendAppInstallSuccCount onErrorResponse e = " + error, new Object[0]);
    }
}
