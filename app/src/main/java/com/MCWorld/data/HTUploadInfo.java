package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.a;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class HTUploadInfo extends a implements Parcelable, Serializable {
    public static final Creator<HTUploadInfo> CREATOR = new 1();
    private static final long serialVersionUID = 642497099987742948L;
    private String fid;
    private String url;

    public HTUploadInfo(JSONObject obj) throws JSONException {
        if (!obj.isNull("fid")) {
            this.fid = obj.getString("fid");
        }
        if (!obj.isNull("url")) {
            this.url = obj.getString("url");
        }
    }

    public String getFid() {
        return this.fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String toString() {
        return "HTUploadInfo{fid='" + this.fid + '\'' + ", url='" + this.url + '\'' + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fid);
        dest.writeString(this.url);
    }

    protected HTUploadInfo(Parcel in) {
        this.fid = in.readString();
        this.url = in.readString();
    }
}
