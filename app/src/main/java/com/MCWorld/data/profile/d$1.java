package com.MCWorld.data.profile;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: StudioAuditInfo */
class d$1 implements Creator<d> {
    d$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return I(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return as(i);
    }

    public d I(Parcel source) {
        return new d(source);
    }

    public d[] as(int size) {
        return new d[size];
    }
}
