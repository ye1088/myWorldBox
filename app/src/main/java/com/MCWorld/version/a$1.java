package com.MCWorld.version;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: ConfigInfo */
class a$1 implements Creator<a> {
    a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bu(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return lx(i);
    }

    public a bu(Parcel source) {
        return new a(source);
    }

    public a[] lx(int size) {
        return new a[size];
    }
}
