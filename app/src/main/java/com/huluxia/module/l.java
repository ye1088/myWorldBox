package com.huluxia.module;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: MapReviewAuthInfo */
public class l extends a {
    public static final Creator<l> CREATOR = new Creator<l>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aF(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jd(i);
        }

        public l aF(Parcel source) {
            return new l(source);
        }

        public l[] jd(int size) {
            return new l[size];
        }
    };
    public boolean ispower = false;

    public boolean hasPower() {
        return isSucc() && this.ispower;
    }

    protected l(Parcel in) {
        boolean z = false;
        super(in);
        if (in.readByte() != (byte) 0) {
            z = true;
        }
        this.ispower = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.ispower ? (byte) 1 : (byte) 0);
    }
}
