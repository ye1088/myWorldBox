package com.huluxia.http.other;

import com.huluxia.data.b;
import com.huluxia.http.base.c;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CartoonCheckRequest */
public class d extends c {
    private static final String tc = "http://v.huluxia.com/video_number.txt";

    public String eU() {
        return String.format(Locale.getDefault(), tc, new Object[0]);
    }

    public void g(List<NameValuePair> list) {
    }

    public void a(com.huluxia.http.base.d response, JSONObject json) throws JSONException {
        response.setData(new b(json));
    }
}
