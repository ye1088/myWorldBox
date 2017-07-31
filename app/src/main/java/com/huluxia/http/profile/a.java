package com.huluxia.http.profile;

import com.huluxia.data.j;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CheckApplyStatusRequest */
public class a extends com.huluxia.http.base.a {
    private long studio_id;
    private int tk;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?studio_id=%d", new Object[]{ab.azp, Long.valueOf(this.studio_id)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.tk = json.optInt("audit_status");
    }

    public void g(List<NameValuePair> list) {
    }

    public long fQ() {
        return this.studio_id;
    }

    public void J(long studio_id) {
        this.studio_id = studio_id;
    }

    public int fR() {
        if (j.ep().ey()) {
            return this.tk;
        }
        return 4;
    }
}
