package com.MCWorld.version;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: VersionInfo */
class e$1 implements Creator<e> {
    e$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bw(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return lz(i);
    }

    public e bw(Parcel source) {
        return new e(source);
    }

    public e[] lz(int size) {
        return new e[size];
    }
}
