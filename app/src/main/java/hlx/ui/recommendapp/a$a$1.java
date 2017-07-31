package hlx.ui.recommendapp;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import hlx.ui.recommendapp.a.a;

/* compiled from: RecommendAppMoreInfo */
class a$a$1 implements Creator<a> {
    a$a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bV(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return nM(i);
    }

    public a bV(Parcel source) {
        return new a(source);
    }

    public a[] nM(int size) {
        return new a[size];
    }
}
