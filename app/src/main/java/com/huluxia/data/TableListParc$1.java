package com.huluxia.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class TableListParc$1 implements Creator<TableListParc> {
    TableListParc$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return i(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return R(i);
    }

    public TableListParc i(Parcel in) {
        return new TableListParc(in, null);
    }

    public TableListParc[] R(int size) {
        return new TableListParc[size];
    }
}
