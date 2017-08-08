package com.MCWorld.framework.base.utils.zip;

import android.os.Parcel;
import android.os.Parcelable;

public class ZipMetadata implements Parcelable {
    public static final Creator<ZipMetadata> CREATOR = new Creator<ZipMetadata>() {
        public ZipMetadata createFromParcel(Parcel source) {
            return new ZipMetadata(source);
        }

        public ZipMetadata[] newArray(int size) {
            return new ZipMetadata[size];
        }
    };
    public String desPath;
    public String secondaryDir;
    public String zipRootName;

    public ZipMetadata(Parcel source) {
        this.zipRootName = source.readString();
        this.desPath = source.readString();
        this.secondaryDir = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.zipRootName);
        dest.writeString(this.desPath);
        dest.writeString(this.secondaryDir);
    }
}
