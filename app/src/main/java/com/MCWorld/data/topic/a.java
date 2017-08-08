package com.MCWorld.data.topic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* compiled from: StudioInfo */
public class a implements Parcelable {
    public static final Creator<a> CREATOR = new 1();
    public String description;
    public String icon;
    public int id;
    public long integral;
    public int ismedal;
    public String name;
    public String qq;
    public String qqgroup;
    public List<a> studioUserInfos;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.qq);
        dest.writeString(this.qqgroup);
        dest.writeString(this.description);
        dest.writeInt(this.ismedal);
        dest.writeString(this.icon);
        dest.writeLong(this.integral);
        dest.writeTypedList(this.studioUserInfos);
    }

    protected a(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.qq = in.readString();
        this.qqgroup = in.readString();
        this.description = in.readString();
        this.ismedal = in.readInt();
        this.icon = in.readString();
        this.integral = in.readLong();
        this.studioUserInfos = in.createTypedArrayList(a.CREATOR);
    }
}
