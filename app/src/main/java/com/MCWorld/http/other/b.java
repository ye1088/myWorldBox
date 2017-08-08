package com.MCWorld.http.other;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import com.MCWorld.service.g;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: BindDeviceRequest */
public class b extends a {
    private String pu;
    private int sZ;
    private int ta;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAh, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        HLog.info(this, "bind device parse response cloudUserID is " + this.pu + " ,json is" + json, new Object[0]);
        if (json.optInt("status") == 1) {
            g.EA().d(true, this.pu);
        } else {
            g.EA().d(false, null);
        }
        g.EA().cm(false);
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("cloudUserID", this.pu));
        nvps.add(new BasicNameValuePair("cloudModel", String.valueOf(this.sZ)));
        nvps.add(new BasicNameValuePair("app_type", String.valueOf(this.ta)));
    }

    public void ay(String cloudUserID) {
        this.pu = cloudUserID;
    }

    public void bh(int cloudModel) {
        this.sZ = cloudModel;
    }

    public void bi(int appType) {
        this.ta = appType;
    }
}
