package com.MCWorld.version;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.module.w;
import com.MCWorld.utils.ah;

/* compiled from: VersionModule */
class h$5 implements Listener<String> {
    final /* synthetic */ h boB;
    final /* synthetic */ long boC;

    h$5(h this$0, long j) {
        this.boB = this$0;
        this.boC = j;
    }

    public void onResponse(String response) {
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (info != null && info.isSucc()) {
                ah.KZ().bx(this.boC);
                HLog.debug(this, "requestVersionCount succ " + this.boC, new Object[0]);
            }
        } catch (Exception e) {
            HLog.error(this, "requestVersionCount e = " + e + ", response = " + response, new Object[0]);
        }
    }
}
