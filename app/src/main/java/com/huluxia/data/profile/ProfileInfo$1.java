package com.huluxia.data.profile;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class ProfileInfo$1 implements Creator<ProfileInfo> {
    ProfileInfo$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return F(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return ap(i);
    }

    public ProfileInfo F(Parcel source) {
        return new ProfileInfo(source);
    }

    public ProfileInfo[] ap(int size) {
        return new ProfileInfo[size];
    }
}
