package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class ExtJson$1 implements Creator<ExtJson> {
    ExtJson$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ae(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aO(i);
    }

    public ExtJson ae(Parcel source) {
        return new ExtJson(source);
    }

    public ExtJson[] aO(int size) {
        return new ExtJson[size];
    }
}
