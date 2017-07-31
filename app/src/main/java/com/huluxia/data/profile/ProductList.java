package com.huluxia.data.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductList implements Serializable {
    private static final long serialVersionUID = -5306666686259149770L;
    private List<GiftInfo> gifts = null;
    private String ruleText;
    private UserCredits user = null;

    public ProductList(JSONObject obj) throws JSONException {
        this.ruleText = obj.optString("ruleText");
        if (!obj.isNull("user")) {
            this.user = new UserCredits(obj.optJSONObject("user"));
        }
        this.gifts = new ArrayList();
        JSONArray arry = obj.optJSONArray("products");
        if (arry != null) {
            for (int i = 0; i < arry.length(); i++) {
                this.gifts.add(new GiftInfo(arry.optJSONObject(i)));
            }
        }
    }

    public List<GiftInfo> getGifts() {
        return this.gifts;
    }

    public void setGifts(List<GiftInfo> gifts) {
        this.gifts = gifts;
    }

    public String getRuleText() {
        return this.ruleText;
    }

    public void setRuleText(String ruleText) {
        this.ruleText = ruleText;
    }

    public UserCredits getUser() {
        return this.user;
    }

    public void setUser(UserCredits user) {
        this.user = user;
    }
}
