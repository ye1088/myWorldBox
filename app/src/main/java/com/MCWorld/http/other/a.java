package com.MCWorld.http.other;

import com.MCWorld.data.j;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: BindCloudUserRequest */
public class a extends com.MCWorld.http.base.a {
    private String pu;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAg, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (json.optInt("status") == 1) {
            j.ep().D(true);
        } else {
            j.ep().D(false);
        }
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("cloudUserID", this.pu));
    }

    public void ay(String cloudUserID) {
        this.pu = cloudUserID;
    }
}
