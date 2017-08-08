package com.MCWorld.data.topic;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.ui.profile.FriendListActivity;
import com.tencent.mm.sdk.plugin.BaseProfile;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class ScoreItem implements Parcelable, Serializable {
    public static final Creator<ScoreItem> CREATOR = new 1();
    private static final long serialVersionUID = 1282409462399148553L;
    private String avatar;
    private int isadmin;
    private long score;
    private String scoreTxt;
    private long userid;
    private String username;

    public ScoreItem(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.userid = obj.optLong(FriendListActivity.EXTRA_USER_ID);
            this.username = obj.optString("username");
            this.score = obj.optLong("score");
            this.scoreTxt = obj.optString("scoreTxt");
            this.isadmin = obj.optInt("isadmin");
            this.avatar = obj.optString(BaseProfile.COL_AVATAR);
        }
    }

    public ScoreItem(long i) {
        this.userid = 0;
        this.username = String.valueOf(i) + "user";
        this.score = i;
        this.scoreTxt = "测试";
        this.isadmin = 0;
    }

    public long getUserid() {
        return this.userid;
    }

    public String getUsername() {
        return this.username;
    }

    public long getScore() {
        return this.score;
    }

    public String getScoreTxt() {
        return this.scoreTxt;
    }

    public boolean isIsadmin() {
        return this.isadmin == 1;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.userid);
        dest.writeString(this.username);
        dest.writeLong(this.score);
        dest.writeString(this.scoreTxt);
        dest.writeInt(this.isadmin);
        dest.writeString(this.avatar);
    }

    protected ScoreItem(Parcel in) {
        this.userid = in.readLong();
        this.username = in.readString();
        this.score = in.readLong();
        this.scoreTxt = in.readString();
        this.isadmin = in.readInt();
        this.avatar = in.readString();
    }
}
