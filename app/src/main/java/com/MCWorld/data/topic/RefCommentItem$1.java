package com.MCWorld.data.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class RefCommentItem$1 implements Creator<RefCommentItem> {
    RefCommentItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return af(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return aP(i);
    }

    public RefCommentItem af(Parcel in) {
        return new RefCommentItem(in);
    }

    public RefCommentItem[] aP(int size) {
        return new RefCommentItem[size];
    }
}
