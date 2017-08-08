package com.MCWorld.module.profile;

import com.MCWorld.data.profile.ProductList;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: ProfileModule */
class g$36 implements Listener<String> {
    final /* synthetic */ g aCs;
    final /* synthetic */ int axC;

    g$36(g this$0, int i) {
        this.aCs = this$0;
        this.axC = i;
    }

    public void onResponse(String response) {
        try {
            JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
            if (json.optInt("status", 0) == 1) {
                ProductList list = new ProductList(json);
                EventNotifyCenter.notifyEvent(h.class, h.arb, new Object[]{Boolean.valueOf(true), list, Integer.valueOf(this.axC)});
                return;
            }
            EventNotifyCenter.notifyEvent(h.class, h.arb, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.axC)});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(h.class, h.arb, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.axC)});
        }
    }
}
