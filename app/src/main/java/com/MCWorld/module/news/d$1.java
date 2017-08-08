package com.MCWorld.module.news;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: NewsCommentItem */
class d$1 implements Creator<d> {
    d$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aR(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jE(i);
    }

    public d aR(Parcel source) {
        return new d(source);
    }

    public d[] jE(int size) {
        return new d[size];
    }
}
