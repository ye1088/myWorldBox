package com.huluxia.http.message;

import com.huluxia.data.message.MsgCounts;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: MessageCountRequest */
public class a extends com.huluxia.http.base.a {
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
