package com.MCWorld.http.message;

import com.MCWorld.data.message.MsgCounts;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: MessageCountRequest */
public class a extends com.MCWorld.http.base.a {
    public String eU() {
        return String.format(Locale.getDefault(), ab.aAe, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (1 == json.optInt("status")) {
            response.setData(new MsgCounts(json.optJSONObject("counts")));
        }
    }

    public void g(List<NameValuePair> list) {
    }
}
