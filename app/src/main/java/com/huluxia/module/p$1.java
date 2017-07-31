package com.huluxia.module;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: ProfileDbInfo */
class p$1 implements Creator<p> {
    p$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aG(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ji(i);
    }

    public p aG(Parcel source) {
        return new p(source);
    }

    public p[] ji(int size) {
        return new p[size];
    }
}
