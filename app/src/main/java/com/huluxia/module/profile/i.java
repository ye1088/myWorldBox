package com.huluxia.module.profile;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.b;
import java.util.ArrayList;

/* compiled from: SpaceStyleListInfo */
public class i extends b implements Parcelable {
    public static final Creator<i> CREATOR = new Creator<i>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bh(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jV(i);
        }

        public i bh(Parcel in) {
            return new i(in);
        }

        public i[] jV(int size) {
            return new i[size];
        }
    };
    public ArrayList<h> spacelist;

    protected i(Parcel in) {
        this.spacelist = in.createTypedArrayList(h.CREATOR);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.spacelist);
    }

    public int describeContents() {
        return 0;
    }
}
