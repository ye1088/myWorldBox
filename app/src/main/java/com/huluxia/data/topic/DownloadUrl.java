package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadUrl implements Parcelable, Serializable {
    public static final Creator<DownloadUrl> CREATOR = new Creator<DownloadUrl>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return ad(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aN(i);
        }

        public DownloadUrl ad(Parcel source) {
            return new DownloadUrl(source);
        }

        public DownloadUrl[] aN(int size) {
            return new DownloadUrl[size];
        }
    };
    private static final long serialVersionUID = 3478318176946847534L;
    public String name;
    public String url;

    public DownloadUrl(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.name = obj.optString("name");
            this.url = obj.optString("url");
        }
    }

    public DownloadUrl(Parcel source) {
        this();
        this.name = source.readString();
        this.url = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
    }

    public String toString() {
        return "DownloadUrl{name='" + this.name + '\'' + ", url='" + this.url + '\'' + '}';
    }
}
