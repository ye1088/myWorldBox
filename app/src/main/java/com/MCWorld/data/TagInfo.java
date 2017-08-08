package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class TagInfo implements Parcelable, Serializable {
    public static final Creator<TagInfo> CREATOR = new 1();
    private static final long serialVersionUID = -1176069706646312378L;
    public long ID;
    public String name;

    public TagInfo(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.ID = obj.optLong("ID");
            this.name = obj.optString("name");
        }
    }

    public TagInfo(Parcel source) {
        this.ID = source.readLong();
        this.name = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.ID);
        dest.writeString(this.name);
    }

    public long getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }
}
