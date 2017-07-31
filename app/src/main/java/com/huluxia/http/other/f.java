package com.huluxia.http.other;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tools.ant.types.selectors.ContainsSelector;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: FeedbackRequest */
public class f extends a {
    private List<String> images = new ArrayList();
    private String te;
    private String text;
    private int tf;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAc, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.optString("msg"));
        response.setCode(json.optInt("code", 0));
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair(ContainsSelector.CONTAINS_KEY, this.text));
        nvps.add(new BasicNameValuePair("ext", this.te));
        nvps.add(new BasicNameValuePair("flag", Integer.toString(this.tf)));
        String szImage = "";
        for (String szfid : this.images) {
            szImage = szImage + szfid + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        nvps.add(new BasicNameValuePair("images", szImage));
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String fP() {
        return this.te;
    }

    public void setExt(String ext) {
        this.te = ext;
    }

    public int getFlag() {
        return this.tf;
    }

    public void setFlag(int flag) {
        this.tf = flag;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
