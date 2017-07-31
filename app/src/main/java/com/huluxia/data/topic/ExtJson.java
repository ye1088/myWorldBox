package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtJson implements Parcelable, Serializable {
    public static final Creator<ExtJson> CREATOR = new 1();
    private static final long serialVersionUID = -6454979145625877457L;
    public String biz;
    public long id;
    public String subBiz;

    public ExtJson(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.id = obj.optLong("id");
            this.biz = obj.optString("biz");
            this.subBiz = obj.optString("subBiz");
        }
    }

    public ExtJson(Parcel source) {
        this.id = source.readLong();
        this.biz = source.readString();
        this.subBiz = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.biz);
        dest.writeString(this.subBiz);
    }
}
