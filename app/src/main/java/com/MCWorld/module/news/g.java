package com.MCWorld.module.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.GameInfo;

/* compiled from: NewsDetailResult */
public class g extends com.MCWorld.module.a {
    public static final Creator<g> CREATOR = new Creator<g>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aU(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jH(i);
        }

        public g aU(Parcel source) {
            return new g(source);
        }

        public g[] jH(int size) {
            return new g[size];
        }
    };
    public a result;
    public long ts;

    /* compiled from: NewsDetailResult */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return aV(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return jI(i);
            }

            public a aV(Parcel source) {
                return new a(source);
            }

            public a[] jI(int size) {
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

    protected g(Parcel in) {
        super(in);
        this.ts = in.readLong();
        this.result = (a) in.readParcelable(a.class.getClassLoader());
    }
}
