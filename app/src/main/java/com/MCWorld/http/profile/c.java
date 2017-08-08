package com.MCWorld.http.profile;

import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import com.MCWorld.module.h;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: FollowFriendshipRequest */
public class c extends a {
    private long sq;
    private boolean tl;

    public String eU() {
        String szUrl = String.format(Locale.getDefault(), "%s?user_id=%d", new Object[]{ab.aAr, Long.valueOf(this.sq)});
        if (this.tl) {
            return szUrl;
        }
        return String.format(Locale.getDefault(), "%s?user_id=%d", new Object[]{ab.aAs, Long.valueOf(this.sq)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        EventNotifyCenter.notifyEvent(h.class, 544, new Object[0]);
    }

    public void g(List<NameValuePair> list) {
    }

    public long fr() {
        return this.sq;
    }

    public void w(long user_id) {
        this.sq = user_id;
    }

    public boolean fS() {
        return this.tl;
    }

    public void K(boolean follow) {
        this.tl = follow;
    }
}
