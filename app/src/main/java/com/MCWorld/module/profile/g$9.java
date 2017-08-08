package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: ProfileModule */
class g$9 implements Listener<String> {
    final /* synthetic */ String MZ;
    final /* synthetic */ String aCk;
    final /* synthetic */ g aCs;
    final /* synthetic */ String aCu;

    g$9(g this$0, String str, String str2, String str3) {
        this.aCs = this$0;
        this.aCk = str;
        this.MZ = str2;
        this.aCu = str3;
    }

    public void onResponse(String response) {
        try {
            if (((JSONObject) new JSONTokener(response).nextValue()).optInt("ret", -1) == 0) {
                EventNotifyCenter.notifyEvent(h.class, h.aro, new Object[]{Boolean.valueOf(true), response, this.aCk, this.MZ, this.aCu});
                return;
            }
            EventNotifyCenter.notifyEvent(h.class, h.aro, new Object[]{Boolean.valueOf(false), null, this.aCk, this.MZ, this.aCu});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(h.class, h.aro, new Object[]{Boolean.valueOf(false), null, this.aCk});
        }
    }
}
