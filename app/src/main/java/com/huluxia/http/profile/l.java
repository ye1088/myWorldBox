package com.huluxia.http.profile;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: SubmitApplyStatusRequest */
public class l extends a {
    private long studio_id;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?studio_id=%d", new Object[]{ab.azm, Long.valueOf(this.studio_id)});
    }

    public void a(d response, JSONObject json) throws JSONException {
    }

    public void g(List<NameValuePair> list) {
    }

    public long fQ() {
        return this.studio_id;
    }

    public void J(long studio_id) {
        this.studio_id = studio_id;
    }
}
