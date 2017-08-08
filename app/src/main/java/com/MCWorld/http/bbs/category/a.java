package com.MCWorld.http.bbs.category;

import com.MCWorld.data.TableList;
import com.MCWorld.data.category.Daren;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CategoryDarenRequest */
public class a extends com.MCWorld.http.base.a {
    private int count = 10;
    private long sm;
    private String start;

    public String eU() {
        StringBuilder sb = new StringBuilder();
        sb.append(ab.ayW);
        sb.append("?cat_id=").append(this.sm);
        sb.append("&start=").append(this.start);
        sb.append("&count=").append(this.count);
        return String.format(Locale.getDefault(), sb.toString(), new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.start = json.optString("start", "0");
        TableList tableList = new TableList(json);
        JSONArray jsonArray = json.getJSONArray("daren");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new Daren((JSONObject) jsonArray.opt(i)));
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
