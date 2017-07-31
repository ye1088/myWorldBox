package com.huluxia.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class HTUploadInfo$1 implements Creator<HTUploadInfo> {
    HTUploadInfo$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return d(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return L(i);
    }

    public HTUploadInfo d(Parcel source) {
        return new HTUploadInfo(source);
    }

    public HTUploadInfo[] L(int size) {
        return new HTUploadInfo[size];
    }
}
