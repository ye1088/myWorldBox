package com.huluxia.data.map;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;

/* compiled from: MapDetailInfo */
public class e extends a {
    public static final Creator<e> CREATOR = new 1();
    public f.a mapDetailInfo;

    public e(Parcel source) {
        super(source);
        this.mapDetailInfo = (f.a) source.readParcelable(null);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.mapDetailInfo, 0);
    }
}
