package com.MCWorld.http.other;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
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
        response.setData(json.optString(com.MCWorld.data.profile.a.qf));
    }
}
