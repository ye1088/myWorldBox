package com.huluxia.data;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: MiCheckInfo */
public class h {
    private int code;
    private String pn;
    private String po;
    private Boolean pp;
    private k pq;

    public h(JSONObject obj) throws JSONException {
        this.code = obj.optInt("code");
        this.pn = obj.optString("errormsg");
        this.po = obj.optString("miUserid");
        this.pp = Boolean.valueOf(obj.optBoolean("isuser"));
        if (!obj.isNull("login")) {
            this.pq = new k(obj.optJSONObject("login"));
        }
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String el() {
        return this.pn;
    }

    public void aw(String errorMsg) {
        this.pn = errorMsg;
    }

    public String em() {
        return this.po;
    }

    public void ax(String miUid) {
        this.po = miUid;
    }

    public Boolean en() {
        return this.pp;
    }

    public void a(Boolean isUser) {
        this.pp = isUser;
    }

    public k eo() {
        return this.pq;
    }

    public void a(k sessionInfo) {
        this.pq = sessionInfo;
    }
}
