package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: ZoneCategoryItem */
class n$1 implements Creator<n> {
    n$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bs(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return kh(i);
    }

    public n bs(Parcel source) {
        return new n(source);
    }

    public n[] kh(int size) {
        return new n[size];
    }
}
