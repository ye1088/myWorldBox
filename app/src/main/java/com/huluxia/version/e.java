package com.huluxia.version;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: VersionInfo */
public class e implements Parcelable {
    public static final Creator<e> CREATOR = new 1();
    public String channel;
    public String content;
    public String md5;
    public String name;
    public String packname;
    public String patchmd5;
    public long patchsize;
    public String patchurl;
    public long size;
    public int updateType;
    public String url;
    public long versioncode;
    public String versionname;

    public e(Parcel source) {
        this();
        this.url = source.readString();
        this.patchurl = source.readString();
        this.size = source.readLong();
        this.patchsize = source.readLong();
        this.md5 = source.readString();
        this.patchmd5 = source.readString();
        this.versionname = source.readString();
        this.versioncode = source.readLong();
        this.content = source.readString();
        this.name = source.readString();
        this.packname = source.readString();
        this.channel = source.readString();
        this.name = source.readString();
        this.updateType = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.patchurl);
        dest.writeLong(this.size);
        dest.writeLong(this.patchsize);
        dest.writeString(this.md5);
        dest.writeString(this.patchmd5);
        dest.writeString(this.versionname);
        dest.writeLong(this.versioncode);
        dest.writeString(this.content);
        dest.writeString(this.name);
        dest.writeString(this.packname);
        dest.writeString(this.channel);
        dest.writeString(this.name);
        dest.writeInt(this.updateType);
    }

    public String toString() {
        return "VersionInfo{url=" + this.url + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        e versionInfo = (e) o;
        if (this.url.equals(versionInfo.url) && this.md5.equals(versionInfo.md5)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.url.hashCode();
    }
}
