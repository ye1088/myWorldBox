package com.MCWorld.module.profile;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: NickChangeNumInfo */
class e$1 implements Creator<e> {
    e$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bf(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jS(i);
    }

    public e bf(Parcel source) {
        return new e(source);
    }

    public e[] jS(int size) {
        return new e[size];
    }
}
