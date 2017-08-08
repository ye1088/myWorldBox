package com.MCWorld.module.profile;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: ProfileSpaceStyle */
public class h implements Parcelable {
    public static final Creator<h> CREATOR = new Creator<h>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bg(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jU(i);
        }

        public h bg(Parcel source) {
            return new h(source);
        }

        public h[] jU(int size) {
            return new h[size];
        }
    };
    public String color;
    public String desc;
    public int id;
    public String imgurl;
    public String integralNick;
    public int isuse;
    public int model;
    public int price;
    public long size;
    public String title;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.imgurl);
        dest.writeString(this.desc);
        dest.writeInt(this.isuse);
        dest.writeInt(this.model);
        dest.writeInt(this.price);
        dest.writeString(this.integralNick);
        dest.writeString(this.color);
        dest.writeLong(this.size);
    }

    protected h(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.imgurl = in.readString();
        this.desc = in.readString();
        this.isuse = in.readInt();
        this.model = in.readInt();
        this.price = in.readInt();
        this.integralNick = in.readString();
        this.color = in.readString();
        this.size = in.readLong();
    }
}
