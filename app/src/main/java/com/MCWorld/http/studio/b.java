package com.MCWorld.http.studio;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: MyAttentResCountRequest */
public class b extends a {
    private int count = 0;
    private String msg = "";
    private int status = 1;

    public String eU() {
        return String.format(Locale.getDefault(), "%s", new Object[]{ab.azz});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.status = json.optInt("status");
        this.msg = json.optString("msg");
        this.count = json.optInt("count");
    }

    public void g(List<NameValuePair> list) {
    }

    public boolean fW() {
        return 1 == this.status;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCount() {
        return this.count;
    }
}
