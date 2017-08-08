package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;

/* compiled from: SeedModule */
class u$8 implements ErrorListener {
    u$8() {
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestSeenGenerateCount onErrorResponse e = " + error, new Object[0]);
    }
}
