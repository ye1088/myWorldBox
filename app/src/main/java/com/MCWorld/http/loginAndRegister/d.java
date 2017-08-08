package com.MCWorld.http.loginAndRegister;

import com.MCWorld.HTApplication;
import com.MCWorld.data.j;
import com.MCWorld.data.k;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsMD5;
import com.MCWorld.http.base.a;
import com.MCWorld.module.h;
import com.MCWorld.service.i;
import com.MCWorld.utils.ah;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: RegisterRequest */
public class d extends a {
    private String email;
    private String nick;
    private String openid;
    private String password;
    private String sQ;
    private String sR;
    private String sS;
    private String sT;
    private String sU;
    private String sV = null;

    public String eU() {
        return String.format(Locale.getDefault(), "%s/account/register/ANDROID/2.1", new Object[]{a.rN});
    }

    public void a(com.MCWorld.http.base.d response, JSONObject json) throws JSONException {
        if (json.optInt("status") == 1) {
            k info = new k(json);
            j.ep().a(info);
            ah.KZ().bT(this.email);
            if (this.sV != null) {
                ah.KZ().o(info.user.userID, 1);
                ah.KZ().p(info.user.userID, 1);
            }
            i.EF();
            HTApplication.bR();
            EventNotifyCenter.notifyEvent(h.class, h.aqW, new Object[0]);
            com.MCWorld.module.account.a.DU().DY();
        }
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("email", this.email));
        nvps.add(new BasicNameValuePair("password", this.password));
        nvps.add(new BasicNameValuePair("voice_code", this.sR));
        nvps.add(new BasicNameValuePair("nick", this.nick));
        nvps.add(new BasicNameValuePair("gender", this.sS));
        nvps.add(new BasicNameValuePair("birthday", this.sT));
        nvps.add(new BasicNameValuePair("avatar_fid", this.sQ));
        nvps.add(new BasicNameValuePair("openid", this.openid));
        nvps.add(new BasicNameValuePair("access_token", this.sU));
        nvps.add(new BasicNameValuePair("qqinfo", this.sV));
    }

    public String fI() {
        return this.email;
    }

    public void aR(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = UtilsMD5.MD5Code(password);
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String fJ() {
        return this.sS;
    }

    public void aS(String gender) {
        this.sS = gender;
    }

    public String fK() {
        return this.sT;
    }

    public void aT(String birthday) {
        this.sT = birthday;
    }

    public String getAvatar_fid() {
        return this.sQ;
    }

    public void setAvatar_fid(String avatar_fid) {
        this.sQ = avatar_fid;
    }

    public String fL() {
        return this.openid;
    }

    public void aU(String openid) {
        this.openid = openid;
    }

    public String fM() {
        return this.sU;
    }

    public void aV(String access_token) {
        this.sU = access_token;
    }

    public String fN() {
        return this.sV;
    }

    public void aW(String qqinfo) {
        this.sV = qqinfo;
    }

    public void aX(String voice_code) {
        this.sR = voice_code;
    }
}
