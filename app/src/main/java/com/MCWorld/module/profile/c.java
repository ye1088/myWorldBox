package com.MCWorld.module.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.UserBaseInfo;

/* compiled from: FriendshipInfo */
public class c implements Parcelable {
    public static final Creator<c> CREATOR = new 1();
    public long createTime;
    public UserBaseInfo user;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.createTime);
        dest.writeParcelable(this.user, flags);
    }

    protected c(Parcel in) {
        this.createTime = in.readLong();
        this.user = (UserBaseInfo) in.readParcelable(UserBaseInfo.class.getClassLoader());
    }
}
