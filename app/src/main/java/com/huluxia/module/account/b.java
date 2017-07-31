package com.huluxia.module.account;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;

/* compiled from: AuthInfo */
public class b extends a {
    public static final Creator<b> CREATOR = new Creator<b>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aL(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jy(i);
        }

        public b aL(Parcel source) {
            return new b(source);
        }

        public b[] jy(int size) {
            return new b[size];
        }
    };
    public String tmp_key;
    public long uuid;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.tmp_key);
        dest.writeLong(this.uuid);
    }

    protected b(Parcel in) {
        super(in);
        this.tmp_key = in.readString();
        this.uuid = in.readLong();
    }
}
