package com.MCWorld.module.news;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: NewsCommentItem */
public class d$a implements Parcelable {
    public static final Creator<d$a> CREATOR = new Creator<d$a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aS(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jF(i);
        }

        public d$a aS(Parcel source) {
            return new d$a(source);
        }

        public d$a[] jF(int size) {
            return new d$a[size];
        }
    };
    public long commentID;
    public String nick;
    public long seq;
    public int state;
    public String text;
    public long userID;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.commentID);
        dest.writeString(this.nick);
        dest.writeLong(this.seq);
        dest.writeInt(this.state);
        dest.writeString(this.text);
        dest.writeLong(this.userID);
    }

    protected d$a(Parcel in) {
        this.commentID = in.readLong();
        this.nick = in.readString();
        this.seq = in.readLong();
        this.state = in.readInt();
        this.text = in.readString();
        this.userID = in.readLong();
    }
}
