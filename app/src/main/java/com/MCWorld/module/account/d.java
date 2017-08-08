package com.MCWorld.module.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.message.SysMsgItem;
import com.MCWorld.module.a;
import java.util.List;

/* compiled from: SysMsgs */
public class d extends a implements Parcelable {
    public static final Creator<d> CREATOR = new 1();
    public List<SysMsgItem> datas;
    public int more;
    public String start;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.more);
        dest.writeString(this.start);
        dest.writeTypedList(this.datas);
    }

    protected d(Parcel in) {
        super(in);
        this.more = in.readInt();
        this.start = in.readString();
        this.datas = in.createTypedArrayList(SysMsgItem.CREATOR);
    }
}
