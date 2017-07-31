package com.huluxia.mconline.gameloc.http;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: RoomsInfo */
class h$1 implements Creator<h> {
    h$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ay(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return hO(i);
    }

    public h ay(Parcel source) {
        return new h(source);
    }

    public h[] hO(int size) {
        return new h[size];
    }
}
