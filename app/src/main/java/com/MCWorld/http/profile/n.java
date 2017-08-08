package com.MCWorld.http.profile;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UserPhotoRequest */
public class n extends a {
    private List<String> images = new ArrayList();

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAy, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.optString("msg"));
        response.setCode(json.optInt("code", 0));
    }

    public void g(List<NameValuePair> nvps) {
        String szImage = "";
        for (String szfid : this.images) {
            szImage = szImage + szfid + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        nvps.add(new BasicNameValuePair("images", szImage));
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
