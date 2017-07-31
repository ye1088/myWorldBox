package com.huluxia.data.message;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class UserMsgItem$1 implements Creator<UserMsgItem> {
    UserMsgItem$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return D(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return an(i);
    }

    public UserMsgItem D(Parcel source) {
        return new UserMsgItem(source);
    }

    public UserMsgItem[] an(int size) {
        return new UserMsgItem[size];
    }
}
