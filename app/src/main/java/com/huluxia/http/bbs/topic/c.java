package com.huluxia.http.bbs.topic;

import com.huluxia.data.PageList;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.http.base.d;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CommentListRequest */
public class c extends com.huluxia.http.base.c {
    private long ss;
    private int su;
    private int sv;

    public String eU() {
        return String.format(Locale.getDefault(), "%s/comment/list?post_id=%d&page_no=%d&page_size=%d", new Object[]{com.huluxia.http.base.c.HOST, Long.valueOf(this.ss), Integer.valueOf(this.su), Integer.valueOf(this.sv)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        PageList pageList = new PageList(json);
        JSONArray jsonArray = json.getJSONArray("comments");
        for (int i = 0; i < jsonArray.length(); i++) {
            pageList.add(new CommentItem((JSONObject) jsonArray.opt(i)));
        }
        response.setData(pageList);
    }

    public void g(List<NameValuePair> list) {
    }

    public long fs() {
        return this.ss;
    }

    public void x(long post_id) {
        this.ss = post_id;
    }

    public int ft() {
        return this.su;
    }

    public void be(int page_no) {
        this.su = page_no;
    }

    public int fu() {
        return this.sv;
    }

    public void bf(int page_size) {
        this.sv = page_size;
    }
}
