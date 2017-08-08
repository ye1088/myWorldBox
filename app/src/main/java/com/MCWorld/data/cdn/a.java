package com.MCWorld.data.cdn;

import android.os.Parcel;

/* compiled from: CDNHost */
public class a extends com.MCWorld.module.a {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return q(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aa(i);
        }

        public a q(Parcel source) {
            return new a(source);
        }

        public a[] aa(int size) {
            return new a[size];
        }
    };
    private String cdnHost;

    public a(Parcel source) {
        if (source != null) {
            this.cdnHost = (String) source.readParcelable(null);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cdnHost);
    }

    public String getCdnHost() {
        return this.cdnHost;
    }

    public void setCdnHost(String cdnHost) {
        this.cdnHost = cdnHost;
    }
}
