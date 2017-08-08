package com.MCWorld.data.studio;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.MCWorld.data.studio.b.a;

/* compiled from: StudioAnnounceInfo */
class b$a$1 implements Creator<a> {
    b$a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return T(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aD(i);
    }

    public a T(Parcel source) {
        return new a(source);
    }

    public a[] aD(int size) {
        return new a[size];
    }
}
