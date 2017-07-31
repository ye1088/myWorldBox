package com.huluxia.http.loginAndRegister;

import com.huluxia.data.j;
import com.huluxia.data.l;
import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: RegisterByMiRequest */
public class c extends a {
    private long birthday;
    private int gender;
    private String nick;
    private long sP;
    private String sQ;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAa, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (json.optInt("status") == 1) {
            j.ep().a(new l(json));
        }
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("mi_userid", String.valueOf(this.sP)));
        nvps.add(new BasicNameValuePair("nick", this.nick));
        nvps.add(new BasicNameValuePair("gender", Integer.toString(this.gender)));
        nvps.add(new BasicNameValuePair("birthday", Long.toString(this.birthday)));
        nvps.add(new BasicNameValuePair("avatar_fid", this.sQ));
    }

    public long fH() {
        return this.sP;
    }

    public void G(long miUid) {
        this.sP = miUid;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return this.birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getAvatar_fid() {
        return this.sQ;
    }

    public void setAvatar_fid(String avatar_fid) {
        this.sQ = avatar_fid;
    }
}
