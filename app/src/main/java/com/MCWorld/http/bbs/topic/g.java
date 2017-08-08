package com.MCWorld.http.bbs.topic;

import com.MCWorld.data.TableList;
import com.MCWorld.data.topic.ScoreItem;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CreditsAllListRequest */
public class g extends a {
    private int count = 100;
    private long ss;
    private String start = "0";
    private long sy = 1;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?post_id=%d&type_id=%d&start=%s&count=%d", new Object[]{ab.aAm, Long.valueOf(this.ss), Long.valueOf(this.sy), this.start, Integer.valueOf(this.count)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.start = json.optString("start", "0");
        TableList tableList = new TableList(json);
        JSONArray jsonArray = json.getJSONArray("scorelist");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new ScoreItem((JSONObject) jsonArray.opt(i)));
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

    public long fs() {
        return this.ss;
    }

    public void x(long post_id) {
        this.ss = post_id;
    }

    public void I(boolean isTopic) {
        this.sy = isTopic ? 1 : 2;
    }
}
