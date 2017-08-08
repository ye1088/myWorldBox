package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class StockItem implements Parcelable, Serializable {
    public static final Creator<StockItem> CREATOR = new Creator<StockItem>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return A(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return ak(i);
        }

        public StockItem A(Parcel in) {
            return new StockItem(in);
        }

        public StockItem[] ak(int size) {
            return new StockItem[size];
        }
    };
    private static final long serialVersionUID = 7064819658629861327L;
    public long id;
    public long number;
    public String photo;
    public long sn;
    public String title;

    public StockItem(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.photo = source.readString();
        this.sn = source.readLong();
        this.number = source.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.photo);
        dest.writeLong(this.sn);
        dest.writeLong(this.number);
    }
}
