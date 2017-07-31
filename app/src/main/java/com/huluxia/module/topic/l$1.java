package com.huluxia.module.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: VcodeInfo */
class l$1 implements Creator<l> {
    l$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bq(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return kf(i);
    }

    public l bq(Parcel source) {
        return new l(source);
    }

    public l[] kf(int size) {
        return new l[size];
    }
}
