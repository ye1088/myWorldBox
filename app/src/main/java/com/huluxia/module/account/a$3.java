package com.huluxia.module.account;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: AccountModule */
class a$3 implements Listener<String> {
    final /* synthetic */ a aBH;

    a$3(a this$0) {
        this.aBH = this$0;
    }

    public void onResponse(String response) {
        try {
            b info = (b) Json.parseJsonObject(response, b.class);
            String msg = info == null ? "结果解析失败，请重试" : info.msg;
            EventNotifyCenter.notifyEventUiThread(h.class, h.arm, new Object[]{info, msg});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.arm, new Object[]{null, "结果解析失败，请重试"});
        }
    }
}
