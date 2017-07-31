package com.huluxia.http.bbs.category;

import com.huluxia.data.TableListParc;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.http.base.a;
import com.huluxia.http.base.d;
import com.huluxia.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: McTopicCategoryListRequest */
public class g extends a {
    public String eU() {
        return String.format(Locale.getDefault(), ab.ayC, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
        int i;
        TableListParc tableList = new TableListParc(json);
        JSONArray jsonArray = json.optJSONArray("McCategorylist");
        HLog.info("TestTest", "McCategorylist size = " + jsonArray.length(), new Object[0]);
        for (i = 0; i < jsonArray.length(); i++) {
            tableList.add(new TopicCategory((JSONObject) jsonArray.opt(i)));
        }
        jsonArray = json.optJSONArray("BBSCategorylist");
        for (i = 0; i < jsonArray.length(); i++) {
            tableList.add(new TopicCategory((JSONObject) jsonArray.opt(i)));
        }
        response.setData(tableList);
    }

    public void g(List<NameValuePair> list) {
    }
}
