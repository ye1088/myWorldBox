package com.MCWorld.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.log.HLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TableListParc extends ArrayList<Object> implements Parcelable {
    public static final Creator<TableListParc> CREATOR = new 1();
    private static final long serialVersionUID = -8360746350643206279L;
    private Object extData;
    private boolean hasMore;
    private long start;

    public boolean isHasMore() {
        return this.hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean getHasMore() {
        return this.hasMore;
    }

    public long getStart() {
        return this.start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public Object getExtData() {
        return this.extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public TableListParc(JSONObject json) throws JSONException {
        boolean hasmore = json.optInt("more") != 0;
        setStart(json.optLong("start"));
        setHasMore(hasmore);
    }

    private TableListParc(Parcel in) {
        this.hasMore = in.readInt() != 0;
        this.start = in.readLong();
        this.extData = in.readValue(TopicItem.class.getClassLoader());
    }

    public TableListParc() {
        this.hasMore = false;
        this.start = 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = 1;
        if (!this.hasMore) {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeLong(this.start);
        dest.writeValue(this.extData);
    }

    public static TableListParc parseJsonResponse(String response) throws JSONException {
        int i;
        JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
        TableListParc tableList = new TableListParc(json);
        JSONArray jsonArray = json.optJSONArray("McCategorylist");
        HLog.info("TestTest", "McCategorylist size = " + jsonArray.length(), new Object[0]);
        for (i = 0; i < jsonArray.length(); i++) {
            tableList.add(new TopicCategory((JSONObject) jsonArray.opt(i)));
        }
        jsonArray = json.optJSONArray("BBSCategorylist");
        for (i = 0; i < jsonArray.length(); i++) {
            tableList.add(new TopicCategory((JSONObject) jsonArray.opt(i)));
        }
        return tableList;
    }
}
