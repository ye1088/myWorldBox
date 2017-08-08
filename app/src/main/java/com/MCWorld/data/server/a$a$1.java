package com.MCWorld.data.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.MCWorld.data.server.a.a;

/* compiled from: ServerInfo */
class a$a$1 implements Creator<a> {
    a$a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return N(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ax(i);
    }

    public a N(Parcel source) {
        return new a(source);
    }

    public a[] ax(int size) {
        return new a[size];
    }
}
