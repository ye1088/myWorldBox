package com.MCWorld.http.bbs.topic;

import com.MCWorld.http.base.a;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CommentRemoveRequest */
public class d extends a {
    private long sw;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?comment_id=%d", new Object[]{ab.aAj, Long.valueOf(this.sw)});
    }

    public void a(com.MCWorld.http.base.d response, JSONObject json) throws JSONException {
    }

    public void g(List<NameValuePair> list) {
    }

    public long fv() {
        return this.sw;
    }

    public void y(long comment_id) {
        this.sw = comment_id;
    }
}
