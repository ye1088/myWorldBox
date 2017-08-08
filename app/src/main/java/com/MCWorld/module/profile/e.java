package com.MCWorld.module.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.a;

/* compiled from: NickChangeNumInfo */
public class e extends a implements Parcelable {
    public static final Creator<e> CREATOR = new 1();
    public int count;

    public e(Parcel source) {
        super(source);
        this.count = source.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.count);
    }

    public boolean isFree() {
        return isSucc() && this.count == 0;
    }
}
