package com.MCWorld.http.profile;

import com.MCWorld.data.profile.ProductList;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProfileScoreRequest */
public class j extends a {
    public static final int tq = 0;
    public static final int tr = 1;
    private int type_id = 0;

    public String eU() {
        return String.format(Locale.getDefault(), "%s?type_id=%d", new Object[]{ab.aAv, Integer.valueOf(this.type_id)});
    }

    public void a(d response, JSONObject json) throws JSONException {
        ProductList data = null;
        if (response.getStatus() == 1) {
            data = new ProductList(json);
        }
        response.setData(data);
    }

    public void g(List<NameValuePair> list) {
    }

    public int fU() {
        return this.type_id;
    }

    public void bj(int type_id) {
        this.type_id = type_id;
    }
}
