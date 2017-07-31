package com.huluxia.module;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: GameDownloadUrl */
public class e implements Parcelable {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aB(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return iW(i);
        }

        public e aB(Parcel source) {
            return new e(source);
        }

        public e[] iW(int size) {
            return new e[size];
        }
    };
    public String name;
    public String url;

    /* compiled from: GameDownloadUrl */
    public static class a {
        public String aqo;
    }

    public e(Parcel source) {
        this();
        this.name = source.readString();
        this.url = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
    }

    public String toString() {
        return "GameDownloadUrl{name='" + this.name + '\'' + ", url='" + this.url + '\'' + '}';
    }
}
