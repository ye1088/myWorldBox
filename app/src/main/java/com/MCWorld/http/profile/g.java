package com.MCWorld.http.profile;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProfileEditRequest */
public class g extends a {
    private long birthday;
    private String contact;
    private int gender;
    private String nick;
    private String sQ;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAu, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (json.optInt("status") != 1) {
        }
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("nick", this.nick));
        nvps.add(new BasicNameValuePair("gender", Integer.toString(this.gender)));
        nvps.add(new BasicNameValuePair("birthday", Long.toString(this.birthday)));
        nvps.add(new BasicNameValuePair("avatar_fid", this.sQ));
        nvps.add(new BasicNameValuePair("contact", this.contact));
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

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
