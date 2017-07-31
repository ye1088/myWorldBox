package hlx.ui.mapseed;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import hlx.ui.mapseed.b.a;

/* compiled from: SeedMoreInfo */
class b$a$1 implements Creator<a> {
    b$a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bT(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return nJ(i);
    }

    public a bT(Parcel source) {
        return new a(source);
    }

    public a[] nJ(int size) {
        return new a[size];
    }
}
