package com.huluxia.version;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: VersionDbInfo */
class d$1 implements Creator<d> {
    d$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bv(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ly(i);
    }

    public d bv(Parcel source) {
        return new d(source);
    }

    public d[] ly(int size) {
        return new d[size];
    }
}
