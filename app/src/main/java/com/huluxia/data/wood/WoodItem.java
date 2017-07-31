package com.huluxia.data.wood;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class WoodItem implements Parcelable, Serializable, Comparable<WoodItem> {
    public static final Creator<WoodItem> CREATOR = new 1();
    public String date;
    public String imgUrl = "";
    public long mapId = -1;
    public String name;
    public String path;
    public long postId = -1;
    public int state = -1;
    public String ver = "";

    public WoodItem(Parcel source) {
        this.name = source.readString();
        this.path = source.readString();
        this.date = source.readString();
        this.state = source.readInt();
        this.ver = source.readString();
        this.mapId = source.readLong();
        this.postId = source.readLong();
        this.imgUrl = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeString(this.date);
        dest.writeInt(this.state);
        dest.writeString(this.ver);
        dest.writeLong(this.mapId);
        dest.writeLong(this.postId);
        dest.writeString(this.imgUrl);
    }

    public WoodItem(WoodItem old) {
        this.name = old.name;
        this.path = old.path;
        this.date = old.date;
        this.state = old.state;
    }

    public WoodItem(String paraName, String paraPath) {
        this.name = paraName;
        this.path = paraPath;
    }

    public WoodItem(String paraName, String paraPath, String paraDate, int paraState, String version) {
        this.name = paraName;
        this.path = paraPath;
        this.date = paraDate;
        this.state = paraState;
        this.ver = version;
    }

    public WoodItem(String paraName, String paraPath, String paraDate, int paraState, String version, long map_id, long post_id, String imgurl) {
        this.name = paraName;
        this.path = paraPath;
        this.date = paraDate;
        this.state = paraState;
        this.ver = version;
        this.mapId = map_id;
        this.postId = post_id;
        this.imgUrl = imgurl;
    }

    public int compareTo(WoodItem o) {
        return this.name.compareTo(o.name);
    }
}
