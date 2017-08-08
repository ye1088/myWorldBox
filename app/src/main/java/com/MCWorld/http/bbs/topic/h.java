package com.MCWorld.http.bbs.topic;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CreditsTransferRequest */
public class h extends a {
    private long sE = 0;
    private String sF;
    private String sG;
    private long ss;
    private long sy = 1;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAn, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("post_id", Long.toString(this.ss)));
        nvps.add(new BasicNameValuePair("type_id", Long.toString(this.sy)));
        nvps.add(new BasicNameValuePair("isadmin", Long.toString(this.sE)));
        nvps.add(new BasicNameValuePair("score", this.sF));
        nvps.add(new BasicNameValuePair("score_txt", this.sG));
    }

    public void I(boolean isTopic) {
        this.sy = isTopic ? 1 : 2;
    }

    public void x(long post_id) {
        this.ss = post_id;
    }

    public void aN(String score) {
        this.sF = score;
    }

    public void aO(String score_txt) {
        this.sG = score_txt;
    }

    public void C(long isadmin) {
        this.sE = isadmin;
    }
}
