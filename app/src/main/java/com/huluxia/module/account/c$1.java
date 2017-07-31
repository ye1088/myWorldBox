package com.huluxia.module.account;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: PwdInfo */
class c$1 implements Creator<c> {
    c$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aM(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jz(i);
    }

    public c aM(Parcel source) {
        return new c(source);
    }

    public c[] jz(int size) {
        return new c[size];
    }
}
