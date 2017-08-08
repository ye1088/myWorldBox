package com.MCWorld.ui.mctool;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.module.m;
import com.MCWorld.module.n;
import com.MCWorld.module.o;
import hlx.module.resources.f;
import java.util.ArrayList;

/* compiled from: ServerListModuleWrapper */
public class e {
    public void y(int requestCode, int cateId, int start, int page) {
        o.p(requestCode, cateId, start, page);
    }

    public void DK() {
        HttpMgr.getInstance().performStringRequest(m.auP, new Listener<String>(this) {
            final /* synthetic */ e bcr;

            {
                this.bcr = this$0;
            }

            public void onResponse(String response) {
                try {
                    hlx.module.resources.e info = (hlx.module.resources.e) Json.parseJsonObject(response, hlx.module.resources.e.class);
                    if (info == null || !info.isSucc()) {
                        String msg = info == null ? "加载服务器分类失败" : info.msg;
                        EventNotifyCenter.notifyEventUiThread(n.class, 3076, new Object[]{Boolean.valueOf(false), null, msg});
                        return;
                    }
                    ArrayList<f> list = info.categorylist;
                    EventNotifyCenter.notifyEventUiThread(n.class, 3076, new Object[]{Boolean.valueOf(true), list, null});
                } catch (Exception e) {
                    EventNotifyCenter.notifyEventUiThread(n.class, 3076, new Object[]{Boolean.valueOf(false), null, "加载服务器分类失败"});
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ e bcr;

            {
                this.bcr = this$0;
            }

            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEventUiThread(n.class, 3076, new Object[]{Boolean.valueOf(false), null, "加载服务器分类失败"});
            }
        });
    }
}
