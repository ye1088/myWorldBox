package com.huluxia.module.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.data.category.Daren;
import com.huluxia.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DarenInfo */
public class h extends b implements Parcelable {
    public static final Creator<h> CREATOR = new Creator<h>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bo(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return kc(i);
        }

        public h bo(Parcel in) {
            return new h(in);
        }

        public h[] kc(int size) {
            return new h[size];
        }
    };
    public List<Daren> daren;
    public long rank;

    public h() {
        this.rank = -1;
        this.daren = new ArrayList();
    }

    protected h(Parcel in) {
        this.rank = in.readLong();
        this.daren = in.createTypedArrayList(Daren.CREATOR);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.rank);
        dest.writeTypedList(this.daren);
    }

    public int describeContents() {
        return 0;
    }
}
