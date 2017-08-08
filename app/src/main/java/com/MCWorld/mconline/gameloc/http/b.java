package com.MCWorld.mconline.gameloc.http;

import android.os.Parcel;

/* compiled from: BaseMoreInfo */
public abstract class b extends a {
    public int more;
    public int start;

    public b(Parcel source) {
        super(source);
        this.more = source.readInt();
        this.start = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.more);
        dest.writeInt(this.start);
    }

    public String toString() {
        return "[more = " + this.more + ", start = " + this.start + " ] + " + super.toString();
    }
}
