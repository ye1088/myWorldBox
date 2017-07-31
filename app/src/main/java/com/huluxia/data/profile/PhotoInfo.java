package com.huluxia.data.profile;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import com.huluxia.widget.banner.a;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class PhotoInfo extends a implements Parcelable, Serializable {
    public static final Creator<PhotoInfo> CREATOR = new 1();
    private static final long serialVersionUID = -7304609834225435115L;
    @SerializedName("id")
    private long fid;

    public PhotoInfo(long fid, String url) {
        this.fid = fid;
        this.url = url;
    }

    public PhotoInfo(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.fid = obj.optLong("id");
            this.url = obj.optString("url");
        }
    }

    public PhotoInfo(Parcel in) {
        this.fid = in.readLong();
        this.url = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.fid);
        dest.writeString(this.url);
    }

    public long getFid() {
        return this.fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
