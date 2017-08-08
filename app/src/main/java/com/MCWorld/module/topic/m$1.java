package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: ZoneCategory */
class m$1 implements Creator<m> {
    m$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return br(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return kg(i);
    }

    public m br(Parcel source) {
        return new m(source);
    }

    public m[] kg(int size) {
        return new m[size];
    }
}
