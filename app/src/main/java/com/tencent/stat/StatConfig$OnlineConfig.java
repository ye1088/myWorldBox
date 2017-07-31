package com.tencent.stat;

import org.json.JSONObject;

class StatConfig$OnlineConfig {
    String md5sum = "";
    JSONObject props = new JSONObject();
    int type;
    int version = 0;

    public StatConfig$OnlineConfig(int i) {
        this.type = i;
    }

    String toJsonString() {
        return this.props.toString();
    }
}
