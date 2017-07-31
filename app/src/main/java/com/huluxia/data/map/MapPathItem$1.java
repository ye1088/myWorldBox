package com.huluxia.data.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class MapPathItem$1 implements Creator<MapPathItem> {
    MapPathItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return w(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ag(i);
    }

    public MapPathItem w(Parcel in) {
        return new MapPathItem(in);
    }

    public MapPathItem[] ag(int size) {
        return new MapPathItem[size];
    }
}
