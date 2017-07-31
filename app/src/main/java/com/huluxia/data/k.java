package com.huluxia.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: SessionInfo */
public class k extends a implements Parcelable {
    public static final Creator<k> CREATOR = new 1();
    public String _key;
    public int checkstatus;
    public i qqinfo;
    public int qqinfostatus;
    public String session_key;
    public g user;

    public k(String token, g userInfo) {
        this._key = token;
        this.user = userInfo;
    }

    public k(JSONObject obj) throws JSONException {
        this._key = obj.optString("_key");
        if (!obj.isNull("user")) {
            this.user = new g(obj.optJSONObject("user"));
        }
        this.checkstatus = obj.optInt("checkstatus", 0);
        this.qqinfostatus = obj.optInt("qqinfostatus", 0);
        if (!obj.isNull("qqinfo")) {
            this.qqinfo = new i(obj.optJSONObject("qqinfo"));
        }
        this.session_key = obj.optString("session_key");
    }

    public k(Parcel source) {
        this._key = source.readString();
        this.user = (g) source.readParcelable(g.class.getClassLoader());
        this.checkstatus = source.readInt();
        this.qqinfostatus = source.readInt();
        this.qqinfo = (i) source.readParcelable(i.class.getClassLoader());
        this.session_key = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._key);
        dest.writeParcelable(this.user, 0);
        dest.writeInt(this.checkstatus);
        dest.writeInt(this.qqinfostatus);
        dest.writeParcelable(this.qqinfo, 0);
        dest.writeString(this.session_key);
    }

    public String toString() {
        return "SessionInfo{token='" + this._key + '\'' + ", user='" + this.user + '\'' + '}';
    }

    public static k convertFromOld(l infoOld) {
        return new k(infoOld.key, new g(infoOld));
    }
}
