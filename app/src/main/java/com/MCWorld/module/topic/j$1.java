package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: TopicDetailInfo */
class j$1 implements Creator<j> {
    j$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bp(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return kd(i);
    }

    public j bp(Parcel source) {
        return new j(source);
    }

    public j[] kd(int size) {
        return new j[size];
    }
}
