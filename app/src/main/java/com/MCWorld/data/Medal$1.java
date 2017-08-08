package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class Medal$1 implements Creator<Medal> {
    Medal$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return f(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return N(i);
    }

    public Medal f(Parcel source) {
        return new Medal(source);
    }

    public Medal[] N(int size) {
        return new Medal[size];
    }
}
