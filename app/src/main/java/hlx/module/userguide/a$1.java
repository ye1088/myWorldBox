package hlx.module.userguide;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: UserGuideInfo */
class a$1 implements Creator<a> {
    a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bN(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return nu(i);
    }

    public a bN(Parcel source) {
        return new a(source);
    }

    public a[] nu(int size) {
        return new a[size];
    }
}
