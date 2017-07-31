package com.huluxia.http.bbs.category;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UserSigninRequest */
public class i extends a {
    private long sm;
    private long sq;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?cat_id=%d&user_id=%d", new Object[]{ab.azf, Long.valueOf(this.sm), Long.valueOf(this.sq)});
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

    public long fr() {
        return this.sq;
    }

    public void w(long user_id) {
        this.sq = user_id;
    }
}
