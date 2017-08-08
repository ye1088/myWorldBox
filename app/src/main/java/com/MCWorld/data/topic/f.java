package com.MCWorld.data.topic;

import android.os.Parcel;

import com.MCWorld.module.a;

/* compiled from: TopicShareAddress */
public class f extends a {
    public static final Creator<f> CREATOR = new Creator<f>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return an(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aX(i);
        }

        public f an(Parcel source) {
            return new f(source);
        }

        public f[] aX(int size) {
            return new f[size];
        }
    };
    public String address;

    public f(Parcel source) {
        super(source);
        this.address = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.address);
    }
}
