package com.huluxia.http.message;

import com.huluxia.data.TableList;
import com.huluxia.data.message.SysMsgItem;
import com.huluxia.data.message.UserMsgItem;
import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: MessageListRequest */
public class b extends a {
    private int count;
    private String start;
    private long sy;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?start=%s&count=%d&type_id=%d", new Object[]{ab.aAf, this.start, Integer.valueOf(this.count), Long.valueOf(this.sy)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        this.start = json.optString("start", "0");
        TableList tableList = new TableList(json);
        JSONArray jsonArray = json.optJSONArray("datas");
        if (jsonArray != null) {
            int i;
            if (1 == this.sy) {
                for (i = 0; i < jsonArray.length(); i++) {
                    tableList.add(new SysMsgItem((JSONObject) jsonArray.opt(i)));
                }
            } else {
                for (i = 0; i < jsonArray.length(); i++) {
                    tableList.add(new UserMsgItem((JSONObject) jsonArray.opt(i)));
                }
            }
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

    public long fx() {
        return this.sy;
    }

    public void z(long type_id) {
        this.sy = type_id;
    }
}
