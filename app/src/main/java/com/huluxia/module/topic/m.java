package com.huluxia.module.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ZoneCategory */
public class m extends a {
    public static final Creator<m> CREATOR = new 1();
    public List<n> categoryforum;

    public m() {
        this.categoryforum = new ArrayList();
        this.categoryforum = new ArrayList();
    }

    public String toString() {
        return "ZoneCategory{categoryforum=" + this.categoryforum + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.categoryforum);
    }

    protected m(Parcel in) {
        super(in);
        this.categoryforum = new ArrayList();
        this.categoryforum = in.createTypedArrayList(n.CREATOR);
    }
}
