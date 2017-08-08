package com.MCWorld.data.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class SimpleTopicItem$1 implements Creator<SimpleTopicItem> {
    SimpleTopicItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ah(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aR(i);
    }

    public SimpleTopicItem ah(Parcel in) {
        return new SimpleTopicItem(in);
    }

    public SimpleTopicItem[] aR(int size) {
        return new SimpleTopicItem[size];
    }
}
