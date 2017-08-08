package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tencent.mm.sdk.plugin.BaseProfile;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LoginUserInfo */
public class g implements Parcelable {
    public static final Creator<g> CREATOR = new 1();
    public int age;
    public String avatar;
    public long credits;
    public int gender;
    public int isgold;
    public int level;
    public String nick;
    public String number;
    public int role;
    public long userID;

    public g(l infoOld) {
        this.userID = infoOld.userID;
        this.nick = infoOld.nick;
        this.age = infoOld.age;
        this.gender = infoOld.gender;
        this.avatar = infoOld.avatar;
        this.role = infoOld.role;
        this.level = infoOld.level;
        this.credits = infoOld.credits;
        this.isgold = 0;
    }

    public g(JSONObject obj) throws JSONException {
        this.userID = obj.optLong("userID");
        this.nick = obj.optString("nick");
        this.age = obj.optInt("age");
        this.gender = obj.optInt("gender");
        this.avatar = obj.optString(BaseProfile.COL_AVATAR);
        this.role = obj.optInt("role");
        this.level = obj.optInt("level");
        this.credits = obj.optLong("credits", 0);
        this.number = obj.optString("number");
        this.isgold = obj.optInt("isgold");
    }

    public g(Parcel source) {
        this.userID = source.readLong();
        this.nick = source.readString();
        this.avatar = source.readString();
        this.age = source.readInt();
        this.gender = source.readInt();
        this.role = source.readInt();
        this.level = source.readInt();
        this.credits = source.readLong();
        this.number = source.readString();
        this.isgold = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.userID);
        dest.writeString(this.nick);
        dest.writeString(this.avatar);
        dest.writeInt(this.age);
        dest.writeInt(this.gender);
        dest.writeInt(this.role);
        dest.writeInt(this.level);
        dest.writeLong(this.credits);
        dest.writeString(this.number);
        dest.writeInt(this.isgold);
    }

    public String toString() {
        return "SessionInfo{, userID=" + this.userID + ", nick='" + this.nick + '\'' + ", avatar='" + this.avatar + '\'' + ", age=" + this.age + ", gender=" + this.gender + ", role=" + this.role + ", level=" + this.level + ", credits=" + this.credits + '}';
    }
}
