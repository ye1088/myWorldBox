package com.MCWorld.http.bbs.topic;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TopicLockRequest */
public class q extends a {
    private String sM;
    private long ss;

    public String eU() {
        return String.format(Locale.getDefault(), "%s/post/lock%s?post_id=%d", new Object[]{a.rN, a.rP, Long.valueOf(this.ss)});
    }

    public void a(d response, JSONObject json) throws JSONException {
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("memo", this.sM));
    }

    public long fs() {
        return this.ss;
    }

    public void x(long post_id) {
        this.ss = post_id;
    }

    public String fE() {
        return this.sM;
    }

    public void aP(String memo) {
        this.sM = memo;
    }
}
