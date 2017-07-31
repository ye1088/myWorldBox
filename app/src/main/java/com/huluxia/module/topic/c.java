package com.huluxia.module.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BbsZoneSubCategory */
public class c extends a {
    public static final Creator<c> CREATOR = new 1();
    public List<d> categories;

    public c() {
        this.categories = new ArrayList();
        this.categories = new ArrayList();
    }

    public String toString() {
        return "BbsZoneSubCategory{categories=" + this.categories + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.categories);
    }

    protected c(Parcel in) {
        super(in);
        this.categories = new ArrayList();
        this.categories = in.createTypedArrayList(d.CREATOR);
    }
}
