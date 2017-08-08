package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: BbsZoneSubModerator */
public class e implements Parcelable {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bl(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jZ(i);
        }

        public e bl(Parcel source) {
            return new e(source);
        }

        public e[] jZ(int size) {
            return new e[size];
        }
    };
    public String avatar;
    public String identityTitle;
    public int leveColor;
    public String nick;
    public int userID;

    public String toString() {
        return "BbsZoneSubModerator{userID=" + this.userID + ", nick='" + this.nick + '\'' + ", avatar='" + this.avatar + '\'' + ", identityTitle='" + this.identityTitle + '\'' + ", leveColor=" + this.leveColor + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.userID != ((e) o).userID) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.userID;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userID);
        dest.writeString(this.nick);
        dest.writeString(this.avatar);
        dest.writeString(this.identityTitle);
        dest.writeInt(this.leveColor);
    }

    protected e(Parcel in) {
        this.userID = in.readInt();
        this.nick = in.readString();
        this.avatar = in.readString();
        this.identityTitle = in.readString();
        this.leveColor = in.readInt();
    }
}
