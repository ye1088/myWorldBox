package com.huluxia.http.bbs.category;

import com.huluxia.data.TableList;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.http.base.a;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CategoryVoteListRequest */
public class d extends a {
    private int count = 20;
    private String start = "0";

    public String eU() {
        return String.format(Locale.getDefault(), "%s/category/vote/list%s?start=%s&count=%d", new Object[]{a.rN, a.rP, this.start, Integer.valueOf(this.count)});
    }

    public void a(com.huluxia.http.base.d response, JSONObject json) throws JSONException {
        this.start = json.optString("start", "0");
        TableList tableList = new TableList(json);
        JSONArray jsonArray = json.optJSONArray("categories");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                tableList.add(new TopicCategory((JSONObject) jsonArray.opt(i)));
            }
        }
        response.setData(tableList);
    }

    public void g(List<NameValuePair> list) {
    }

    public void aK(String start) {
        this.start = start;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
