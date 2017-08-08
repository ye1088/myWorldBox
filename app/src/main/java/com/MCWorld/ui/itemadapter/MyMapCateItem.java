package com.MCWorld.ui.itemadapter;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MyMapCateItem implements Parcelable, Serializable {
    public static final Creator<MyMapCateItem> CREATOR = new Creator<MyMapCateItem>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bt(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return kF(i);
        }

        public MyMapCateItem bt(Parcel in) {
            return new MyMapCateItem(in);
        }

        public MyMapCateItem[] kF(int size) {
            return new MyMapCateItem[size];
        }
    };
    private static final long serialVersionUID = 6383791210200854771L;
    public long id;
    public String photo;
    public String title;

    public MyMapCateItem(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.id = obj.optLong("cateid");
            this.title = obj.optString("catename");
            this.photo = obj.optString("imgurl");
        }
    }

    public MyMapCateItem(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.photo = source.readString();
    }

    public static ArrayList<MyMapCateItem> parseJsonStr(String jsonA) throws JSONException {
        JSONArray cate_list = ((JSONObject) new JSONTokener(jsonA).nextValue()).getJSONArray("categorylist");
        ArrayList<MyMapCateItem> mapList = new ArrayList();
        for (int i = 0; i < cate_list.length(); i++) {
            mapList.add(new MyMapCateItem(cate_list.getJSONObject(i)));
        }
        return mapList;
    }

    public static ArrayList<MyMapCateItem> parseJsonStrByTag(String jsonA, String tag) throws JSONException {
        JSONArray cate_list = ((JSONObject) new JSONTokener(jsonA).nextValue()).getJSONArray(tag);
        ArrayList<MyMapCateItem> mapList = new ArrayList();
        for (int i = 0; i < cate_list.length(); i++) {
            mapList.add(new MyMapCateItem(cate_list.getJSONObject(i)));
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

    public MyMapCateItem(MyMapCateItem old) {
        this.id = old.id;
        this.title = old.title;
        this.photo = old.photo;
    }

    public MyMapCateItem(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String toString() {
        return this.title;
    }
}
