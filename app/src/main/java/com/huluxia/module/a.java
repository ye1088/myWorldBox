package com.huluxia.module;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: BaseInfo */
public abstract class a implements Parcelable {
    public int code;
    public String msg;
    public int status;
    public String title;

    public a(Parcel source) {
        this();
        this.status = source.readInt();
        this.msg = source.readString();
        this.code = source.readInt();
        this.title = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.msg);
        dest.writeInt(this.code);
        dest.writeString(this.title);
    }

    public String toString() {
        return "[status = " + this.status + ", msg = " + this.msg + " ]";
    }

    public boolean isSucc() {
        return this.status == 1;
    }

    public static boolean isDataSucc(a info) {
        return info != null && info.status == 1;
    }
}
