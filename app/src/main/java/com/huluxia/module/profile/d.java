package com.huluxia.module.profile;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: Friendships */
public class d extends b implements Parcelable {
    public static final Creator<d> CREATOR = new Creator<d>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return be(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jR(i);
        }

        public d be(Parcel source) {
            return new d(source);
        }

        public d[] jR(int size) {
            return new d[size];
        }
    };
    public List<c> friendships = new ArrayList();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.friendships);
    }

    protected d(Parcel in) {
        super(in);
        this.friendships = in.createTypedArrayList(c.CREATOR);
    }
}
