package hlx.ui.mapseed;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: SeedMoreInfo */
class b$1 implements Creator<b> {
    b$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bS(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return nI(i);
    }

    public b bS(Parcel source) {
        return new b(source);
    }

    public b[] nI(int size) {
        return new b[size];
    }
}
