package com.MCWorld.module;

import com.MCWorld.data.map.MapCateItem;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
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
