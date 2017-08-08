package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: QQInfo */
class i$1 implements Creator<i> {
    i$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return g(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return O(i);
    }

    public i g(Parcel in) {
        return new i(in);
    }

    public i[] O(int size) {
        return new i[size];
    }
}
