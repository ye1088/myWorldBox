package com.MCWorld.http.bbs.topic;

import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import com.MCWorld.r;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PublicTopicRequest */
public class m extends a {
    private String address;
    private String detail;
    private List<String> images = new ArrayList();
    private float latitude;
    private float longitude;
    private List<UserBaseInfo> remindUsers = new ArrayList();
    private long sH;
    private long sJ = 0;
    private long sm;
    private String sx;
    private String title;

    public String eU() {
        return String.format(Locale.getDefault(), ab.azN, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(json.optString("msg"));
        response.setCode(json.optInt("code", 0));
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("cat_id", Long.toString(this.sm)));
        nvps.add(new BasicNameValuePair("tag_id", Long.toString(this.sH)));
        nvps.add(new BasicNameValuePair("type", Long.toString(this.sJ)));
        nvps.add(new BasicNameValuePair("title", this.title));
        nvps.add(new BasicNameValuePair(r.gQ, this.detail));
        nvps.add(new BasicNameValuePair("longitude", Float.toString(this.longitude)));
        nvps.add(new BasicNameValuePair("latitude", Float.toString(this.latitude)));
        nvps.add(new BasicNameValuePair(com.MCWorld.data.profile.a.qf, this.address));
        String szImage = "";
        for (String szfid : this.images) {
            szImage = szImage + szfid + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        nvps.add(new BasicNameValuePair("images", szImage));
        nvps.add(new BasicNameValuePair("patcha", this.sx));
        if (!UtilsFunction.empty(this.remindUsers)) {
            String remindUsersStr = "";
            for (UserBaseInfo info : this.remindUsers) {
                remindUsersStr = remindUsersStr + info.userID + MiPushClient.ACCEPT_TIME_SEPARATOR;
            }
            nvps.add(new BasicNameValuePair("user_ids", remindUsersStr));
        }
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

    public void setRemindUsers(List<UserBaseInfo> remindUsers) {
        this.remindUsers = remindUsers;
    }

    public String fw() {
        return this.sx;
    }

    public void aM(String patcha) {
        this.sx = patcha;
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

    public long fC() {
        return this.sJ;
    }

    public void E(long type) {
        this.sJ = type;
    }
}
