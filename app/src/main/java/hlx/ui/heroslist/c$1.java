package hlx.ui.heroslist;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: HeroRankInfo */
class c$1 implements Creator<c> {
    c$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bP(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return nw(i);
    }

    public c bP(Parcel source) {
        return new c(source);
    }

    public c[] nw(int size) {
        return new c[size];
    }
}
