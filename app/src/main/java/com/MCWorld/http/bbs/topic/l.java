package com.MCWorld.http.bbs.topic;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PatchatRequest */
public class l extends a {
    public String eU() {
        return String.format(Locale.getDefault(), ab.azY, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.opt("url"));
    }

    public void g(List<NameValuePair> list) {
    }
}
