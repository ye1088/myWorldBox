package hlx.module.userguide;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import hlx.module.userguide.a.a;

/* compiled from: UserGuideInfo */
class a$a$1 implements Creator<a> {
    a$a$1() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return bO(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return nv(i);
    }

    public a bO(Parcel source) {
        return new a(source);
    }

    public a[] nv(int size) {
        return new a[0];
    }
}
