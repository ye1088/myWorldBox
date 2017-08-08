package com.MCWorld.module.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.UserBaseInfo;

/* compiled from: NewsCommentItem */
public class d implements Parcelable {
    public static final Creator<d> CREATOR = new 1();
    public long commentID;
    public long createTime;
    public a refComment;
    public long seq;
    public int state;
    public String text;
    public UserBaseInfo user;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.commentID);
        dest.writeLong(this.createTime);
        dest.writeLong(this.seq);
        dest.writeInt(this.state);
        dest.writeString(this.text);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.refComment, flags);
    }

    protected d(Parcel in) {
        this.commentID = in.readLong();
        this.createTime = in.readLong();
        this.seq = in.readLong();
        this.state = in.readInt();
        this.text = in.readString();
        this.user = (UserBaseInfo) in.readParcelable(UserBaseInfo.class.getClassLoader());
        this.refComment = (a) in.readParcelable(a.class.getClassLoader());
    }
}
