package com.huluxia.module.news;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: News */
class c$1 implements Creator<c> {
    c$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aQ(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jD(i);
    }

    public c aQ(Parcel source) {
        return new c(source);
    }

    public c[] jD(int size) {
        return new c[size];
    }
}
