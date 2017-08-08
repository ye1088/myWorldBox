package com.MCWorld.module.news;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/* compiled from: News */
public class c implements Parcelable {
    public static final Creator<c> CREATOR = new 1();
    public int cmtCount;
    public String coverType;
    public ArrayList<String> covers = new ArrayList();
    public int imgCount;
    public long infoId;
    public long publishTime;
    public String tag;
    public String title;
    public String uri;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.infoId);
        dest.writeStringList(this.covers);
        dest.writeInt(this.cmtCount);
        dest.writeInt(this.imgCount);
        dest.writeLong(this.publishTime);
        dest.writeString(this.coverType);
        dest.writeString(this.tag);
        dest.writeString(this.uri);
        dest.writeString(this.title);
    }

    protected c(Parcel in) {
        this.infoId = in.readLong();
        this.covers = in.createStringArrayList();
        this.cmtCount = in.readInt();
        this.imgCount = in.readInt();
        this.publishTime = in.readLong();
        this.coverType = in.readString();
        this.tag = in.readString();
        this.uri = in.readString();
        this.title = in.readString();
    }
}
