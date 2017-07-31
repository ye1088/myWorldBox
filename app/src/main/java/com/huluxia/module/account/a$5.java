package com.huluxia.module.account;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.module.w;

/* compiled from: AccountModule */
class a$5 implements Listener<String> {
    final /* synthetic */ a aBH;

    a$5(a this$0) {
        this.aBH = this$0;
    }

    public void onResponse(String response) {
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (info != null) {
                HLog.info("AccountModule", "logOut code " + info.code, new Object[0]);
            }
        } catch (Exception e) {
        }
    }
}
