package com.MCWorld.module;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "hlx_profile_info")
/* compiled from: ProfileDbInfo */
public class p implements Parcelable {
    public static final Creator<p> CREATOR = new 1();
    public static final String JSON = "json";
    public static final String RESERVE1 = "reserve1";
    public static final String RESERVE2 = "reserve2";
    public static final String TABLE = "hlx_profile_info";
    public static final String UID = "uid";
    @DatabaseField(columnName = "json")
    public String json;
    @DatabaseField(columnName = "reserve1")
    public int reserve1;
    @DatabaseField(columnName = "reserve2")
    public String reserve2;
    @DatabaseField(columnName = "uid", id = true)
    public long uid;

    public p(long _uid, String _json) {
        this.uid = _uid;
        this.json = _json;
    }

    public p(Parcel source) {
        this();
        this.uid = source.readLong();
        this.json = source.readString();
        this.reserve1 = source.readInt();
        this.reserve2 = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeString(this.json);
        dest.writeInt(this.reserve1);
        dest.writeString(this.reserve2);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (((p) o).uid != this.uid) {
            return false;
        }
        return true;
    }
}
