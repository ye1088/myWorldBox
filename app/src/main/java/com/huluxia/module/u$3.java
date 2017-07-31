package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import hlx.module.resources.b;

/* compiled from: SeedModule */
class u$3 implements Listener<String> {
    u$3() {
    }

    public void onResponse(String response) {
        try {
            b info = (b) Json.parseJsonObject(response, b.class);
            if (info == null || !info.isSucc()) {
                String msg = info == null ? "加载种子分类失败" : info.msg;
                EventNotifyCenter.notifyEventUiThread(n.class, 301, new Object[]{Boolean.valueOf(false), null, msg});
                return;
            }
            EventNotifyCenter.notifyEventUiThread(n.class, 301, new Object[]{Boolean.valueOf(true), info.categorylist, null});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(n.class, 301, new Object[]{Boolean.valueOf(false), null, "加载种子分类失败"});
        }
    }
}
