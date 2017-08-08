package hlx.ui.mapseed;

import android.os.Parcel;

/* compiled from: SeedDetatilMoreInfo */
public class a extends com.MCWorld.module.a {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bR(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return nH(i);
        }

        public a bR(Parcel source) {
            return new a(source);
        }

        public a[] nH(int size) {
            return new a[size];
        }
    };
    public hlx.ui.mapseed.b.a mapSeedInfo;

    public a(Parcel source) {
        super(source);
        this.mapSeedInfo = (hlx.ui.mapseed.b.a) source.readParcelable(null);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.mapSeedInfo, 0);
    }
}
