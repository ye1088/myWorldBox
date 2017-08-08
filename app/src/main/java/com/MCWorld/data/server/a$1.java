package com.MCWorld.data.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: ServerInfo */
class a$1 implements Creator<a> {
    a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return M(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aw(i);
    }

    public a M(Parcel source) {
        return new a(source);
    }

    public a[] aw(int size) {
        return new a[size];
    }
}
