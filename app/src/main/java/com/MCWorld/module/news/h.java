package com.MCWorld.module.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.GameInfo;

/* compiled from: NewsGameResult */
public class h extends com.MCWorld.module.a {
    public static final Creator<h> CREATOR = new Creator<h>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aW(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jJ(i);
        }

        public h aW(Parcel source) {
            return new h(source);
        }

        public h[] jJ(int size) {
            return new h[size];
        }
    };
    public a result;
    public long ts;

    /* compiled from: NewsGameResult */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return aX(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return jK(i);
            }

            public a aX(Parcel source) {
                return new a(source);
            }

            public a[] jK(int size) {
                return new a[size];
            }
        };
        public GameInfo app;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.app, flags);
        }

        protected a(Parcel in) {
            this.app = (GameInfo) in.readParcelable(GameInfo.class.getClassLoader());
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.ts);
        dest.writeParcelable(this.result, flags);
    }

    protected h(Parcel in) {
        super(in);
        this.ts = in.readLong();
        this.result = (a) in.readParcelable(a.class.getClassLoader());
    }
}
