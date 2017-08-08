package com.MCWorld.module.account;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.MCWorld.module.account.a.a;

/* compiled from: AccountModule */
class a$a$1 implements Creator<a> {
    a$a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aK(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jx(i);
    }

    public a aK(Parcel source) {
        return new a(source);
    }

    public a[] jx(int size) {
        return new a[size];
    }
}
