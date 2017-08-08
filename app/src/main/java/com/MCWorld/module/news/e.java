package com.MCWorld.module.news;

import android.os.Parcel;

import com.MCWorld.module.b;
import java.util.ArrayList;

/* compiled from: NewsCommentResult */
public class e extends b {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aT(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jG(i);
        }

        public e aT(Parcel source) {
            return new e(source);
        }

        public e[] jG(int size) {
            return new e[size];
        }
    };
    public ArrayList<d> list = new ArrayList();
    public long ts;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.ts);
        dest.writeTypedList(this.list);
    }

    protected e(Parcel in) {
        super(in);
        this.ts = in.readLong();
        this.list = in.createTypedArrayList(d.CREATOR);
    }
}
