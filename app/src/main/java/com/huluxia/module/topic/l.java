package com.huluxia.module.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;

/* compiled from: VcodeInfo */
public class l extends a implements Parcelable {
    public static final Creator<l> CREATOR = new 1();
    public String url;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.url);
    }

    protected l(Parcel in) {
        super(in);
        this.url = in.readString();
    }
}
