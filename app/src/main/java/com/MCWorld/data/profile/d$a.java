package com.MCWorld.data.profile;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: StudioAuditInfo */
public class d$a implements Parcelable {
    public static final Creator<d$a> CREATOR = new Creator<d$a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return J(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return at(i);
        }

        public d$a J(Parcel source) {
            return new d$a(source);
        }

        public d$a[] at(int size) {
            return new d$a[size];
        }
    };
    private String avatar;
    private int id;
    private String nick;
    private int sid;
    private int status;
    private long userid;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public long getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = (long) userid;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sid);
        dest.writeLong(this.userid);
        dest.writeString(this.avatar);
        dest.writeString(this.nick);
        dest.writeInt(this.status);
        dest.writeInt(this.id);
    }

    protected d$a(Parcel in) {
        this.sid = in.readInt();
        this.userid = in.readLong();
        this.avatar = in.readString();
        this.nick = in.readString();
        this.status = in.readInt();
        this.id = in.readInt();
    }
}
