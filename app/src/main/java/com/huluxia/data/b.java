package com.huluxia.data;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CartoonInfo */
public class b {
    private int count;

    public b(JSONObject obj) throws JSONException {
        this.count = obj.optInt("count");
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
