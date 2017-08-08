package com.MCWorld.http.profile;

import com.MCWorld.data.TableList;
import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: FollowingListRequest */
public class e extends a {
    private int count;
    private long sq;
    private String start;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?user_id=%d&start=%s&count=%d", new Object[]{ab.aAq, Long.valueOf(this.sq), this.start, Integer.valueOf(this.count)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.start = json.optString("start", "0");
        TableList tableList = new TableList(json);
        JSONArray jsonArray = json.getJSONArray("friendships");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new ProfileInfo(((JSONObject) jsonArray.opt(i)).getJSONObject("user")));
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
