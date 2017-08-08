package com.MCWorld.data;

import com.MCWorld.login.k;
import com.tencent.mm.sdk.plugin.BaseProfile;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: SessionInfoOld */
public class l {
    public int age;
    public String avatar;
    public long credits;
    public int gender;
    public String key;
    public int level;
    public String nick;
    public int role;
    public long userID;

    public l(k obj) throws JSONException {
        this.key = obj.key;
        JSONObject user = null;
        this.userID = user.optLong("userID");
        this.nick = user.optString("nick");
        this.age = user.optInt("age");
        this.gender = user.optInt("gender");
        this.avatar = user.optString(BaseProfile.COL_AVATAR);
        this.role = user.optInt("role");
        this.level = user.optInt("level");
        this.credits = user.optLong("credits", 0);
    }

    public l(JSONObject obj) throws JSONException {
        this.key = obj.optString("_key");
        JSONObject user = obj.optJSONObject("user");
        this.userID = user.optLong("userID");
        this.nick = user.optString("nick");
        this.age = user.optInt("age");
        this.gender = user.optInt("gender");
        this.avatar = user.optString(BaseProfile.COL_AVATAR);
        this.role = user.optInt("role");
        this.level = user.optInt("level");
        this.credits = user.optLong("credits", 0);
    }

    public l(k session) {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getUserID() {
        return this.userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getLevel() {
        return this.level;
    }

    public long getCredits() {
        return this.credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }
}
