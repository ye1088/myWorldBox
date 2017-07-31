package hlx.ui.recommendapp;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: RecommendAppMoreInfo */
class a$1 implements Creator<a> {
    a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bU(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return nL(i);
    }

    public a bU(Parcel source) {
        return new a(source);
    }

    public a[] nL(int size) {
        return new a[size];
    }
}
