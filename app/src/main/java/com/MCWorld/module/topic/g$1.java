package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: CreatePowerInfo */
class g$1 implements Creator<g> {
    g$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bn(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return kb(i);
    }

    public g bn(Parcel source) {
        return new g(source);
    }

    public g[] kb(int size) {
        return new g[size];
    }
}
