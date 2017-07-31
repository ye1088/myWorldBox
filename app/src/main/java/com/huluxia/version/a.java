package com.huluxia.version;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ConfigInfo */
public class a extends com.huluxia.module.a implements Parcelable {
    public static final Creator<a> CREATOR = new 1();
    public int kroot;
    public int newUpdate;
    public int search;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.newUpdate);
        dest.writeInt(this.kroot);
        dest.writeInt(this.search);
    }

    protected a(Parcel in) {
        super(in);
        this.newUpdate = in.readInt();
        this.kroot = in.readInt();
        this.search = in.readInt();
    }
}
