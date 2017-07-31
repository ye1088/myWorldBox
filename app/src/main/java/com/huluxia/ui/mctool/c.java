package com.huluxia.ui.mctool;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PatchatRequest */
public class c extends a {
    public String eU() {
        return String.format(Locale.getDefault(), "%s/patcha", new Object[]{a.rN});
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.opt("url"));
    }

    public void g(List<NameValuePair> list) {
    }
}
