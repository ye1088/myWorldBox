package com.MCWorld.mconline.gameloc.http;

import android.os.Parcel;

import com.MCWorld.module.a;

/* compiled from: NoticeInfo */
public class d extends a {
    public static final Creator<d> CREATOR = new Creator<d>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return au(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return hK(i);
        }

        public d au(Parcel source) {
            return new d(source);
        }

        public d[] hK(int size) {
            return new d[size];
        }
    };
    public String notice;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.notice);
    }

    protected d(Parcel in) {
        super(in);
        this.notice = in.readString();
    }
}
