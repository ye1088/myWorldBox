package com.huluxia.http.other;

import com.huluxia.data.j;
import com.huluxia.framework.BaseHttpMgr;
import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: BindDeviceUserRequest */
public class c extends a {
    private int ta;
    private String tb;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAi, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (json.optInt("status") == 1) {
            j.ep().D(true);
        } else {
            j.ep().D(false);
        }
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair(BaseHttpMgr.PARAM_DEVICE_CODE, this.tb));
        nvps.add(new BasicNameValuePair("app_type", String.valueOf(this.ta)));
    }

    public void ba(String deviceCode) {
        this.tb = deviceCode;
    }

    public void bi(int appType) {
        this.ta = appType;
    }
}
