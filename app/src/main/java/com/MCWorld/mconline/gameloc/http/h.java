package com.MCWorld.mconline.gameloc.http;

import android.os.Parcel;

import com.MCWorld.module.j;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RoomsInfo */
public class h extends j {
    public static final Creator<h> CREATOR = new 1();
    public String client_ip;
    public List<g> room_infos = new ArrayList();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.room_infos);
    }

    protected h(Parcel in) {
        super(in);
        this.room_infos = in.createTypedArrayList(g.CREATOR);
    }
}
