package com.MCWorld.http.map;

import com.MCWorld.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.m;
import com.MCWorld.r;
import com.tencent.open.SocialConstants;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PublishMapRequest */
public class b extends a {
    private String author;
    private String detail;
    private long id;
    private List<String> images = new ArrayList();
    private String md5;
    private long sW;
    private String sX;
    private boolean sY = false;
    private long sm;
    private String source;
    private String sx;
    private String title;
    private String url;
    private String version;

    public b() {
        F(false);
        bc(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS);
    }

    public String eU() {
        if (this.sY) {
            return String.format(Locale.getDefault(), m.avh, new Object[0]);
        }
        return String.format(Locale.getDefault(), m.avg, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.optString("msg"));
        response.setCode(json.optInt("code", 0));
    }

    public void g(List<NameValuePair> nvps) {
        if (this.sY) {
            nvps.add(new BasicNameValuePair("id", Long.toString(this.id)));
        }
        nvps.add(new BasicNameValuePair("cat_id", Long.toString(this.sm)));
        nvps.add(new BasicNameValuePair("title", this.title));
        nvps.add(new BasicNameValuePair("author", this.author));
        nvps.add(new BasicNameValuePair(SocialConstants.PARAM_SOURCE, this.source));
        nvps.add(new BasicNameValuePair("version", this.version));
        nvps.add(new BasicNameValuePair(r.gQ, this.detail));
        nvps.add(new BasicNameValuePair("res_url", this.url));
        nvps.add(new BasicNameValuePair("map_size", Long.toString(this.sW)));
        nvps.add(new BasicNameValuePair("md5", this.md5));
        nvps.add(new BasicNameValuePair("page_name", this.sX));
        String szImage = "";
        for (String szfid : this.images) {
            szImage = szImage + szfid + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        nvps.add(new BasicNameValuePair("images", szImage));
        nvps.add(new BasicNameValuePair("patcha", this.sx));
    }

    public long fm() {
        return this.sm;
    }

    public void v(long cat_id) {
        this.sm = cat_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void aY(String md5) {
        this.md5 = md5;
    }

    public void H(long map_size) {
        this.sW = map_size;
    }

    public void aZ(String page_name) {
        this.sX = page_name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUpdate(boolean isUpdate) {
        this.sY = isUpdate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
