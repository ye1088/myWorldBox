package com.huluxia.module;

import com.huluxia.HTApplication;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFunction;
import hlx.module.resources.a;
import hlx.module.resources.b;
import java.util.Iterator;

/* compiled from: ModuleRequestWrapper */
class o$13 implements Listener<b> {
    o$13() {
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((b) obj);
    }

    public void a(b info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(n.class, n.axe, new Object[]{Boolean.valueOf(true), info});
            return;
        }
        if (!UtilsFunction.empty(info.mapCategoryList)) {
            Iterator it = info.mapCategoryList.iterator();
            while (it.hasNext()) {
                a item = (a) it.next();
                HTApplication.b(item.cateid, item.catename);
            }
        }
        EventNotifyCenter.notifyEvent(n.class, n.axe, new Object[]{Boolean.valueOf(true), info.mapCategoryList});
    }
}
