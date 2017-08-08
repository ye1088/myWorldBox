package com.MCWorld.data.category;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class Daren$1 implements Creator<Daren> {
    Daren$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return n(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return X(i);
    }

    public Daren n(Parcel in) {
        return new Daren(in);
    }

    public Daren[] X(int size) {
        return new Daren[size];
    }
}
