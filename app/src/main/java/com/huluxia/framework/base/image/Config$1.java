package com.huluxia.framework.base.image;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class Config$1 implements Creator<Config> {
    Config$1() {
    }

    public Config createFromParcel(Parcel source) {
        return new Config(source);
    }

    public Config[] newArray(int size) {
        return new Config[size];
    }
}
