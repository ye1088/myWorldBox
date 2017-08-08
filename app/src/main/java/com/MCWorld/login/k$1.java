package com.MCWorld.login;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: SessionInfo */
class k$1 implements Creator<k> {
    k$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ar(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return cR(i);
    }

    public k ar(Parcel in) {
        return new k(in);
    }

    public k[] cR(int size) {
        return new k[size];
    }
}
