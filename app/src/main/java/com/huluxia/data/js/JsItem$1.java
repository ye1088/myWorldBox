package com.huluxia.data.js;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class JsItem$1 implements Creator<JsItem> {
    JsItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return r(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ab(i);
    }

    public JsItem r(Parcel in) {
        return new JsItem(in);
    }

    public JsItem[] ab(int size) {
        return new JsItem[size];
    }
}
