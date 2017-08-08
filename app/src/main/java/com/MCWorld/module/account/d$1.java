package com.MCWorld.module.account;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: SysMsgs */
class d$1 implements Creator<d> {
    d$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aN(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jA(i);
    }

    public d aN(Parcel source) {
        return new d(source);
    }

    public d[] jA(int size) {
        return new d[size];
    }
}
