package com.huluxia.module.picture;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: PictureUnit */
class b$1 implements Creator<b> {
    b$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bb(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return jO(i);
    }

    public b bb(Parcel source) {
        return new b(source);
    }

    public b[] jO(int size) {
        return new b[size];
    }
}
