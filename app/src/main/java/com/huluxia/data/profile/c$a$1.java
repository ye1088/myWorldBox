package com.huluxia.data.profile;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.data.profile.c.a;

/* compiled from: Studio */
class c$a$1 implements Creator<a> {
    c$a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return H(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ar(i);
    }

    public a H(Parcel source) {
        return new a(source);
    }

    public a[] ar(int size) {
        return new a[size];
    }
}
