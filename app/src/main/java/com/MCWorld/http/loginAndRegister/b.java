package com.MCWorld.http.loginAndRegister;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PasswordResetRequest */
public class b extends a {
    private String sO;

    public String fG() {
        return this.sO;
    }

    public void aQ(String mailAddress) {
        this.sO = mailAddress;
    }

    public String eU() {
        String email = "";
        try {
            email = URLEncoder.encode(this.sO, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format(Locale.getDefault(), "%s?email=%s", new Object[]{ab.aAd, email});
    }

    public void g(List<NameValuePair> list) {
    }

    public void a(d response, JSONObject json) throws JSONException {
    }
}
