package com.MCWorld.data.studio;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.b;
import java.util.List;

/* compiled from: StudioDownRankInfo */
public class e extends b {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return W(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aG(i);
        }

        public e W(Parcel source) {
            return new e(source);
        }

        public e[] aG(int size) {
            return new e[size];
        }
    };
    public List<a> mapList;

    /* compiled from: StudioDownRankInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return X(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return aH(i);
            }

            public a X(Parcel source) {
                return new a(source);
            }

            public a[] aH(int size) {
                return new a[size];
            }
        };
        public com.MCWorld.data.map.f.a info;
        public int rank;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.info, flags);
            dest.writeInt(this.rank);
        }

        protected a(Parcel in) {
            this.info = (com.MCWorld.data.map.f.a) in.readParcelable(com.MCWorld.data.map.f.a.class.getClassLoader());
            this.rank = in.readInt();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.mapList);
    }

    protected e(Parcel in) {
        super(in);
        this.mapList = in.createTypedArrayList(a.CREATOR);
    }
}
