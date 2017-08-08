package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MapPathItem implements Parcelable, Serializable, Comparable<MapPathItem> {
    public static final Creator<MapPathItem> CREATOR = new 1();
    public int fileType;
    public String name;
    public String path;

    public MapPathItem(Parcel source) {
        this.name = source.readString();
        this.path = source.readString();
        this.fileType = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeInt(this.fileType);
    }

    public MapPathItem(MapPathItem old) {
        this.name = old.name;
        this.path = old.path;
        this.fileType = old.fileType;
    }

    public MapPathItem(String paraName, String paraPath) {
        this.name = paraName;
        this.path = paraPath;
    }

    public int compareTo(MapPathItem o) {
        return this.name.compareTo(o.name);
    }
}
