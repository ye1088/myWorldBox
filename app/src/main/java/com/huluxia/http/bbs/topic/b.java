package com.huluxia.http.bbs.topic;

import com.huluxia.data.TableList;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.http.base.c;
import com.huluxia.http.base.d;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CommentInListRequest */
public class b extends c {
    private int count;
    private String start;

    public String eU() {
        return String.format(Locale.getDefault(), "%s/comment/in/list?start=%s&count=%d", new Object[]{c.HOST, this.start, Integer.valueOf(this.count)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.start = json.optString("start", "0");
        TableList tableList = new TableList(json);
        JSONArray jsonArray = json.getJSONArray("comments");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new CommentItem((JSONObject) jsonArray.opt(i)));
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
}
