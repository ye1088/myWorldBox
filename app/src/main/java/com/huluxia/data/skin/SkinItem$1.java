package com.huluxia.data.skin;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class SkinItem$1 implements Creator<SkinItem> {
    SkinItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return P(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return az(i);
    }

    public SkinItem P(Parcel in) {
        return new SkinItem(in);
    }

    public SkinItem[] az(int size) {
        return new SkinItem[size];
    }
}
