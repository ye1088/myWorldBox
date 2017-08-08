package com.MCWorld.widget.photowall;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.MCWorld.widget.photowall.PhotoWall.b;

class PhotoWall$b$1 implements Creator<b> {
    PhotoWall$b$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bA(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return mi(i);
    }

    public b bA(Parcel source) {
        return new b(source);
    }

    public b[] mi(int size) {
        return new b[size];
    }
}
