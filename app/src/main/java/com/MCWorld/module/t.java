package com.MCWorld.module;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: ResourceUploadInfo */
public class t extends a implements Parcelable {
    public static final Creator<t> CREATOR = new Creator<t>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aI(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jk(i);
        }

        public t[] jk(int size) {
            return new t[size];
        }

        public t aI(Parcel source) {
            return new t(source);
        }
    };
    public String url;

    public t(Parcel source) {
        super(source);
        this.url = source.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.url);
    }
}
