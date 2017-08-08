package com.MCWorld.module;

import com.MCWorld.data.map.MapCateItem;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import java.util.ArrayList;

/* compiled from: MapModule */
class k$11 implements Listener<String> {
    final /* synthetic */ k atq;

    k$11(k this$0) {
        this.atq = this$0;
    }

    public void onResponse(String response) {
        try {
            ArrayList<MapCateItem> mapList = MapCateItem.parseJsonStrByTag(response, "mapCategoryList");
            EventNotifyCenter.notifyEventUiThread(n.class, 292, new Object[]{Boolean.valueOf(true), mapList});
        } catch (Exception e) {
            HLog.error(this, "requestMapCate e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEventUiThread(n.class, 292, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
