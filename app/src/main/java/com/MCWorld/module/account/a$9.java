package com.MCWorld.module.account;

import com.MCWorld.HTApplication;
import com.MCWorld.data.j;
import com.MCWorld.data.k;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.service.i;
import com.MCWorld.utils.ah;

/* compiled from: AccountModule */
class a$9 implements Listener<String> {
    final /* synthetic */ a aBH;

    a$9(a this$0) {
        this.aBH = this$0;
    }

    public void onResponse(String response) {
        HLog.verbose("LoginActivity", "testQQLogin recv response " + response, new Object[0]);
        try {
            k info = (k) Json.parseJsonObject(response, k.class);
            if (info != null && info.isSucc()) {
                j.ep().a(info);
                ah.KZ().o(info.user.userID, 1);
                ah.KZ().p(info.user.userID, 1);
                i.EF();
                HTApplication.bR();
                EventNotifyCenter.notifyEvent(h.class, h.aqW, new Object[0]);
                a.DU().DY();
            }
            String msg = info == null ? "结果解析失败，请重试" : info.msg;
            EventNotifyCenter.notifyEventUiThread(h.class, h.Nd, new Object[]{info, msg});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.Nd, new Object[]{null, "结果解析失败，请重试"});
        }
    }
}
