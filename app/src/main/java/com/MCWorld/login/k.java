package com.MCWorld.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.tencent.mm.sdk.plugin.BaseProfile;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: SessionInfo */
public class k implements Parcelable {
    public static final Creator<k> CREATOR = new 1();
    public int age;
    public String avatar;
    public long credits;
    public String email;
    public String encryptPwd;
    public int gender;
    public String key;
    public int level;
    public String nick;
    public int role;
    public long userID;

    public k(JSONObject obj) throws JSONException {
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

    public k(String email, String encryptPwd) {
        this.email = email;
        this.encryptPwd = encryptPwd;
    }

    public k(Parcel source) {
        this.key = source.readString();
        this.userID = source.readLong();
        this.nick = source.readString();
        this.avatar = source.readString();
        this.age = source.readInt();
        this.gender = source.readInt();
        this.role = source.readInt();
        this.level = source.readInt();
        this.credits = source.readLong();
        this.email = source.readString();
        this.encryptPwd = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeLong(this.userID);
        dest.writeString(this.nick);
        dest.writeString(this.avatar);
        dest.writeInt(this.age);
        dest.writeInt(this.gender);
        dest.writeInt(this.role);
        dest.writeInt(this.level);
        dest.writeLong(this.credits);
        dest.writeString(this.email);
        dest.writeString(this.encryptPwd);
    }

    public String toString() {
        return "SessionInfo{key='" + this.key + '\'' + ", userID=" + this.userID + ", nick='" + this.nick + '\'' + ", avatar='" + this.avatar + '\'' + ", age=" + this.age + ", gender=" + this.gender + ", role=" + this.role + ", level=" + this.level + ", credits=" + this.credits + ", email='" + this.email + '\'' + ", encryptPwd='" + this.encryptPwd + '\'' + '}';
    }
}
