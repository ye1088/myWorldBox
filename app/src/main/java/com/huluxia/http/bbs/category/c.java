package com.huluxia.http.bbs.category;

import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.h;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CategorySubscribeRequest */
public class c extends a {
    private long sm;
    private boolean so;
    private String sp;

    public String eU() {
        String szUrl = String.format(Locale.getDefault(), "%s/category/subscibe%s?cat_ids=%d", new Object[]{a.rN, a.rP, Long.valueOf(this.sm)});
        if (this.so) {
            return szUrl;
        }
        return String.format(Locale.getDefault(), "%s/category/unsubscibe%s?cat_ids=%d", new Object[]{a.rN, a.rP, Long.valueOf(this.sm)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (response != null && response.getStatus() == 1) {
            EventNotifyCenter.notifyEvent(h.class, h.arA, new Object[]{this.sp});
        }
    }

    public void g(List<NameValuePair> list) {
    }

    public long fm() {
        return this.sm;
    }

    public void v(long cat_id) {
        this.sm = cat_id;
    }

    public boolean fo() {
        return this.so;
    }

    public void G(boolean subscibe) {
        this.so = subscibe;
    }

    public String fp() {
        return this.sp;
    }

    public void aL(String flag) {
        this.sp = flag;
    }
}
