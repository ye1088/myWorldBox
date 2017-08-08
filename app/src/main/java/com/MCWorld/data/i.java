package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tencent.mm.sdk.plugin.BaseProfile;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: QQInfo */
public class i implements Parcelable {
    public static final Creator<i> CREATOR = new 1();
    public String figureurl_qq_1;
    public String nickname;

    public i(JSONObject obj) throws JSONException {
        this.nickname = obj.optString(BaseProfile.COL_NICKNAME);
        this.figureurl_qq_1 = obj.getString("figureurl_qq_1");
    }

    public i(Parcel source) {
        this.nickname = source.readString();
        this.figureurl_qq_1 = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickname);
    }

    public String toString() {
        return "SessionInfo{, nick='" + this.nickname + '\'' + '}';
    }
}
