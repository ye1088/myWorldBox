package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: SessionInfo */
class k$1 implements Creator<k> {
    k$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return h(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return Q(i);
    }

    public k h(Parcel in) {
        return new k(in);
    }

    public k[] Q(int size) {
        return new k[size];
    }
}
