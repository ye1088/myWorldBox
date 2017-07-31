package com.huluxia.mconline.gameloc.http;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: DemoInfo */
public class c implements Parcelable {
    public static final Creator<c> CREATOR = new Creator<c>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return as(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return hI(i);
        }

        public c as(Parcel source) {
            return new c(source);
        }

        public c[] hI(int size) {
            return new c[size];
        }
    };
    public String demo;
    public int more;
    public String msg;
    public int status;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeString(this.demo);
        dest.writeInt(this.more);
        dest.writeInt(this.status);
    }

    protected c(Parcel in) {
        this.msg = in.readString();
        this.demo = in.readString();
        this.more = in.readInt();
        this.status = in.readInt();
    }
}
