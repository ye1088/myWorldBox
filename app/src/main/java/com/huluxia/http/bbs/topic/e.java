package com.huluxia.http.bbs.topic;

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

/* compiled from: CommentRequest */
public class e extends a {
    private String address;
    private String detail;
    private List<String> images = new ArrayList();
    private float latitude;
    private float longitude;
    private long ss;
    private long sw;
    private String sx;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAk, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.optString("msg"));
        response.setCode(json.optInt("code", 0));
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("post_id", Long.toString(this.ss)));
        nvps.add(new BasicNameValuePair(ContainsSelector.CONTAINS_KEY, this.detail));
        nvps.add(new BasicNameValuePair("comment_id", Long.toString(this.sw)));
        nvps.add(new BasicNameValuePair("longitude", Float.toString(this.longitude)));
        nvps.add(new BasicNameValuePair("latitude", Float.toString(this.latitude)));
        nvps.add(new BasicNameValuePair(com.huluxia.data.profile.a.qf, this.address));
        String image = "";
        for (String szfid : this.images) {
            image = image + szfid + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        nvps.add(new BasicNameValuePair("images", image));
        nvps.add(new BasicNameValuePair("patcha", this.sx));
    }

    public long fs() {
        return this.ss;
    }

    public void x(long post_id) {
        this.ss = post_id;
    }

    public long fv() {
        return this.sw;
    }

    public void y(long comment_id) {
        this.sw = comment_id;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String fw() {
        return this.sx;
    }

    public void aM(String patcha) {
        this.sx = patcha;
    }

    public void b(float longitude) {
        this.longitude = longitude;
    }

    public void c(float latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
