package com.MCWorld.data.topic;

import android.os.Parcel;

import com.MCWorld.module.a;

/* compiled from: TopicFavorCheck */
public class d extends a {
    public static final Creator<d> CREATOR = new Creator<d>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return ak(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aU(i);
        }

        public d ak(Parcel source) {
            return new d(source);
        }

        public d[] aU(int size) {
            return new d[size];
        }
    };
    public int isFavorite;

    public d(Parcel source) {
        super(source);
        this.isFavorite = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.isFavorite);
    }
}
