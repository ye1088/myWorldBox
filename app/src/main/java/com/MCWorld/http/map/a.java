package com.MCWorld.http.map;

import com.MCWorld.data.TableList;
import com.MCWorld.data.map.MapCateItem;
import com.MCWorld.http.base.c;
import com.MCWorld.http.base.d;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: MapCateRequest */
public class a extends c {
    public String eU() {
        return String.format(Locale.getDefault(), "%s/map/category", new Object[]{c.HOST});
    }

    public void a(d response, JSONObject json) throws JSONException {
        TableList tableList = new TableList(json);
        JSONArray jsonArray = json.optJSONArray("categorylist");
        for (int i = 0; i < jsonArray.length(); i++) {
            tableList.add(new MapCateItem((JSONObject) jsonArray.opt(i)));
        }
        response.setData(tableList);
    }

    public void g(List<NameValuePair> list) {
    }
}
