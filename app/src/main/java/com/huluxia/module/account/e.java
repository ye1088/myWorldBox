package com.huluxia.module.account;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.data.message.UserMsgItem;
import com.huluxia.module.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: UserMsgs */
public class e extends a implements Parcelable {
    public static final Creator<e> CREATOR = new 1();
    public List<UserMsgItem> datas = new ArrayList();
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

    protected e(Parcel in) {
        super(in);
        this.more = in.readInt();
        this.start = in.readString();
        this.datas = in.createTypedArrayList(UserMsgItem.CREATOR);
    }
}
