package com.huluxia.module.profile;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: AddPhotoInfo */
public class a extends com.huluxia.module.a {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bc(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jP(i);
        }

        public a bc(Parcel source) {
            return new a(source);
        }

        public a[] jP(int size) {
            return new a[size];
        }
    };
    public long photoId;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.photoId);
    }

    protected a(Parcel in) {
        super(in);
        this.photoId = in.readLong();
    }
}
