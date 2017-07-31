package com.huluxia.http.bbs.topic;

import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import com.huluxia.r;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ModifyTopicRequest */
public class j extends a {
    private String address;
    private String detail;
    private List<String> images = new ArrayList();
    private float latitude;
    private float longitude;
    private long sH;
    private List<String> sI = new ArrayList();
    private long ss;
    private String title;

    public String eU() {
        return String.format(Locale.getDefault(), ab.azL, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.optString("msg"));
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("post_id", Long.toString(this.ss)));
        nvps.add(new BasicNameValuePair("tag_id", Long.toString(this.sH)));
        nvps.add(new BasicNameValuePair("title", this.title));
        nvps.add(new BasicNameValuePair(r.gQ, this.detail));
        nvps.add(new BasicNameValuePair("longitude", Float.toString(this.longitude)));
        nvps.add(new BasicNameValuePair("latitude", Float.toString(this.latitude)));
        nvps.add(new BasicNameValuePair(com.huluxia.data.profile.a.qf, this.address));
        String szImage = "";
        for (String szfid : this.images) {
            szImage = szImage + szfid + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        nvps.add(new BasicNameValuePair("images", szImage));
        String szUsers = "";
        for (String szUser : this.sI) {
            szUsers = szUsers + szUser + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        nvps.add(new BasicNameValuePair("user_ids", szUsers));
    }

    public long fs() {
        return this.ss;
    }

    public void x(long post_id) {
        this.ss = post_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public long fA() {
        return this.sH;
    }

    public void D(long tag_id) {
        this.sH = tag_id;
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

    public List<String> fB() {
        return this.sI;
    }

    public void i(List<String> user_ids) {
        this.sI = user_ids;
    }
}
