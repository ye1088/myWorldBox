package com.huluxia.data.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class MapItem$1 implements Creator<MapItem> {
    MapItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return v(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return af(i);
    }

    public MapItem v(Parcel in) {
        return new MapItem(in);
    }

    public MapItem[] af(int size) {
        return new MapItem[size];
    }
}
