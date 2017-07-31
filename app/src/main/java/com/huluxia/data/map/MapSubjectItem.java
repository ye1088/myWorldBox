package com.huluxia.data.map;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapSubjectItem implements Parcelable, Serializable {
    public static final Creator<MapCateItem> CREATOR = new Creator<MapCateItem>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return t(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return ad(i);
        }

        public MapCateItem t(Parcel in) {
            return new MapCateItem(in);
        }

        public MapCateItem[] ad(int size) {
            return new MapCateItem[size];
        }
    };
    private static final long serialVersionUID = 6383791210200854771L;
    public long id;
    public String photo;
    public String title;

    public MapSubjectItem(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.id = obj.optLong("id");
            this.title = obj.optString("name");
            this.photo = obj.optString("icon");
        }
    }

    public MapSubjectItem(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.photo = source.readString();
    }

    public static List<MapSubjectItem> parseJsonStr(JSONArray jsonA) throws JSONException {
        ArrayList<MapSubjectItem> mapList = new ArrayList();
        for (int i = 0; i < jsonA.length(); i++) {
            mapList.add(new MapSubjectItem(jsonA.getJSONObject(i)));
        }
        return mapList;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.photo);
    }

    public MapSubjectItem(MapCateItem old) {
        this.id = old.id;
        this.title = old.title;
        this.photo = old.photo;
    }
}
