package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: TopicInfo */
class e$1 implements Creator<e> {
    e$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return al(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aV(i);
    }

    public e al(Parcel source) {
        return new e(source);
    }

    public e[] aV(int size) {
        return new e[size];
    }
}
