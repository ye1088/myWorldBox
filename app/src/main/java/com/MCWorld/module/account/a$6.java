package com.MCWorld.module.account;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;

/* compiled from: AccountModule */
class a$6 implements ErrorListener {
    final /* synthetic */ a aBH;

    a$6(a this$0) {
        this.aBH = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "loginOut onErrorResponse e = " + error, new Object[0]);
    }
}
