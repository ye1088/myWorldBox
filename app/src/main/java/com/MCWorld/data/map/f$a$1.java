package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.MCWorld.data.map.f.a;

/* compiled from: MapRankingInfo */
class f$a$1 implements Creator<a> {
    f$a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return y(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ai(i);
    }

    public a y(Parcel source) {
        return new a(source);
    }

    public a[] ai(int size) {
        return new a[size];
    }
}
