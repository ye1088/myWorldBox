package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: BbsZoneSubCategory */
class c$1 implements Creator<c> {
    c$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bj(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jX(i);
    }

    public c bj(Parcel source) {
        return new c(source);
    }

    public c[] jX(int size) {
        return new c[size];
    }
}
