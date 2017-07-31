package hlx.module.resources;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: ResourceCateInfo */
class b$1 implements Creator<b> {
    b$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bI(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return np(i);
    }

    public b bI(Parcel source) {
        return new b(source);
    }

    public b[] np(int size) {
        return new b[size];
    }
}
