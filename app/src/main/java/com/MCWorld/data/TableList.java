package com.MCWorld.data;

import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.topic.TopicItem;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TableList extends ArrayList<Object> {
    private static final long serialVersionUID = 1;
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

    public TableList(JSONObject json) throws JSONException {
        boolean hasmore = json.optInt("more") != 0;
        setStart(json.optLong("start"));
        setHasMore(hasmore);
    }

    public Object getExtData() {
        return this.extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public static TableList parseCateTopicListResponse(String response) throws JSONException {
        JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
        TableList tableList = new TableList(json);
        if (!json.isNull("category")) {
            tableList.setExtData(new TopicCategory(json.optJSONObject("category")));
        }
        JSONArray jsonArray = json.getJSONArray("posts");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new TopicItem((JSONObject) jsonArray.opt(i)));
        }
        return tableList;
    }
}
