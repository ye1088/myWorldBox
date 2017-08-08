package com.MCWorld.http.bbs.topic;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: MoveTopicRequest */
public class k extends a {
    private long sH;
    private long sm;
    private long ss;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?post_id=%d&cat_id=%d&tag_id=%d", new Object[]{ab.azM, Long.valueOf(this.ss), Long.valueOf(this.sm), Long.valueOf(this.sH)});
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

    public long fm() {
        return this.sm;
    }

    public void v(long cat_id) {
        this.sm = cat_id;
    }

    public long fA() {
        return this.sH;
    }

    public void D(long tag_id) {
        this.sH = tag_id;
    }
}
