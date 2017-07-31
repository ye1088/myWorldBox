package com.huluxia.http.bbs.topic;

import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CheckFavoriteTopicRequest */
public class a extends com.huluxia.http.base.a {
    private long ss;
    private boolean st = false;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?post_id=%d", new Object[]{ab.azI, Long.valueOf(this.ss)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        boolean z = true;
        if (json.optInt("isFavorite") != 1) {
            z = false;
        }
        this.st = z;
    }

    public void g(List<NameValuePair> list) {
    }

    public long fs() {
        return this.ss;
    }

    public void x(long post_id) {
        this.ss = post_id;
    }

    public boolean isFavorite() {
        return this.st;
    }
}
