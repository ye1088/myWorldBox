package com.huluxia.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: CloudIdInfo */
public class c implements Parcelable {
    public static final Creator<c> CREATOR = new Creator<c>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return c(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return I(i);
        }

        public c c(Parcel in) {
            return new c(in);
        }

        public c[] I(int size) {
            return new c[size];
        }
    };
    public String cloudid;
    public String devicecode;

    public c(String _devicecode, String _cloudid) {
        this.devicecode = _devicecode;
        this.cloudid = _cloudid;
    }

    public c(Parcel source) {
        this.devicecode = source.readString();
        this.cloudid = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.devicecode);
        dest.writeString(this.cloudid);
    }

    public String toString() {
        return "CloudIdInfo{devicecode='" + this.devicecode + '\'' + ", cloudid='" + this.cloudid + '\'' + '}';
    }
}
