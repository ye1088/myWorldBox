package com.huluxia.http.other;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PostShareRequest */
public class g extends a {
    public String eU() {
        return String.format(Locale.getDefault(), ab.azW, new Object[0]);
    }

    public void g(List<NameValuePair> list) {
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.optString(com.huluxia.data.profile.a.qf));
    }
}
