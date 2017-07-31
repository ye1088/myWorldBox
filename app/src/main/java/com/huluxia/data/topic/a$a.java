package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: StudioInfo */
public class a$a implements Parcelable {
    public static final Creator<a$a> CREATOR = new Creator<a$a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aj(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aT(i);
        }

        public a$a aj(Parcel source) {
            return new a$a(source);
        }

        public a$a[] aT(int size) {
            return new a$a[size];
        }
    };
    public String avatar;
    public int isStudio;
    public String userName;
    public long userid;

    protected a$a(Parcel in) {
        this.userid = in.readLong();
        this.userName = in.readString();
        this.avatar = in.readString();
        this.isStudio = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.userid);
        dest.writeString(this.userName);
        dest.writeString(this.avatar);
        dest.writeInt(this.isStudio);
    }
}
