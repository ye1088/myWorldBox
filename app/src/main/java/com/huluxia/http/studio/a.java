package com.huluxia.http.studio;

import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CreateConditionRequest */
public class a extends com.huluxia.http.base.a {
    private String msg = "";
    private int status = 1;

    public String eU() {
        return String.format(Locale.getDefault(), "%s", new Object[]{ab.azx});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.status = json.optInt("status");
        this.msg = json.optString("msg");
    }

    public void g(List<NameValuePair> list) {
    }

    public boolean fW() {
        return 1 == this.status;
    }

    public String getMsg() {
        return this.msg;
    }
}
