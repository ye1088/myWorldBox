package com.MCWorld.data.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: StudioInfo */
class a$1 implements Creator<a> {
    a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ai(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aS(i);
    }

    public a ai(Parcel source) {
        return new a(source);
    }

    public a[] aS(int size) {
        return new a[size];
    }
}
