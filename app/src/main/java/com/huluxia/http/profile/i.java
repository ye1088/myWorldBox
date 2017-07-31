package com.huluxia.http.profile;

import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProfileRequest */
public class i extends a {
    private long sq;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?user_id=%d", new Object[]{ab.ayV, Long.valueOf(this.sq)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        ProfileInfo data = null;
        if (response.getStatus() == 1) {
            data = new ProfileInfo(json);
        }
        response.setData(data);
    }

    public void g(List<NameValuePair> list) {
    }

    public long fr() {
        return this.sq;
    }

    public void w(long user_id) {
        this.sq = user_id;
    }
}
