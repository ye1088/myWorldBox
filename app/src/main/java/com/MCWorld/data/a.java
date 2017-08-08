package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: BindRet */
public class a extends com.MCWorld.module.a implements Parcelable {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return b(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return H(i);
        }

        public a b(Parcel in) {
            return new a(in);
        }

        public a[] H(int size) {
            return new a[size];
        }
    };
    public int bind;
    public int checkstatus;
    public String session_key;

    public a(JSONObject obj) throws JSONException {
        this.bind = obj.optInt("bind", 0);
        this.checkstatus = obj.optInt("checkstatus", 2);
        this.session_key = obj.optString("session_key");
    }

    public a(Parcel source) {
        this.bind = source.readInt();
        this.checkstatus = source.readInt();
        this.session_key = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bind);
        dest.writeInt(this.checkstatus);
        dest.writeString(this.session_key);
    }
}
