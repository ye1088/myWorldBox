package com.MCWorld.http.bbs.category;

import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CategoryDetailRequest */
public class b extends a {
    private long sm;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?cat_id=%d", new Object[]{ab.ayX, Long.valueOf(this.sm)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(new TopicCategory(json));
    }

    public void g(List<NameValuePair> list) {
    }

    public long fm() {
        return this.sm;
    }

    public void v(long cat_id) {
        this.sm = cat_id;
    }
}
