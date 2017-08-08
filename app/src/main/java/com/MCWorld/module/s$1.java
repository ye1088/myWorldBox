package com.MCWorld.module;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: ResDbInfo */
class s$1 implements Creator<s> {
    s$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aH(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jj(i);
    }

    public s aH(Parcel source) {
        return new s(source);
    }

    public s[] jj(int size) {
        return new s[size];
    }
}
