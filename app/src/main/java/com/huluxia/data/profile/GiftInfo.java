package com.huluxia.data.profile;

import com.tencent.open.SocialConstants;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class GiftInfo implements Serializable {
    public static final int TYPE_ALIPAY = 3;
    public static final int TYPE_OBJECT = 4;
    public static final int TYPE_QB = 1;
    public static final int TYPE_TEL = 2;
    private static final long serialVersionUID = -5231108066075662905L;
    private long GUID;
    private int cashType;
    private String convert;
    private long credits;
    private String desc;
    private String icon;
    private String name;

    public GiftInfo(JSONObject obj) throws JSONException {
        this.GUID = obj.optLong("GUID");
        this.name = obj.optString("name");
        this.icon = obj.optString("icon");
        this.credits = obj.optLong("credits");
        this.desc = obj.optString(SocialConstants.PARAM_APP_DESC);
        this.convert = obj.optString("convert");
        this.cashType = obj.optInt(a.qb);
    }

    public long getGUID() {
        return this.GUID;
    }

    public void setGUID(long gUID) {
        this.GUID = gUID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getCredits() {
        return this.credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getConvert() {
        return this.convert;
    }

    public int getCashType() {
        return this.cashType;
    }

    public void setCashType(int cashType) {
        this.cashType = cashType;
    }
}
