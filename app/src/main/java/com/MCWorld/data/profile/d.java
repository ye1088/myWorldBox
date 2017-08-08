package com.MCWorld.data.profile;

import android.os.Parcel;

import com.MCWorld.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: StudioAuditInfo */
public class d extends b {
    public static final Creator<d> CREATOR = new 1();
    private List<a> list;
    public int total;

    public List<a> getList() {
        return this.list;
    }

    public void setList(List<a> list) {
        this.list = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.total);
        dest.writeList(this.list);
    }

    protected d(Parcel in) {
        super(in);
        this.total = in.readInt();
        this.list = new ArrayList();
        in.readList(this.list, a.class.getClassLoader());
    }
}
