package com.huluxia.data.studio;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.b;
import java.util.List;

/* compiled from: StudioMonthRankInfo */
public class f extends b {
    public static final Creator<f> CREATOR = new Creator<f>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return Y(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aI(i);
        }

        public f Y(Parcel source) {
            return new f(source);
        }

        public f[] aI(int size) {
            return new f[size];
        }
    };
    public List<a> list;

    /* compiled from: StudioMonthRankInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return Z(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return aJ(i);
            }

            public a Z(Parcel source) {
                return new a(source);
            }

            public a[] aJ(int size) {
                return new a[size];
            }
        };
        public int integral;
        public String name;
        public int rank;
        public int sid;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.sid);
            dest.writeString(this.name);
            dest.writeInt(this.rank);
            dest.writeInt(this.integral);
        }

        protected a(Parcel in) {
            this.sid = in.readInt();
            this.name = in.readString();
            this.rank = in.readInt();
            this.integral = in.readInt();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.list);
    }

    protected f(Parcel in) {
        super(in);
        this.list = in.createTypedArrayList(a.CREATOR);
    }
}
