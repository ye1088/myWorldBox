package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: BbsZoneSubTags */
public class f implements Parcelable {
    public static final Creator<f> CREATOR = new Creator<f>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bm(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return ka(i);
        }

        public f bm(Parcel source) {
            return new f(source);
        }

        public f[] ka(int size) {
            return new f[size];
        }
    };
    public int ID;
    public String name;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.ID != ((f) o).ID) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.ID;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.name);
    }

    protected f(Parcel in) {
        this.ID = in.readInt();
        this.name = in.readString();
    }
}
