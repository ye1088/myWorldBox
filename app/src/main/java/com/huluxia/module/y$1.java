package com.huluxia.module;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: StudioCheckInfo */
class y$1 implements Creator<y> {
    y$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aJ(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jn(i);
    }

    public y aJ(Parcel source) {
        return new y(source);
    }

    public y[] jn(int size) {
        return new y[size];
    }
}
