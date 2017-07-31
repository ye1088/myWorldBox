package com.huluxia.module.profile;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: FriendshipInfo */
class c$1 implements Creator<c> {
    c$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bd(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jQ(i);
    }

    public c bd(Parcel source) {
        return new c(source);
    }

    public c[] jQ(int size) {
        return new c[size];
    }
}
