package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class MapCateItem$1 implements Creator<MapCateItem> {
    MapCateItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return t(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ad(i);
    }

    public MapCateItem t(Parcel in) {
        return new MapCateItem(in);
    }

    public MapCateItem[] ad(int size) {
        return new MapCateItem[size];
    }
}
