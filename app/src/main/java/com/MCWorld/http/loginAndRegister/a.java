package com.MCWorld.http.loginAndRegister;

import com.MCWorld.data.h;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LoginMiRequest */
public class a extends com.MCWorld.http.base.a {
    private String sN;
    private long uid;

    public String eU() {
        return String.format(Locale.getDefault(), ab.azZ, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (json.optInt("status") == 1) {
            response.setData(new h(json));
        }
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("mi_userid", String.valueOf(this.uid)));
        nvps.add(new BasicNameValuePair("session", this.sN));
    }

    public long fF() {
        return this.uid;
    }

    public void F(long uid) {
        this.uid = uid;
    }

    public String getSession() {
        return this.sN;
    }

    public void setSession(String session) {
        this.sN = session;
    }
}
