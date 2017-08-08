package com.MCWorld.http.profile;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UserPhotoDestoryRequest */
public class m extends a {
    private Set<String> tu = new HashSet();

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAx, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.optString("msg"));
        response.setCode(json.optInt("code", 0));
    }

    public void g(List<NameValuePair> nvps) {
        String szImage = "";
        for (String id : this.tu) {
            szImage = szImage + id + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        nvps.add(new BasicNameValuePair("photo_ids", szImage));
    }

    public Set<String> fV() {
        return this.tu;
    }

    public void b(Set<String> ids) {
        this.tu = ids;
    }

    public void setId(String id) {
        this.tu.clear();
        this.tu.add(id);
    }
}
