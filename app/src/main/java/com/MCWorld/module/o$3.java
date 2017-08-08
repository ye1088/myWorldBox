package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.ui.itemadapter.MyMapCateItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ModuleRequestWrapper */
class o$3 implements Listener<String> {
    o$3() {
    }

    public void onResponse(String response) {
        try {
            ArrayList<MyMapCateItem> mapList = MyMapCateItem.parseJsonStrByTag(response, "mapCategoryList");
            ArrayList<MyMapCateItem> jsList = MyMapCateItem.parseJsonStrByTag(response, "jsCategoryList");
            ArrayList<MyMapCateItem> skinList = MyMapCateItem.parseJsonStrByTag(response, "skinCategoryList");
            ArrayList<MyMapCateItem> woodList = MyMapCateItem.parseJsonStrByTag(response, "woodCategoryList");
            Map<String, ArrayList<MyMapCateItem>> info = new HashMap();
            info.put("map_cate", mapList);
            info.put("js_cate", jsList);
            info.put("skin_cate", skinList);
            info.put("wood_cate", woodList);
            EventNotifyCenter.notifyEvent(n.class, 291, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            HLog.error(this, "requestMapCategory e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 291, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
