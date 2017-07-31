package com.huluxia.module.account;

import com.huluxia.data.e;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.account.a.a;
import com.huluxia.module.h;
import com.huluxia.utils.ah;

/* compiled from: AccountModule */
class a$14 implements Listener<String> {
    final /* synthetic */ a aBH;
    final /* synthetic */ long rD;

    a$14(a this$0, long j) {
        this.aBH = this$0;
        this.rD = j;
    }

    public void onResponse(String response) {
        try {
            a info = (a) Json.parseJsonObject(response, a.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEventUiThread(h.class, h.arB, new Object[]{Boolean.valueOf(false), info});
                return;
            }
            e msgRemind = new e();
            msgRemind.A(info.isNotify());
            msgRemind.B(info.isHarry());
            msgRemind.C(info.isSound());
            msgRemind.x(info.isVibration());
            ah.KZ().a(this.rD, msgRemind);
            EventNotifyCenter.notifyEventUiThread(h.class, h.arB, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.arB, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
