package com.huluxia.module.account;

import com.huluxia.HTApplication;
import com.huluxia.data.j;
import com.huluxia.data.k;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;
import com.huluxia.service.i;
import com.huluxia.utils.ah;

/* compiled from: AccountModule */
class a$34 implements Listener<String> {
    final /* synthetic */ String MS;
    final /* synthetic */ a aBH;

    a$34(a this$0, String str) {
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
                ah.KZ().ai(this.MS, info.session_key);
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
