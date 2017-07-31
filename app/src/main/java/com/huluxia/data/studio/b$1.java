package com.huluxia.data.studio;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: StudioAnnounceInfo */
class b$1 implements Creator<b> {
    b$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return S(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aC(i);
    }

    public b S(Parcel source) {
        return new b(source);
    }

    public b[] aC(int size) {
        return new b[size];
    }
}
