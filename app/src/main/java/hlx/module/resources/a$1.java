package hlx.module.resources;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: MapCateItem */
class a$1 implements Creator<a> {
    a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bH(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return no(i);
    }

    public a bH(Parcel in) {
        return new a(in);
    }

    public a[] no(int size) {
        return new a[size];
    }
}
