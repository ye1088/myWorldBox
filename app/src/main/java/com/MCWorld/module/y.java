package com.MCWorld.module;

import android.os.Parcel;

/* compiled from: StudioCheckInfo */
public class y extends a {
    public static final Creator<y> CREATOR = new 1();
    private int sid;

    public int getSid() {
        return this.sid;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.sid);
    }

    protected y(Parcel in) {
        super(in);
        this.sid = in.readInt();
    }
}
