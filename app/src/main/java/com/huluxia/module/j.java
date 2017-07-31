package com.huluxia.module;

import android.os.Parcel;

/* compiled from: LongMoreInfo */
public abstract class j extends a {
    public int more;
    public long start;

    public j(Parcel source) {
        super(source);
        this.more = source.readInt();
        this.start = source.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.more);
        dest.writeLong(this.start);
    }

    public String toString() {
        return "[more = " + this.more + ", start = " + this.start + " ] + " + super.toString();
    }
}
