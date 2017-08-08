package com.MCWorld.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HTSysBannerInfo implements Serializable {
    private static final long serialVersionUID = -5763541814344106833L;
    private String msg;
    private List<Screen> screenlist = new ArrayList();

    public HTSysBannerInfo(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.msg = obj.optString("msg");
            JSONArray arrScreen = obj.optJSONArray("screenlist");
            if (arrScreen != null) {
                for (int i = 0; i < arrScreen.length(); i++) {
                    this.screenlist.add(new Screen(arrScreen.optJSONObject(i)));
                }
            }
        }
    }

    public String getMsg() {
        return this.msg;
    }

    public List<Screen> getScreenlist() {
        return this.screenlist;
    }

    public Screen getFirstItem() {
        if (this.screenlist == null || this.screenlist.isEmpty()) {
            return new Screen();
        }
        return (Screen) this.screenlist.get(0);
    }
}
