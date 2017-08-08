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
class a$36 implements Listener<String> {
    final /* synthetic */ String MS;
    final /* synthetic */ a aBH;

    a$36(a this$0, String str) {
        this.aBH = this$0;
        this.MS = str;
    }

    public void onResponse(String response) {
        try {
            k info = (k) Json.parseJsonObject(response, k.class);
            if (info != null && info.isSucc()) {
                HLog.info("AccountModule", "isgold %d", new Object[]{Integer.valueOf(info.user.isgold)});
                j.ep().a(info);
                ah.KZ().bT(this.MS);
                ah.KZ().o(info.user.userID, info.checkstatus);
                ah.KZ().p(info.user.userID, info.qqinfostatus);
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
