package com.huluxia.module;

import com.huluxia.data.map.MapCateItem;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import java.util.ArrayList;

/* compiled from: ModuleRequestWrapper */
class o$20 implements Listener<String> {
    final /* synthetic */ int axp;

    o$20(int i) {
        this.axp = i;
    }

    public void onResponse(String response) {
        try {
            ArrayList<MapCateItem> mapList = MapCateItem.parseJsonStr(response);
            EventNotifyCenter.notifyEvent(n.class, n.awa, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.axp), mapList});
        } catch (Exception e) {
            HLog.error(this, "requestMapCategory e = " + e + ", response = " + response, new Object[0]);
        }
    }
}
