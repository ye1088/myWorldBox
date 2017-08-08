package com.MCWorld.module.news;

import android.os.Parcel;

import com.MCWorld.module.b;
import java.util.ArrayList;

/* compiled from: NewsResult */
public class k extends b {
    public static final Creator<k> CREATOR = new Creator<k>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aZ(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jM(i);
        }

        public k aZ(Parcel source) {
            return new k(source);
        }

        public k[] jM(int size) {
            return new k[size];
        }
    };
    public ArrayList<c> list = new ArrayList();
    public long ts;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.ts);
        dest.writeTypedList(this.list);
    }

    protected k(Parcel in) {
        super(in);
        this.ts = in.readLong();
        this.list = in.createTypedArrayList(c.CREATOR);
    }
}
