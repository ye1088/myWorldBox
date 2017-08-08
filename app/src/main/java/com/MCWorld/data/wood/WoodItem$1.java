package com.MCWorld.data.wood;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class WoodItem$1 implements Creator<WoodItem> {
    WoodItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ap(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aZ(i);
    }

    public WoodItem ap(Parcel in) {
        return new WoodItem(in);
    }

    public WoodItem[] aZ(int size) {
        return new WoodItem[size];
    }
}
