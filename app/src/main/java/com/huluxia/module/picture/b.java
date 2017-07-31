package com.huluxia.module.picture;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.framework.base.utils.UtilsFunction;

/* compiled from: PictureUnit */
public class b implements Parcelable {
    public static final Creator<b> CREATOR = new 1();
    public long createDate;
    public String fid;
    public long id;
    public String localPath;
    public String name;
    public long size;
    public String url;

    public b(long id, String localPath, long createDate, String name, long size) {
        this.id = id;
        this.localPath = localPath;
        this.createDate = createDate;
        this.name = name;
        this.size = size;
    }

    public b(Parcel source) {
        this.id = source.readLong();
        this.fid = source.readString();
        this.url = source.readString();
        this.localPath = source.readString();
        this.createDate = source.readLong();
        this.name = source.readString();
        this.size = source.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.fid);
        dest.writeString(this.url);
        dest.writeString(this.localPath);
        dest.writeLong(this.createDate);
        dest.writeString(this.name);
        dest.writeLong(this.size);
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof b)) {
            return super.equals(o);
        }
        b unit = (b) o;
        if (unit.id == this.id || ((!UtilsFunction.empty(unit.localPath) && unit.localPath.equals(this.localPath)) || (!UtilsFunction.empty(unit.url) && unit.url.equals(this.url)))) {
            return true;
        }
        return false;
    }
}
