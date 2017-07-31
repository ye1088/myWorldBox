package com.huluxia.http.bbs.topic;

import com.huluxia.data.TableList;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TopicListRequest */
public class p extends a {
    private int count;
    private long sH;
    private int sL = 0;
    private long sm;
    private String start;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?cat_id=%d&tag_id=%d&start=%s&count=%d&sort_by=%d", new Object[]{ab.azQ, Long.valueOf(this.sm), Long.valueOf(this.sH), this.start, Integer.valueOf(this.count), Integer.valueOf(this.sL)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.start = json.optString("start", "0");
        TableList tableList = new TableList(json);
        if (!json.isNull("category")) {
            tableList.setExtData(new TopicCategory(json.optJSONObject("category")));
        }
        JSONArray jsonArray = json.getJSONArray("posts");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new TopicItem((JSONObject) jsonArray.opt(i)));
        }
        response.setData(tableList);
    }

    public void g(List<NameValuePair> list) {
    }

    public long fm() {
        return this.sm;
    }

    public void v(long cat_id) {
        this.sm = cat_id;
    }

    public long fA() {
        return this.sH;
    }

    public void D(long tag_id) {
        this.sH = tag_id;
    }

    public String fn() {
        return this.start;
    }

    public void aK(String start) {
        this.start = start;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int fD() {
        return this.sL;
    }

    public void bg(int sort_by) {
        this.sL = sort_by;
    }
}
