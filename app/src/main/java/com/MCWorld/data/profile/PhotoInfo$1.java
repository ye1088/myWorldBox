package com.MCWorld.data.profile;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class PhotoInfo$1 implements Creator<PhotoInfo> {
    PhotoInfo$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return E(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ao(i);
    }

    public PhotoInfo E(Parcel source) {
        return new PhotoInfo(source);
    }

    public PhotoInfo[] ao(int size) {
        return new PhotoInfo[size];
    }
}
