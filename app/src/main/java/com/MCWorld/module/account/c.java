package com.MCWorld.module.account;

import android.os.Parcel;

import com.MCWorld.data.i;
import com.MCWorld.module.a;

/* compiled from: PwdInfo */
public class c extends a {
    public static final Creator<c> CREATOR = new 1();
    public int countTime;
    public int isNext;
    public i qqinfo;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.isNext);
        dest.writeParcelable(this.qqinfo, 0);
        dest.writeInt(this.countTime);
    }

    protected c(Parcel in) {
        super(in);
        this.isNext = in.readInt();
        this.qqinfo = (i) in.readParcelable(i.class.getClassLoader());
        this.countTime = in.readInt();
    }
}
