package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class Medal implements Parcelable, Serializable {
    public static final Creator<Medal> CREATOR = new 1();
    private static final long serialVersionUID = 4358494580586485592L;
    public String name;
    public String url;

    public Medal(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.name = obj.optString("name");
            this.url = obj.optString("url");
        }
    }

    public Medal(Parcel source) {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
