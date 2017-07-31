package com.huluxia.http.bbs.category;

import com.huluxia.data.TableListParc;
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

/* compiled from: TopicCategoryListRequest */
public class h extends a {
    public String eU() {
        return String.format(Locale.getDefault(), ab.azb, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        TableListParc tableList = new TableListParc(json);
        JSONArray jsonArray = json.optJSONArray("categories");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new TopicCategory((JSONObject) jsonArray.opt(i)));
        }
        JSONObject obj = json.optJSONObject("postInfo");
        if (obj != null) {
            tableList.setExtData(new TopicItem(obj));
        }
        response.setData(tableList);
    }

    public void g(List<NameValuePair> list) {
    }
}
