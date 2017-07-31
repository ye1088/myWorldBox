package com.huluxia.http.bbs.category;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CategoryVoteRequest */
public class e extends a {
    private long sm;

    public String eU() {
        return String.format(Locale.getDefault(), "%s/category/vote%s?cat_id=%d", new Object[]{a.rN, a.rP, Long.valueOf(this.sm)});
    }

    public void a(d response, JSONObject json) throws JSONException {
    }

    public void g(List<NameValuePair> list) {
    }

    public long fm() {
        return this.sm;
    }

    public void v(long cat_id) {
        this.sm = cat_id;
    }
}
