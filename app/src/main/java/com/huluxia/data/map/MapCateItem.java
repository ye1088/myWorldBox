package com.huluxia.data.map;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MapCateItem implements Parcelable, Serializable {
    public static final Creator<MapCateItem> CREATOR = new 1();
    private static final long serialVersionUID = 6383791210200854771L;
    public long id;
    public String photo;
    public String title;

    public MapCateItem(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.id = obj.optLong("cateid");
            this.title = obj.optString("catename");
            this.photo = obj.optString("imgurl");
        }
    }

    public MapCateItem(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.photo = source.readString();
    }

    public static ArrayList<MapCateItem> parseJsonStr(String jsonA) throws JSONException {
        JSONArray cate_list = ((JSONObject) new JSONTokener(jsonA).nextValue()).getJSONArray("categorylist");
        ArrayList<MapCateItem> mapList = new ArrayList();
        for (int i = 0; i < cate_list.length(); i++) {
            mapList.add(new MapCateItem(cate_list.getJSONObject(i)));
        }
        return mapList;
    }

    public static ArrayList<MapCateItem> parseJsonStrByTag(String jsonA, String tag) throws JSONException {
        JSONArray cate_list = ((JSONObject) new JSONTokener(jsonA).nextValue()).getJSONArray(tag);
        ArrayList<MapCateItem> mapList = new ArrayList();
        for (int i = 0; i < cate_list.length(); i++) {
            mapList.add(new MapCateItem(cate_list.getJSONObject(i)));
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

    public MapCateItem(MapCateItem old) {
        this.id = old.id;
        this.title = old.title;
        this.photo = old.photo;
    }

    public MapCateItem(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String toString() {
        return this.title;
    }
}
