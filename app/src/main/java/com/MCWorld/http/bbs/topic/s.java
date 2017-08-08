package com.MCWorld.http.bbs.topic;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TopicUnlockRequest */
public class s extends a {
    private long ss;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?post_id=%d", new Object[]{ab.azS, Long.valueOf(this.ss)});
    }

    public void a(d response, JSONObject json) throws JSONException {
    }

    public void g(List<NameValuePair> list) {
    }

    public long fs() {
        return this.ss;
    }

    public void x(long post_id) {
        this.ss = post_id;
    }
}
