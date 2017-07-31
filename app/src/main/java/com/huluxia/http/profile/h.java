package com.huluxia.http.profile;

import com.huluxia.data.TableList;
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

/* compiled from: ProfileFavoriteListRequest */
public class h extends a {
    private int count;
    private long sq;
    private String start;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?user_id=%d&start=%s&count=%d", new Object[]{ab.azX, Long.valueOf(this.sq), this.start, Integer.valueOf(this.count)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.start = json.optString("start", "0");
        TableList tableList = new TableList(json);
        JSONArray jsonArray = json.getJSONArray("posts");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new TopicItem((JSONObject) jsonArray.opt(i)));
        }
        response.setData(tableList);
    }

    public void g(List<NameValuePair> list) {
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

    public long fr() {
        return this.sq;
    }

    public void w(long user_id) {
        this.sq = user_id;
    }
}
