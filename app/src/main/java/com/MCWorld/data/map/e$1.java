package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: MapDetailInfo */
class e$1 implements Creator<e> {
    e$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return u(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ae(i);
    }

    public e u(Parcel source) {
        return new e(source);
    }

    public e[] ae(int size) {
        return new e[size];
    }
}
