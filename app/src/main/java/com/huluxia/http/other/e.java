package com.huluxia.http.other;

import com.huluxia.data.j;
import com.huluxia.data.l;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CheckMiRequest */
public class e extends a {
    private String email;
    private String password;
    private long td;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAb, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (json.optInt("status") == 1) {
            j.ep().a(new l(json));
        }
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("mi_userid", String.valueOf(this.td)));
        nvps.add(new BasicNameValuePair("email", this.email));
        nvps.add(new BasicNameValuePair("password", UtilsMD5.MD5Code(this.password)));
    }

    public String fI() {
        return this.email;
    }

    public void aR(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long fO() {
        return this.td;
    }

    public void I(long miuid) {
        this.td = miuid;
    }
}
