package com.huluxia.http.bbs.category;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CheckUserSigninRequest */
public class f extends a {
    private long sm;
    private long sq;
    private boolean sr = false;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?user_id=%d&cat_id=%d", new Object[]{ab.aze, Long.valueOf(this.sq), Long.valueOf(this.sm)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        boolean z = true;
        if (json.optInt("signin") != 1) {
            z = false;
        }
        this.sr = z;
    }

    public void g(List<NameValuePair> list) {
    }

    public void w(long user_id) {
        this.sq = user_id;
    }

    public void v(long cat_id) {
        this.sm = cat_id;
    }

    public boolean fq() {
        return this.sr;
    }

    public void H(boolean signin) {
        this.sr = signin;
    }
}
