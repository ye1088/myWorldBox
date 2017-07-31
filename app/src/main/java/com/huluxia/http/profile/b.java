package com.huluxia.http.profile;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CheckFriendshipRequest */
public class b extends a {
    private long sq;
    private boolean tl;
    private int tn;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?user_id=%d", new Object[]{ab.aAo, Long.valueOf(this.sq)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.tn = json.optInt("friendship");
        if (1 == this.tn || 2 == this.tn) {
            this.tl = true;
        } else {
            this.tl = false;
        }
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

    public int fT() {
        return this.tn;
    }
}
