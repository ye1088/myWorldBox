package com.huluxia.module;

import com.huluxia.data.map.MapProfileInfo;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$14 implements Listener<String> {
    final /* synthetic */ Object amk;
    final /* synthetic */ int atr;
    final /* synthetic */ int ats;

    k$14(int i, int i2, Object obj) {
        this.atr = i;
        this.ats = i2;
        this.amk = obj;
    }

    public void onResponse(String response) {
        try {
            MapProfileInfo info = (MapProfileInfo) Json.parseJsonObject(response, MapProfileInfo.class);
            if (info == null || !info.isSucc()) {
                String msg = info == null ? "获取数据失败" : info.msg;
                EventNotifyCenter.notifyEvent(n.class, 528, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.atr), Integer.valueOf(this.ats), null, msg, this.amk});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, 528, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.atr), Integer.valueOf(this.ats), info, null, this.amk});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, 528, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.atr), Integer.valueOf(this.ats), null, "获取数据失败", this.amk});
        }
    }
}
