package com.huluxia.module.news;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: CheckGoodGame */
public class a extends com.huluxia.module.a {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aP(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jC(i);
        }

        public a aP(Parcel source) {
            return new a(source);
        }

        public a[] jC(int size) {
            return new a[size];
        }
    };
    public int isopen;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.isopen);
    }

    protected a(Parcel in) {
        super(in);
        this.isopen = in.readInt();
    }
}
