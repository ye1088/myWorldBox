package com.huluxia.login;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: RegisterReqInfo */
public final class i implements Parcelable {
    public static final Creator<i> CREATOR = new Creator<i>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aq(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return cQ(i);
        }

        public i aq(Parcel source) {
            return new i(source);
        }

        public i[] cQ(int size) {
            return new i[size];
        }
    };
    public String accessToken;
    public String avatarFid;
    public long birthday;
    public String email;
    public String encrytPwd;
    public int gender;
    public String nick;
    public String openid;

    public i(Parcel source) {
        this.email = source.readString();
        this.encrytPwd = source.readString();
        this.nick = source.readString();
        this.gender = source.readInt();
        this.birthday = source.readLong();
        this.avatarFid = source.readString();
        this.openid = source.readString();
        this.accessToken = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.encrytPwd);
        dest.writeString(this.nick);
        dest.writeInt(this.gender);
        dest.writeLong(this.birthday);
        dest.writeString(this.avatarFid);
        dest.writeString(this.openid);
        dest.writeString(this.accessToken);
    }
}
