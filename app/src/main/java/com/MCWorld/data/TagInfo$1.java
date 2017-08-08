package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class TagInfo$1 implements Creator<TagInfo> {
    TagInfo$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return j(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return S(i);
    }

    public TagInfo j(Parcel source) {
        return new TagInfo(source);
    }

    public TagInfo[] S(int size) {
        return new TagInfo[size];
    }
}
