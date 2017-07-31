package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class ScoreItem$1 implements Creator<ScoreItem> {
    ScoreItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ag(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aQ(i);
    }

    public ScoreItem ag(Parcel source) {
        return new ScoreItem(source);
    }

    public ScoreItem[] aQ(int size) {
        return new ScoreItem[size];
    }
}
