package com.huluxia.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: LoginUserInfo */
class g$1 implements Creator<g> {
    g$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return e(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return M(i);
    }

    public g e(Parcel in) {
        return new g(in);
    }

    public g[] M(int size) {
        return new g[size];
    }
}
