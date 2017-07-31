package com.huluxia.mconline.gameloc.http;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: RoomCreateItem */
class f$1 implements Creator<f> {
    f$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aw(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return hM(i);
    }

    public f aw(Parcel source) {
        return new f(source);
    }

    public f[] hM(int size) {
        return new f[size];
    }
}
