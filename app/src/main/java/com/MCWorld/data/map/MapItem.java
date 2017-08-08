package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class MapItem implements Parcelable, Serializable {
    public static final Creator<MapItem> CREATOR = new 1();
    private static final long serialVersionUID = -2371516191633351006L;
    public String creatTime;
    public String date;
    public long id;
    public String md5;
    public String name;
    public String photo;
    public long postid;
    public boolean showDate = false;
    public String subVersion;
    public String url;
    public String version;

    public MapItem(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.id = obj.optLong("id");
            this.postid = obj.optLong("postid");
            this.name = obj.optString("name");
            this.url = obj.optString("downUrl");
            this.photo = obj.optString("icon");
            this.creatTime = obj.optString("createTime");
            this.md5 = obj.optString("md5");
            this.version = obj.optString("version");
            this.subVersion = obj.optString("subVersion");
        }
    }

    public MapItem(Parcel source) {
        this.id = source.readLong();
        this.name = source.readString();
        this.url = source.readString();
        this.photo = source.readString();
        this.creatTime = source.readString();
        this.md5 = source.readString();
        this.version = source.readString();
        this.subVersion = source.readString();
        this.postid = source.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "MapItem{id=" + this.id + "name=" + this.name + ", url='" + this.url + '\'' + ", photo='" + this.photo + '\'' + ", creatTime=" + this.creatTime + ", md5=" + this.md5 + ", version=" + this.version + ", postid='" + this.postid + '\'' + '}';
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.photo);
        dest.writeString(this.creatTime);
        dest.writeString(this.md5);
        dest.writeString(this.version);
        dest.writeLong(this.postid);
    }

    public MapItem(MapItem old) {
        this.id = old.id;
        this.name = old.name;
        this.url = old.url;
        this.photo = old.photo;
        this.creatTime = old.creatTime;
        this.md5 = old.md5;
        this.version = old.version;
        this.postid = old.postid;
    }

    public MapItem(long id, String name, String url, String photo, String creatTime, String md5, String version, long postid) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.photo = photo;
        this.creatTime = creatTime;
        this.md5 = md5;
        this.version = version;
        this.postid = postid;
    }

    public MapItem(long id, String name, String url, String photo, String creatTime, String md5, String version, String subVersion, long postid) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.photo = photo;
        this.creatTime = creatTime;
        this.md5 = md5;
        this.version = version;
        this.subVersion = subVersion;
        this.postid = postid;
    }
}
