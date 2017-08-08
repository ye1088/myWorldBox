package com.MCWorld.framework.base.utils.zip;

import android.os.Parcel;
import android.os.Parcelable;

public class Zippee implements Parcelable {
    public static final Creator<Zippee> CREATOR = new Creator<Zippee>() {
        public Zippee createFromParcel(Parcel source) {
            return new Zippee(source);
        }

        public Zippee[] newArray(int size) {
            return new Zippee[size];
        }
    };
    public String file;
    public ZipMetadata metadata;

    public Zippee(Parcel source) {
        this.file = source.readString();
        this.metadata = (ZipMetadata) source.readParcelable(ZipMetadata.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.file);
        dest.writeParcelable(this.metadata, 0);
    }
}
