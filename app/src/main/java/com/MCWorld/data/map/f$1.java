package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: MapRankingInfo */
class f$1 implements Creator<f> {
    f$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return x(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ah(i);
    }

    public f x(Parcel source) {
        return new f(source);
    }

    public f[] ah(int size) {
        return new f[size];
    }
}
