package com.huluxia.module.account;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: UserMsgs */
class e$1 implements Creator<e> {
    e$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return aO(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jB(i);
    }

    public e aO(Parcel source) {
        return new e(source);
    }

    public e[] jB(int size) {
        return new e[size];
    }
}
