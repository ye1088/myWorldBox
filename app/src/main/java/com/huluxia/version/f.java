package com.huluxia.version;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;

/* compiled from: VersionInfos */
public class f extends a implements Parcelable {
    public static final Creator<f> CREATOR = new Creator<f>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bx(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return lA(i);
        }

        public f bx(Parcel source) {
            return new f(source);
        }

        public f[] lA(int size) {
            return new f[size];
        }
    };
    public e versionInfo;

    public f(Parcel source) {
        this();
        this.versionInfo = (e) source.readParcelable(e.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.versionInfo, 0);
    }
}
