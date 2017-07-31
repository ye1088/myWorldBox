package com.huluxia.mconline.gameloc.http;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: RoomInfo */
class g$1 implements Creator<g> {
    g$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ax(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return hN(i);
    }

    public g ax(Parcel source) {
        return new g(source);
    }

    public g[] hN(int size) {
        return new g[size];
    }
}
