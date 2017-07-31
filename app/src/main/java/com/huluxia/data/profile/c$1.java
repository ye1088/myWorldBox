package com.huluxia.data.profile;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: Studio */
class c$1 implements Creator<c> {
    c$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return G(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aq(i);
    }

    public c G(Parcel source) {
        return new c(source);
    }

    public c[] aq(int size) {
        return new c[size];
    }
}
