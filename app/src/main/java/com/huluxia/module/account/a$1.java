package com.huluxia.module.account;

import com.huluxia.data.a;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: AccountModule */
class a$1 implements Listener<String> {
    final /* synthetic */ a aBH;

    a$1(a this$0) {
        this.aBH = this$0;
    }

    public void onResponse(String response) {
        try {
            a info = (a) Json.parseJsonObject(response, a.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEventUiThread(h.class, h.arj, new Object[]{Boolean.valueOf(false), info, "请求失败，请重试"});
                return;
            }
            EventNotifyCenter.notifyEventUiThread(h.class, h.arj, new Object[]{Boolean.valueOf(true), info, null});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.arj, new Object[]{Boolean.valueOf(false), null, "结果解析失败，请重试"});
        }
    }
}
